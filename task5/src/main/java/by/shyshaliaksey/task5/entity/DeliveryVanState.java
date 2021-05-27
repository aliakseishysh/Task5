package by.shyshaliaksey.task5.entity;

public abstract class DeliveryVanState {
	
	private DeliveryVan deliveryVan;
	
	protected DeliveryVanState(DeliveryVan deliveryVan) {
		this.setDeliveryVan(deliveryVan);
	}
	
	public DeliveryVan getDeliveryVan() {
		return deliveryVan;
	}

	public void setDeliveryVan(DeliveryVan deliveryVan) {
		this.deliveryVan = deliveryVan;
	}
	
	public abstract void unload();
	public abstract void load();
	public abstract void await();

}
