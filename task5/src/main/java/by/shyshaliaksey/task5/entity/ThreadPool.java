package by.shyshaliaksey.task5.entity;

import java.util.ArrayDeque;
import java.util.Queue;

public class ThreadPool {

	private static final int DEFAULT_POOL_SIZE = LogisticsBaseSingleton.TERMINAL_COUNT;
	private static ThreadPool instance = new ThreadPool();
	private Queue<DeliveryVan> queue;
	
	private ThreadPool() {
		queue = new ArrayDeque<>(DEFAULT_POOL_SIZE);
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			
		}
	}
	
	public static ThreadPool getInstance() {
		return instance;
	}
	
	public DeliveryVan getDeliveryVan() {
		DeliveryVan deliveryVan = queue.peek();
		return deliveryVan;
	}
	
	public void releaseDeliveryVan(DeliveryVan deliveryVan) {
		queue.remove(deliveryVan);
	}
	
}
