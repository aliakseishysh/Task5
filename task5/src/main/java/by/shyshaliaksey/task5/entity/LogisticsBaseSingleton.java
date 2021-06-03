package by.shyshaliaksey.task5.entity;

import java.util.ArrayDeque;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogisticsBaseSingleton {

	private static LogisticsBaseSingleton instance;
	private static final ResourceBundle logisticsBaseSettings;
	private static final Logger logger = LogManager.getRootLogger();
	private static final AtomicBoolean isBaseCreated = new AtomicBoolean(false);
	private static final String LOGISTICS_BASE_PROPERTIES_PATH = "\\data\\base";
	private static final String MAX_CONTAINER_COUNT_NAME = "MAX_CONTAINER_COUNT";
	private static final String TERMINAL_COUNT_NAME = "TERMINAL_COUNT";
	private static final String CURRENT_CONTAINER_COUNT_NAME = "currentContainerCount";
	private final int maxContainerCount;
	private final int terminalCount;
	private final ReentrantLock freeTerminalsLock = new ReentrantLock(true);
	private final Condition freeTerminalsCondition = freeTerminalsLock.newCondition();
	private final ReentrantLock occupiedTerminalsLock = new ReentrantLock(true);
	private Queue<Terminal> freeTerminals = new ArrayDeque<>();
	private Queue<Terminal> occupiedTerminals = new ArrayDeque<>();
	private AtomicInteger containersInBase;

	static {
		try {
			logisticsBaseSettings = ResourceBundle.getBundle(LOGISTICS_BASE_PROPERTIES_PATH);
		} catch (MissingResourceException e) {
			logger.log(Level.FATAL, "MissingResourceException: {}", e.getMessage());
			throw new ExceptionInInitializerError("MissingResourceException: " + e.getMessage());
		}
	}

	private LogisticsBaseSingleton() {
		maxContainerCount = Integer.parseInt(logisticsBaseSettings.getString(MAX_CONTAINER_COUNT_NAME));
		terminalCount = Integer.parseInt(logisticsBaseSettings.getString(TERMINAL_COUNT_NAME));
		containersInBase = new AtomicInteger(Integer.parseInt(logisticsBaseSettings.getString(CURRENT_CONTAINER_COUNT_NAME)));
		for (int i = 0; i < terminalCount; i++) {
			freeTerminals.add(new Terminal());
		}
	}

	public static LogisticsBaseSingleton getInstance() {
		while (instance == null) {
			if (isBaseCreated.compareAndSet(false, true)) {
				instance = new LogisticsBaseSingleton();
			}
		}
		return instance;
	}

	public AtomicInteger getContainersInBase() {
		return containersInBase;
	}
	
	public void addContainersInBase(int containers) {
		containersInBase.addAndGet(containers);
	}

	public Terminal getFreeTerminal() {
		Terminal terminal;
		freeTerminalsLock.lock();
		while (freeTerminals.isEmpty()) {
			try {
				freeTerminalsCondition.await();
			} catch (InterruptedException e) {
				logger.log(Level.DEBUG, "Thread was interrupted: {}", e.getMessage());
				Thread.currentThread().interrupt();
			} 
		}
		try {
			terminal = freeTerminals.poll();
		} finally {
			freeTerminalsLock.unlock();
		}
		occupiedTerminalsLock.lock();
		try {
			occupiedTerminals.add(terminal);
		} finally {
			occupiedTerminalsLock.unlock();
		}
		return terminal;
	}
	
	public void releaseOccupiedTerminal(Terminal terminal) {
		occupiedTerminalsLock.lock();
		try {
			occupiedTerminals.remove(terminal);
		} finally {
			occupiedTerminalsLock.unlock();
		}
		try {
			freeTerminalsLock.lock();
			freeTerminals.add(terminal);
			freeTerminalsCondition.signal();
		} finally {
			freeTerminalsLock.unlock();
		}
	}

	public int getMaxContainerCount() {
		return maxContainerCount;
	}

	public int getTerminalCount() {
		return terminalCount;
	}
	
}
