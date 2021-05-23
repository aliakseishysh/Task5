package by.shyshaliaksey.task5.entity;

import org.testng.annotations.Test;

public class LogisticsBaseSingletonTest {

	@Test
	public void readPropertiesTest() {
		LogisticsBaseSingleton base = LogisticsBaseSingleton.getInstance();
		System.out.println(base.getCurrentContainerCount());
	}
	
}
