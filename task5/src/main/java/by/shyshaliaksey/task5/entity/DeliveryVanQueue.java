package by.shyshaliaksey.task5.entity;

import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DeliveryVanQueue {

	private Queue<DeliveryVan> vansOutsideBase;

	public DeliveryVanQueue(List<DeliveryVan> deliveryVans) {
		vansOutsideBase = new PriorityQueue<>(new DeliveryVanQueueComparator());
		vansOutsideBase.addAll(deliveryVans);
	}

	public void add(DeliveryVan deliveryVan) {
		vansOutsideBase.add(deliveryVan);
		deliveryVan.getState().printInfoMessage(deliveryVan.getId());
	}

	public void addAll(Collection<DeliveryVan> collection) {
		collection.forEach(this::add);
	}

	public DeliveryVan pool() {
		return vansOutsideBase.poll();
	}

	public boolean isEmpty() {
		return vansOutsideBase.isEmpty();
	}

}
