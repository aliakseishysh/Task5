package by.shyshaliaksey.task5.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum DeliveryVanState {

	NEW {
		@Override
		DeliveryVanState printInfoMessage(int vanId) {
			Logger logger = LogManager.getRootLogger();
			logger.log(Level.INFO, "New DeliveryVan in Queue: {}", vanId);
			return this;
		}
	},
	PROCESSING {
		@Override
		DeliveryVanState printInfoMessage(int vanId) {
			Logger logger = LogManager.getRootLogger();
			logger.log(Level.INFO, "DeliveryVan {} is now processing", vanId);
			return this;
		}
	},
	FINISHED {
		@Override
		DeliveryVanState printInfoMessage(int vanId) {
			Logger logger = LogManager.getRootLogger();
			logger.log(Level.INFO, "DeliveryVan {} finished", vanId);
			return this;
		}
	};

	abstract DeliveryVanState printInfoMessage(int vanId);
}
