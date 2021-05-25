package by.shyshaliaksey.task5.entity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import by.shyshaliaksey.task5.exception.MultithreadingTaskException;
import by.shyshaliaksey.task5.reader.TextReader;
import by.shyshaliaksey.task5.reader.impl.TextReaderImpl;

public class MainTest {

	@Test
	public void test() throws URISyntaxException, MultithreadingTaskException, InterruptedException, ExecutionException {
		URI uri = getClass().getResource("/data/DeliveryVansInfo.txt").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		TextReader reader = new TextReaderImpl();
		List<String> content = reader.readAllLines(absolutePath);
		List<DeliveryVan> vans = content.stream()
				.map(DeliveryVanFactory::createInstance)
				.collect(Collectors.toList());
		LogisticsBase base = LogisticsBase.getInstance();
		for(DeliveryVan van : vans) {
			base.addVanToQueue(van);
		}
		base.handle();
		System.out.println("Container Count: " + LogisticsBase.getCurrentContainerCount());
	}
	
}
