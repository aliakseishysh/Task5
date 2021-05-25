package by.shyshaliaksey.task5.entity;

import org.testng.annotations.Test;

public class LogisticsBaseTest {

	@Test
	public void readPropertiesTest() {
		LogisticsBase base = LogisticsBase.getInstance();
		System.out.println(base.getCurrentContainerCount());
	}
	
}
