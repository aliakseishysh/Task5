package by.shyshaliaksey.task5.service;

public enum ContainerTime {

	TO_LOAD(1),
	TO_UNLOAD(2);

	private int value;
	
	private ContainerTime(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
