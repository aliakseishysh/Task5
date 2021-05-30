package by.shyshaliaksey.task5.entity.comparator;

import java.util.Comparator;

import by.shyshaliaksey.task5.entity.DeliveryVan;
import by.shyshaliaksey.task5.entity.ShelfLifeType;

public class DeliveryVanByTypeThenIdComparator implements Comparator<DeliveryVan> {

	@Override
	public int compare(DeliveryVan van1, DeliveryVan van2) {
		ShelfLifeType type1 = van1.getContainsPerishableProduct();
		ShelfLifeType type2 = van2.getContainsPerishableProduct();
		int result = 0;
		if (type1 == ShelfLifeType.PERISHABLE && type2 == ShelfLifeType.NOT_PERISHABLE) {
			result = -1;
		} else if (type1 == ShelfLifeType.NOT_PERISHABLE && type2 == ShelfLifeType.PERISHABLE) {
			result = 1;
		} else {
			result = van1.getId() - van2.getId();
		}
		return result;
	}

}
