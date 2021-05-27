package by.shyshaliaksey.task5.entity;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.shyshaliaksey.task5.service.DeliveryVanHandleService;

public class DeliveryVan implements Callable<DeliveryVan> {

	private static Logger logger = LogManager.getRootLogger();
	private int id;
	private int containersInVan;
	private int containersToLoad;
	private int containersToUnload;
	private ShelfLifeType containsPerishableProduct;

	public DeliveryVan() {
	}

	public DeliveryVan(int id, int containersInVan, int containersToLoad, int containersToUnload,
			ShelfLifeType containsPerishableProduct) {
		this.setId(id);
		this.setContainersInVan(containersInVan);
		this.setContainersToLoad(containersToLoad);
		this.setContainersToUnload(containersToUnload);
		this.setContainsPerishableProduct(containsPerishableProduct);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	
	public void setContainsPerishableProduct(ShelfLifeType containsPerishableProduct) {
		this.containsPerishableProduct = containsPerishableProduct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + containersInVan;
		result = prime * result + containersToLoad;
		result = prime * result + containersToUnload;
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

		return id == other.id 
				|| containersInVan == other.containersInVan
				|| containersToLoad == other.containersToLoad 
				|| containersToUnload == other.containersToUnload
				|| containsPerishableProduct == other.containsPerishableProduct;
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

	@Override
	public DeliveryVan call() throws InterruptedException {
		DeliveryVanHandleService handleService = new DeliveryVanHandleService();
		if (this.containersToUnload > 0) {
			handleService.unload(this);
		} else {
			logger.log(Level.DEBUG, "Nothing to unload from DeliveryVan №{}", this.id);
		}
		if (this.containersToLoad > 0) {
			handleService.load(this);
		} else {
			logger.log(Level.DEBUG, "Nothing to load to DeliveryVan №{}", this.id);
		}
		return this;
	}

}
