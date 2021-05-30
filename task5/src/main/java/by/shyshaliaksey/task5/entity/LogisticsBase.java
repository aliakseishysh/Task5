package by.shyshaliaksey.task5.entity;

import java.util.ArrayDeque;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogisticsBase {

	public static final int MAX_CONTAINER_COUNT;
	public static final int TERMINAL_COUNT;
	private static final Logger logger = LogManager.getRootLogger();
	private static final ReentrantLock containersLock = new ReentrantLock(true);
	private static final ReentrantLock freeTerminalsLock = new ReentrantLock(true);
	private static final ReentrantLock occupiedTerminalsLock = new ReentrantLock(true);
	private static final Queue<Terminal> freeTerminals = new ArrayDeque<>();
	private static final Queue<Terminal> occupiedTerminals = new ArrayDeque<>();
	private static final AtomicBoolean isBaseCreated = new AtomicBoolean(false);
	private static int currentContainerCount;
	private static LogisticsBase instance;

	static {
		try {
			ResourceBundle resources = ResourceBundle.getBundle("\\data\\base");
			MAX_CONTAINER_COUNT = Integer.parseInt(resources.getString("MAX_CONTAINER_COUNT"));
			TERMINAL_COUNT = Integer.parseInt(resources.getString("TERMINAL_COUNT"));
			currentContainerCount = Integer.parseInt(resources.getString("currentContainerCount"));
			for (int i = 0; i < TERMINAL_COUNT; i++) {
				freeTerminals.add(new Terminal());
			}
		} catch (MissingResourceException e) {
			logger.log(Level.FATAL, "MissingResourceException: {}", e.getMessage());
			throw new ExceptionInInitializerError("MissingResourceException: " + e.getMessage());
		}
	}

	private LogisticsBase() {
	}

	public static LogisticsBase getInstance() {
		while (instance == null) {
			if (isBaseCreated.compareAndSet(false, true)) {
				instance = new LogisticsBase();
			}
		}
		return instance;
	}

	public static int getCurrentContainerCount() {
		return currentContainerCount;
	}

	public Terminal getFreeTerminal() {
		Terminal terminal;
		while (true) {
			freeTerminalsLock.lock();
			try {
				if (!freeTerminals.isEmpty()) {
					terminal = freeTerminals.poll();
					break;
				}
			} finally {
				freeTerminalsLock.unlock();
			}
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
		} finally {
			freeTerminalsLock.unlock();
		}
	}

	public static void changeCurrentContainerCount(int containersCount) {
		containersLock.lock();
		try {
			currentContainerCount += containersCount;
		} finally {
			containersLock.unlock();
		}
	}
}
