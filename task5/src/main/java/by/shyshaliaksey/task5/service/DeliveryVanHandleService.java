package by.shyshaliaksey.task5.service;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.shyshaliaksey.task5.entity.DeliveryVan;
import by.shyshaliaksey.task5.entity.LogisticsBaseSingleton;

public class DeliveryVanHandleService {

	private static final Logger logger = LogManager.getRootLogger();

	public void unload(DeliveryVan deliveryVan) {
		logger.log(Level.DEBUG, "Start unloading from DeliveryVan №{}: containers №{}", deliveryVan.getId(),
				deliveryVan.getContainersToUnload());
		while (deliveryVan.getContainersToUnload() > 0) {
			try {
				TimeUnit.SECONDS.sleep(ContainerTime.TO_UNLOAD.getValue());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			deliveryVan.setContainersToUnload(deliveryVan.getContainersToUnload() - 1);
			deliveryVan.setContainersInVan(deliveryVan.getContainersInVan() - 1);
			LogisticsBaseSingleton logisticsBaseSingleton = LogisticsBaseSingleton.getInstance();
			logisticsBaseSingleton.addContainersInBase(1);
			logger.log(Level.DEBUG, "Container unloaded from DeliveryVan №{}", deliveryVan.getId());
		}
		logger.log(Level.DEBUG, "Containers unloaded from DeliveryVan №{}", deliveryVan.getId());
	}

	public void load(DeliveryVan deliveryVan) {
		logger.log(Level.DEBUG, "Start loading to DeliveryVan №{}: containers №{}", deliveryVan.getId(),
				deliveryVan.getContainersToLoad());
		while (deliveryVan.getContainersToLoad() > 0) {
			try {
				TimeUnit.SECONDS.sleep(ContainerTime.TO_LOAD.getValue());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			deliveryVan.setContainersToLoad(deliveryVan.getContainersToLoad() - 1);
			deliveryVan.setContainersInVan(deliveryVan.getContainersInVan() + 1);
			LogisticsBaseSingleton logisticsBaseSingleton = LogisticsBaseSingleton.getInstance();
			logisticsBaseSingleton.addContainersInBase(-1);
			logger.log(Level.DEBUG, "Container loaded to DeliveryVan №{}", deliveryVan.getId());
		}
		logger.log(Level.DEBUG, "Containers loaded to DeliveryVan №{}", deliveryVan.getId());
	}

}
