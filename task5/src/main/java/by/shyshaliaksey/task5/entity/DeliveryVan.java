package by.shyshaliaksey.task5.entity;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeliveryVan implements Callable {

	private static Logger logger = LogManager.getRootLogger();
	private int containerCount;
	private ShelfLifeType containsPerishableProduct;
	
	public DeliveryVan() {
	}
	
	public DeliveryVan(int containerCount, ShelfLifeType containsPerishableProduct) {
		this.setContainerCount(containerCount);
		this.setContainsPerishableProduct(containsPerishableProduct);
	}

	public int getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(int containerCount) {
		this.containerCount = containerCount;
	}

	public ShelfLifeType getContainsPerishableProduct() {
		return containsPerishableProduct;
	}

	public void setContainsPerishableProduct(ShelfLifeType containsPerishableProduct) {
		this.containsPerishableProduct = containsPerishableProduct;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeliveryVan [containerCount=");
		builder.append(containerCount);
		builder.append(", containsPerishableProduct=");
		builder.append(containsPerishableProduct);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + containerCount;
		result = prime * result + ((containsPerishableProduct == null) ? 0 : containsPerishableProduct.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		DeliveryVan other = (DeliveryVan) obj;
		if (containerCount != other.containerCount 
				|| containsPerishableProduct != other.containsPerishableProduct) {
			return false;
		}
		return true;
	}

	@Override
	public Object call() throws InterruptedException {
//		LogisticsBase instance = LogisticsBase.getInstance();
//		Optional<Terminal> terminalOptional;
//		while(true) {
//			terminalOptional = instance.getFreeTerminal();
//			if (!terminalOptional.isEmpty()) {
//				break;
//			}
//			TimeUnit.SECONDS.sleep(5);
//		}
		logger.log(Level.DEBUG, "Handling: {}", this);
		LogisticsBase.setCurrentContainerCount(LogisticsBase.getCurrentContainerCount() + this.getContainerCount());
		setContainerCount(0);
		setContainsPerishableProduct(ShelfLifeType.NOT_PERISHABLE);
		TimeUnit.SECONDS.sleep(3);
		logger.log(Level.DEBUG, "Handling completed: {}", this);
		
		return "Thread finished: " + this.toString();
	}



	
}
