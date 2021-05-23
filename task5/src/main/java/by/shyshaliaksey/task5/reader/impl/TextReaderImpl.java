package by.shyshaliaksey.task5.reader.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.shyshaliaksey.task5.exception.MultithreadingTaskException;
import by.shyshaliaksey.task5.reader.TextReader;


/**
 * 
 * @author AlekseyShysh
 *
 */
public class TextReaderImpl implements TextReader {

	private static Logger rootLogger = LogManager.getLogger();
	
	/**
	 * Method to read all lines in the file WITHOUT ANY VALIDATION
	 * 
	 * @param path to file
	 * @return List<String> with all lines in the file
	 * @throws MultithreadingTaskException 
	 */
	public List<String> readAllLines(String absolutePath) throws MultithreadingTaskException {
		Path path = Paths.get(absolutePath);
		List<String> arrayList = new ArrayList<>();
		try (Stream<String> stream = Files.lines(path)) {
			arrayList = stream.collect(Collectors.toList());
		} catch (IOException e) {
			rootLogger.log(Level.ERROR, "Problem with file reading by path: \'{}\' occured", path);
			throw new MultithreadingTaskException("Problem with file reading by path :" + path + " occured");
		}
		return arrayList;
	}

	


}
