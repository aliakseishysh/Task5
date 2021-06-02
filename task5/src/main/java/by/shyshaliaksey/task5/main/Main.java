package by.shyshaliaksey.task5.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.shyshaliaksey.task5.entity.DeliveryVan;
import by.shyshaliaksey.task5.entity.DeliveryVanFactory;
import by.shyshaliaksey.task5.entity.DeliveryVanQueue;
import by.shyshaliaksey.task5.entity.LogisticsBaseSingleton;
import by.shyshaliaksey.task5.exception.MultithreadingTaskException;
import by.shyshaliaksey.task5.reader.DeliveryVansReader;

public class Main {

	private static final Logger logger = LogManager.getLogger();
	private static final String DELIVERY_VANS_PATH = "/data/DeliveryVansInfo.txt";

	public static void main(String[] strings) throws MultithreadingTaskException {
		DeliveryVansReader reader = new DeliveryVansReader();
		List<String> jsonStrings = reader.readAllLines(DELIVERY_VANS_PATH);
		List<DeliveryVan> vans = DeliveryVanFactory.createInstance(jsonStrings);
		DeliveryVanQueue deliveryVanQueue = new DeliveryVanQueue(vans);
		LogisticsBaseSingleton logisticsBaseSingleton = LogisticsBaseSingleton.getInstance();
		ExecutorService executorService = Executors.newFixedThreadPool(logisticsBaseSingleton.getTerminalCount());
		List<Future<DeliveryVan>> futures = new ArrayList<>(logisticsBaseSingleton.getTerminalCount());
		while (!deliveryVanQueue.isEmpty()) {
			if (futures.size() < logisticsBaseSingleton.getTerminalCount()) {
				DeliveryVan van = deliveryVanQueue.pool();
				logger.log(Level.DEBUG, "DeliveryVan №{} submited to ExecutorService", van.getId());
				futures.add(executorService.submit(van));
			}
			while (futures.size() == logisticsBaseSingleton.getTerminalCount()) {
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
		LogisticsBaseSingleton logisticsBaseSingleton = LogisticsBaseSingleton.getInstance();
		List<Future<DeliveryVan>> futuresToRemove = new ArrayList<>(logisticsBaseSingleton.getTerminalCount());
		for (Future<DeliveryVan> future : futures) {
			if (future.isDone()) {
				futuresToRemove.add(future);
			}
		}
		futures.removeAll(futuresToRemove);
	}

}
