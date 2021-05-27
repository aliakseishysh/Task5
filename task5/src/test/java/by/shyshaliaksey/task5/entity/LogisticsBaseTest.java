package by.shyshaliaksey.task5.entity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import by.shyshaliaksey.task5.exception.MultithreadingTaskException;
import by.shyshaliaksey.task5.reader.TextReader;
import by.shyshaliaksey.task5.reader.impl.TextReaderImpl;

public class LogisticsBaseTest {

	@Test
	public void test() throws URISyntaxException, MultithreadingTaskException, InterruptedException, ExecutionException {
		URI uri = getClass().getResource("/data/DeliveryVansInfo.txt").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		TextReader reader = new TextReaderImpl();
		List<String> content = reader.readAllLines(absolutePath);
		List<DeliveryVan> vans = content.stream()
				.map(DeliveryVanFactory::createInstance)
				.collect(Collectors.toList());
		int expected = LogisticsBase.getCurrentContainerCount();
		for (DeliveryVan van: vans) {
			expected += van.getContainersToUnload();
			expected -= van.getContainersToLoad();
		}
		LogisticsBase base = LogisticsBase.getInstance();
		base.addAllVansToQueue(vans);
		base.handle();
		int actual = LogisticsBase.getCurrentContainerCount();
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void testSublistFrom0To6() throws URISyntaxException, MultithreadingTaskException, InterruptedException {
		URI uri = getClass().getResource("/data/DeliveryVansInfo.txt").toURI();
		String absolutePath = new File(uri).getAbsolutePath();
		TextReader reader = new TextReaderImpl();
		List<String> content = reader.readAllLines(absolutePath);
		List<DeliveryVan> vans = content.stream()
				.map(DeliveryVanFactory::createInstance)
				.collect(Collectors.toList());
		vans = vans.subList(0, 6);
		int expected = LogisticsBase.getCurrentContainerCount();
		for (DeliveryVan van: vans) {
			expected += van.getContainersToUnload();
			expected -= van.getContainersToLoad();
		}
		LogisticsBase base = LogisticsBase.getInstance();
		base.addAllVansToQueue(vans);
		base.handle();
		int actual = LogisticsBase.getCurrentContainerCount();
		Assert.assertEquals(actual, expected);
	}
}
