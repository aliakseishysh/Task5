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
		Product product1 = new Product("Orange", ShelfLifeType.PERISHABLE);
		Product product2 = new Product("Apple", ShelfLifeType.PERISHABLE);
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);
		DeliveryVan van1 = new DeliveryVan(products);
		
		StringWriter writer = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(writer, van1);
		String actual = writer.toString();
		String expected = "{\"products\":[{\"productName\":\"Orange\",\"shelfLifeType\":\"PERISHABLE\"},{\"productName\":\"Apple\",\"shelfLifeType\":\"PERISHABLE\"}]}";
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
		
		List<DeliveryVan> expected = new ArrayList<>();
		
		Product product1 = new Product("Orange", ShelfLifeType.PERISHABLE);
		Product product2 = new Product("Apple", ShelfLifeType.PERISHABLE);
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);
		DeliveryVan van = new DeliveryVan(products);
		expected.add(van);
		
		product1 = new Product("Chair", ShelfLifeType.NOT_PERISHABLE);
		products = new ArrayList<>();
		products.add(product1);
		van = new DeliveryVan(products);
		expected.add(van);
		
		products = new ArrayList<>();
		van = new DeliveryVan(products);
		expected.add(van);
		
		products = new ArrayList<>();
		product1 = new Product("Cola", ShelfLifeType.NOT_PERISHABLE);
		products.add(product1);
		van = new DeliveryVan(products);
		expected.add(van);
		
		Assert.assertEquals(actual, expected);
	}
	
}
