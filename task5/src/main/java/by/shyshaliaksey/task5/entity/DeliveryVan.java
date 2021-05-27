package by.shyshaliaksey.task5.entity;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DeliveryVan implements Callable<DeliveryVan> {

	private static Logger logger = LogManager.getRootLogger();
//	private DeliveryVanState deliveryVanState;
	private int id;
	private int containersInVan;
	private int containersToLoad;
	private int containersToUnload;
	private ShelfLifeType containsPerishableProduct;
	
	public DeliveryVan() {
//		this.setDeliveryVanState();
	}
	
	public DeliveryVan(int id, int containersInVan, int containersToLoad, int containersToUnload, ShelfLifeType containsPerishableProduct) {
		this.setId(id);
		this.setContainersInVan(containersInVan);
		this.setContainersToLoad(containersToLoad);
		this.setContainersToUnload(containersToUnload);
		this.setContainsPerishableProduct(containsPerishableProduct);
	}
	
//	public DeliveryVanState getDeliveryVanState() {
//		return deliveryVanState;
//	}
//
//	public void setDeliveryVanState(DeliveryVanState deliveryVanState) {
//		this.deliveryVanState = deliveryVanState;
//	}

	public int getContainersInVan() {
		return containersInVan;
	}

	public void setContainersInVan(int containersInVan) {
		this.containersInVan = containersInVan;
	}

	public int getContainersToLoad() {
		return containersToLoad;
	}

	public void setContainersToLoad(int containersToLoad) {
		this.containersToLoad = containersToLoad;
	}

	public int getContainersToUnload() {
		return containersToUnload;
	}

	public void setContainersToUnload(int containersToUnload) {
		this.containersToUnload = containersToUnload;
	}

	public ShelfLifeType getContainsPerishableProduct() {
		return containsPerishableProduct;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + containersInVan;
		result = prime * result + containersToLoad;
		result = prime * result + containersToUnload;
		result = prime * result + ((containsPerishableProduct == null) ? 0 : containsPerishableProduct.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DeliveryVan other = (DeliveryVan) obj;
		if (containersInVan != other.containersInVan) {
			return false;
		}
		if (containersToLoad != other.containersToLoad) {
			return false;
		}
		if (containersToUnload != other.containersToUnload) {
			return false;
		}
		if (containsPerishableProduct != other.containsPerishableProduct) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeliveryVan [id=");
		builder.append(id);
		builder.append(", containersInVan=");
		builder.append(containersInVan);
		builder.append(", containersToLoad=");
		builder.append(containersToLoad);
		builder.append(", containersToUnload=");
		builder.append(containersToUnload);
		builder.append(", containsPerishableProduct=");
		builder.append(containsPerishableProduct);
		builder.append("]");
		return builder.toString();
	}

	public void setContainsPerishableProduct(ShelfLifeType containsPerishableProduct) {
		this.containsPerishableProduct = containsPerishableProduct;
	}
	
	@Override
	public DeliveryVan call() throws InterruptedException {
		int timeToUnloadContainer = 2;
		int timeToLoadContainer = 1;
		logger.log(Level.DEBUG, "Start unloading from DeliveryVan №{}: containers №{}", 
				this.id, this.containersToUnload);
		// unload containers
		while (this.containersToUnload > 0) {
			TimeUnit.SECONDS.sleep(timeToUnloadContainer);
			this.containersToUnload--;
			this.containersInVan--;
			LogisticsBase.changeCurrentContainerCount(1);
			logger.log(Level.DEBUG, "Container unloaded from DeliveryVan №{}", this.id);
		}
		logger.log(Level.DEBUG, "Containers unloaded from DeliveryVan №{}", this.id);
		
		// load containers
		logger.log(Level.DEBUG, "Start loading to DeliveryVan №{}: containers №{}", 
				this.id, this.containersToLoad);
		while (this.containersToLoad > 0) {
			TimeUnit.SECONDS.sleep(timeToLoadContainer);
			this.containersToLoad--;
			this.containersInVan++;
			LogisticsBase.changeCurrentContainerCount(-1);
			logger.log(Level.DEBUG, "Container loaded to DeliveryVan №{}", this.id);
		}
		logger.log(Level.DEBUG, "Containers loaded to DeliveryVan №{}", this.id);
		
		return this;
	}





	
}
