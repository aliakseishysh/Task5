package by.shyshaliaksey.task5.reader;

import java.util.List;

import by.shyshaliaksey.task5.exception.MultithreadingTaskException;

public interface TextReader {

	public List<String> readAllLines(String path) throws MultithreadingTaskException;

}
