package by.shyshaliaksey.task5.entity;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeliveryVanFactory {

	private static final Logger logger = LogManager.getRootLogger();

	private DeliveryVanFactory() {
	}

	public static DeliveryVan createInstance(String jsonObject) {
		ObjectMapper mapper = new ObjectMapper();
		DeliveryVan deliveryVan = null;
		try {
			deliveryVan = mapper.readValue(jsonObject, DeliveryVan.class);
			deliveryVan.setState(DeliveryVanState.NEW);
		} catch (JsonProcessingException e) {
			// never happened due to task conditions
			logger.log(Level.ERROR, "Corrupted string: {}", jsonObject);
		}
		return deliveryVan;
	}

	public static List<DeliveryVan> createInstance(List<String> jsonObjects) {
		return jsonObjects.stream().map(DeliveryVanFactory::createInstance).collect(Collectors.toList());
	}

}
