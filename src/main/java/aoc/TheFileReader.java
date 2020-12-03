package aoc;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TheFileReader {

	public static void main(String[] args) {
		List<String> day1 = readFile("day1/day1");
		System.out.println(day1.toString());
	}

	public static List<String> readFile(String filename) {
		try {
			String resourcePath = filename + ".input";
			Path path = Paths.get(TheFileReader.class.getClassLoader().getResource(resourcePath).toURI());

			Stream<String> lines = Files.lines(path);
			List<String> list = lines.collect(Collectors.toList());
			lines.close();
			return list;
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	 }
}
