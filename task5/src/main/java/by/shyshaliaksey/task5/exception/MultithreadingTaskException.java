package by.shyshaliaksey.task5.exception;

public class MultithreadingTaskException extends Exception {

	private static final long serialVersionUID = -3303410395502061392L;

	public MultithreadingTaskException() {
		super();
	}

	public MultithreadingTaskException(String message) {
		super(message);
	}

	public MultithreadingTaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultithreadingTaskException(Throwable cause) {
		super(cause);
	}
}
