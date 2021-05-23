package by.shyshaliaksey.task5.entity;

public class Product implements Cloneable {

	private String productName;
	private ShelfLifeType shelfLifeType;
	
	public Product() {
	}
	
	public Product(String productName, ShelfLifeType shelfLifeType) {
		this.productName = productName;
		this.shelfLifeType = shelfLifeType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ShelfLifeType getShelfLifeType() {
		return shelfLifeType;
	}

	public void setShelfLifeType(ShelfLifeType shelfLifeType) {
		this.shelfLifeType = shelfLifeType;
	}
	
	@Override
	public Product clone() {
		try {
			return (Product) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [productName=");
		builder.append(productName);
		builder.append(", shelfLifeType=");
		builder.append(shelfLifeType);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + ((shelfLifeType == null) ? 0 : shelfLifeType.hashCode());
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
		Product other = (Product) obj;
		if (productName == null) {
			if (other.productName != null) {
				return false;
			}
		} else if (!productName.equals(other.productName)) {
			return false;
		}
		if (shelfLifeType != other.shelfLifeType) {
			return false;
		}
		return true;
	}
	
}
