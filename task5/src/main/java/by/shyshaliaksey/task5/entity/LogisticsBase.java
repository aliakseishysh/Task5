package by.shyshaliaksey.task5.entity;

import java.util.ArrayDeque;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogisticsBase {

	private static final Logger logger = LogManager.getRootLogger();
	public static final int MAX_CONTAINER_COUNT;
	public static final int TERMINAL_COUNT;
	private static LogisticsBase instance;
	private static int currentContainerCount;
	private static Queue<DeliveryVan> vansInQueue = new ArrayDeque<>();
	
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
	
	public void handle() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(TERMINAL_COUNT);
		while(!vansInQueue.isEmpty()) {
			DeliveryVan van = vansInQueue.poll();
			Future future = executorService.submit(van);
			
		}
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}
	
	public void addVanToQueue(DeliveryVan deliveryVan) {
		vansInQueue.add(deliveryVan);
	}
	
	public static void setCurrentContainerCount(int newCount) {
		currentContainerCount = newCount;
	}
	
	public static void increaseByOneCurrentContainerCount() {
		currentContainerCount++;
	}
	
	public static void decreaseByOneCurrentContainerCount() {
		currentContainerCount--;
	}
	
}
