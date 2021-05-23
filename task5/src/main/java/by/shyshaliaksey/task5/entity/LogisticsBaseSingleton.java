package by.shyshaliaksey.task5.entity;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogisticsBaseSingleton {

	private static final Logger logger = LogManager.getRootLogger();
	public static final int MAX_CONTAINER_COUNT;
	public static final int TERMINAL_COUNT;
	private static int currentContainerCount;
	private static LogisticsBaseSingleton instance;
	
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
	
	private LogisticsBaseSingleton() {
	}
	
	public static LogisticsBaseSingleton getInstance() {
		if (instance == null) {
			instance = new LogisticsBaseSingleton();
		}
		return instance;
	}
	
	public int getCurrentContainerCount() {
		return currentContainerCount;
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
