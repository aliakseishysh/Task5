package by.shyshaliaksey.task5.main;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.shyshaliaksey.task5.entity.DeliveryVan;
import by.shyshaliaksey.task5.entity.DeliveryVanQueue;
import by.shyshaliaksey.task5.entity.LogisticsBase;
import by.shyshaliaksey.task5.entity.factory.DeliveryVanFactory;
import by.shyshaliaksey.task5.exception.MultithreadingTaskException;
import by.shyshaliaksey.task5.reader.TextReader;

public class Main {

	private static final Logger logger = LogManager.getLogger();
	private static final String DELIVERY_VANS_PATH;

	static {
		try {
			ResourceBundle resources = ResourceBundle.getBundle("\\data\\project");
			DELIVERY_VANS_PATH = resources.getString("DELIVERY_VANS_PATH");
		} catch (MissingResourceException e) {
			logger.log(Level.FATAL, "MissingResourceException: {}", e.getMessage());
			throw new ExceptionInInitializerError("MissingResourceException: " + e.getMessage());
		}
	}

	public static void main(String[] strings) throws MultithreadingTaskException {
		TextReader reader = new TextReader();
		List<String> content = reader.readAllLines(DELIVERY_VANS_PATH);
		List<DeliveryVan> vans = content.stream().map(DeliveryVanFactory::createInstance).collect(Collectors.toList());
		DeliveryVanQueue.addAll(vans);

		ExecutorService executorService = Executors.newFixedThreadPool(LogisticsBase.TERMINAL_COUNT);
		List<Future<DeliveryVan>> futures = new ArrayList<>(LogisticsBase.TERMINAL_COUNT);

		while (!DeliveryVanQueue.isEmpty()) {
			if (futures.size() < LogisticsBase.TERMINAL_COUNT) {
				DeliveryVan van = DeliveryVanQueue.pool();
				logger.log(Level.DEBUG, "DeliveryVan №{} submited to ExecutorService", van.getId());
				futures.add(executorService.submit(van));
			}
			while (futures.size() == LogisticsBase.TERMINAL_COUNT) {
				removeHandledFutures(futures);
			}
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
			logger.log(Level.INFO, "All vans were handled");
		} catch (InterruptedException e) {
			logger.log(Level.INFO, "Current thread was interrupted: {}", e.getMessage());
			Thread.currentThread().interrupt();
		}

	}

	private static void removeHandledFutures(List<Future<DeliveryVan>> futures) {
		List<Future<DeliveryVan>> futuresToRemove = new ArrayList<>(LogisticsBase.TERMINAL_COUNT);
		for (Future<DeliveryVan> future : futures) {
			if (future.isDone()) {
				futuresToRemove.add(future);
			}
		}
		futures.removeAll(futuresToRemove);
	}

}
