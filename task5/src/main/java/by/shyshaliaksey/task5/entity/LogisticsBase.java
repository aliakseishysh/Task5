package by.shyshaliaksey.task5.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogisticsBase {

	public static final int MAX_CONTAINER_COUNT;
	public static final int TERMINAL_COUNT;
	private static final Logger logger = LogManager.getRootLogger();
	private static final ReentrantLock reentrantLock = new ReentrantLock(true);
	private static final List<DeliveryVan> vansInQueue = new ArrayList<>();
	private static int currentContainerCount;
	private static LogisticsBase instance;
	
	static {
		try  {
			ResourceBundle resources = ResourceBundle.getBundle("\\data\\base");
			MAX_CONTAINER_COUNT = Integer.parseInt(resources.getString("MAX_CONTAINER_COUNT"));
			TERMINAL_COUNT = Integer.parseInt(resources.getString("TERMINAL_COUNT"));
			currentContainerCount = Integer.parseInt(resources.getString("currentContainerCount"));
		} catch (MissingResourceException e) {
			logger.log(Level.FATAL, "MissingResourceException: {}", e.getMessage());
			throw new ExceptionInInitializerError("MissingResourceException: " + e.getMessage());
		}
	}
	
	private LogisticsBase() {
	}
	
	public static LogisticsBase getInstance() {
		if (instance == null) {
			instance = new LogisticsBase();
		}
		return instance;
	}
	
	public static int getCurrentContainerCount() {
		return currentContainerCount;
	}
	
	public void handle() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(TERMINAL_COUNT);
		List<Future<DeliveryVan>> futures = new ArrayList<>(TERMINAL_COUNT);
		while(!vansInQueue.isEmpty()) {
			if (futures.size() < TERMINAL_COUNT) {
				DeliveryVan van = vansInQueue.get(0);
				vansInQueue.remove(0);
				logger.log(Level.DEBUG, "DeliveryVan №{} submited to ExecutorService", van.getId());
				futures.add(executorService.submit(van));
			} 
			while (futures.size() == TERMINAL_COUNT) {
				removeDoneFutures(futures);
			}
		}
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}
	
	private void removeDoneFutures(List<Future<DeliveryVan>> futures) {
		List<Future<DeliveryVan>> futuresToRemove = new ArrayList<>(TERMINAL_COUNT);
		for (Future<DeliveryVan> future: futures) {
			if (future.isDone()) {
				futuresToRemove.add(future);
			}
		}
		futures.removeAll(futuresToRemove);
	}
	
	public void addVanToQueue(DeliveryVan deliveryVan) {
		if (deliveryVan.getContainsPerishableProduct() == ShelfLifeType.PERISHABLE) {
			Optional<DeliveryVan> lastPerishable = findLastPerishable();
			if (lastPerishable.isEmpty()) {
				vansInQueue.add(0, deliveryVan);
			} else {
				DeliveryVan insertAfter = lastPerishable.get();
				vansInQueue.add(vansInQueue.indexOf(insertAfter) + 1, deliveryVan);
			}
		} else {
			vansInQueue.add(deliveryVan);			
		}
	}
	
	private Optional<DeliveryVan> findLastPerishable() {
		Optional<DeliveryVan> lastPerishable = Optional.empty();
		for (DeliveryVan van: vansInQueue) {
			if (van.getContainsPerishableProduct() == ShelfLifeType.PERISHABLE) {
				lastPerishable = Optional.of(van);
			}
		}
		return lastPerishable;
	}
	
	
	public void addAllVansToQueue(List<DeliveryVan> deliveryVans) {
		deliveryVans.stream().forEach(this::addVanToQueue);
	}
	
	public static void changeCurrentContainerCount(int containersCount) {
		reentrantLock.lock();
		try {
			currentContainerCount += containersCount;
		} finally {
			reentrantLock.unlock();
		}
	}
}
