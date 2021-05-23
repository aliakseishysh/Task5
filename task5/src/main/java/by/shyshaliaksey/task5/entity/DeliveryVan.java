package by.shyshaliaksey.task5.entity;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryVan implements Cloneable {

	private List<Product> products;
	
	public DeliveryVan() {
	}
	
	public DeliveryVan(List<Product> products) {
		this.setProducts(products);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products.stream()
				.map(Product::clone)
				.collect(Collectors.toList());
	}
	
	@Override
	public DeliveryVan clone() {
		try {
			return (DeliveryVan) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeliveryVan [products=");
		builder.append(products);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((products == null) ? 0 : products.hashCode());
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
		if (products == null) {
			if (other.products != null) {
				return false;
			}
		} else if (!products.equals(other.products)) {
			return false;
		}
		return true;
	}
	
	
}
