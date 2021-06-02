package by.shyshaliaksey.task5.entity;

import java.util.Comparator;

public class DeliveryVanQueueComparator implements Comparator<DeliveryVan> {

	/**
	 * Special comparator for {@link by.shyshaliaksey.task5.entity.DeliveryVanQueue}
	 * PriorityQueue does not guarantee ordering of elements of equal priority
	 */
	@Override
	public int compare(DeliveryVan van1, DeliveryVan van2) {
		boolean type1 = van1.isPerishable();
		boolean type2 = van2.isPerishable();
		int result = 0;
		if (type1 && !type2) {
			result = -1;
		} else if (!type1 && type2) {
			result = 1;
		} else {
			// correct ordering of elements of equal priority
			result = van1.getId() - van2.getId();
		}
		return result;
	}

}
