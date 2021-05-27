package by.shyshaliaksey.task5.entity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import by.shyshaliaksey.task5.exception.MultithreadingTaskException;
import by.shyshaliaksey.task5.reader.TextReader;
import by.shyshaliaksey.task5.reader.impl.TextReaderImpl;


public class SerializeJsonTest {

	@Test
	public void serializeTest() throws JsonGenerationException, JsonMappingException, IOException {
		DeliveryVan van = new DeliveryVan(1, 2, 1, 2, ShelfLifeType.PERISHABLE);
		StringWriter writer = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(writer, van);
		String actual = writer.toString();
		String expected = "{\"id\":1,\"containersInVan\":2,\"containersToLoad\":1,\"containersToUnload\":2,\"containsPerishableProduct\":\"PERISHABLE\"}";
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void deserializeTest() throws URISyntaxException, MultithreadingTaskException, JsonMappingException, JsonProcessingException {
		URI uri = getClass().getResource("/data/DeliveryVansInfo.txt").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		TextReader reader = new TextReaderImpl();
		List<String> content = reader.readAllLines(absolutePath);
		List<DeliveryVan> actual = content.stream()
				.map(DeliveryVanFactory::createInstance)
				.collect(Collectors.toList());
		actual = actual.subList(0, 6);
		
		List<DeliveryVan> expected = new ArrayList<>();
		DeliveryVan van1 = new DeliveryVan(1, 2, 1, 2, ShelfLifeType.PERISHABLE);
		DeliveryVan van2 = new DeliveryVan(2, 3, 0, 2, ShelfLifeType.NOT_PERISHABLE);
		DeliveryVan van3 = new DeliveryVan(3, 4, 2, 0, ShelfLifeType.PERISHABLE);
		DeliveryVan van4 = new DeliveryVan(4, 5, 4, 5, ShelfLifeType.NOT_PERISHABLE);
		DeliveryVan van5 = new DeliveryVan(5, 6, 1, 1, ShelfLifeType.PERISHABLE);
		DeliveryVan van6 = new DeliveryVan(6, 0, 0, 0, ShelfLifeType.NOT_PERISHABLE);
		expected.add(van1);
		expected.add(van2);
		expected.add(van3);
		expected.add(van4);
		expected.add(van5);
		expected.add(van6);
		Assert.assertEquals(actual, expected);
	}
	
}
