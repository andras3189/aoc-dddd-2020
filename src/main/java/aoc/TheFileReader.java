package aoc;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
			URL resource = TheFileReader.class.getClassLoader().getResource(resourcePath);
			if (resource == null) {
				System.out.println("File not found: " + filename);
				return null;
			}
			Path path = Paths.get(resource.toURI());

			Stream<String> lines = Files.lines(path);
			List<String> input = lines.collect(Collectors.toList());
			lines.close();
			return input;
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	 }

	public static List<List<String>> readFileMultiLineInput(String filename) {
		try {
			String resourcePath = filename + ".input";
			URL resource = TheFileReader.class.getClassLoader().getResource(resourcePath);
			if (resource == null) {
				System.out.println("File not found: " + filename);
				return null;
			}
			Path path = Paths.get(resource.toURI());

			Stream<String> lines = Files.lines(path);
			List<String> input = lines.collect(Collectors.toList());

			List<List<String>> outputs = new ArrayList<>();
			List<String> oneOutput = new ArrayList<>();

			for (String line : input) {
				if ("".equals(line.trim()) && !oneOutput.isEmpty()) {
					outputs.add(oneOutput);
					oneOutput = new ArrayList<>();
				} else {
					oneOutput.add(line);
				}
			}
			if (!oneOutput.isEmpty()) {
				outputs.add(oneOutput);
			}

			lines.close();
			return outputs;
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
