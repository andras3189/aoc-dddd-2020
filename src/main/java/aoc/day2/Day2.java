package aoc.day2;

import static aoc.TheFileReader.readFile;

import java.util.List;

public class Day2 {

	public static void main(String[] args) {
		solvePuzzle1();
		solvePuzzle2();
	}

	private static void solvePuzzle2() {
		IntWrapper valid = new IntWrapper();
		getInput().forEach(line -> {
			String pw = getPw(line);
			char ch = getCharacter(line);
			if (pw.charAt(getMin(line) - 1) == ch ^ pw.charAt(getMax(line) - 1) == ch) {
				valid.count++;
			}
		});
		System.out.println(valid.count);
	}

	private static void solvePuzzle1() {
		IntWrapper valid = new IntWrapper();
		getInput().forEach(line -> {
			long usageCount = getPw(line).chars().filter(c -> c == getCharacter(line)).count();
			if (getMin(line) <= usageCount && usageCount <= getMax(line)) {
				valid.count++;
			}
		});
		System.out.println(valid.count);
	}

	public static int getMin(String line) {
		return Integer.parseInt(getRange(line)[0]);
	}

	public static int getMax(String line) {
		return Integer.parseInt(getRange(line)[1]);
	}

	public static String[] getRange(String line) {
		String[] split = line.split("\\s+");
		return split[0].split("-");
	}

	public static char getCharacter(String line) {
		String[] split = line.split("\\s+");
		return split[1].toCharArray()[0];
	}

	public static String getPw(String line) {
		String[] split = line.split("\\s+");
		return split[2];
	}

	public static class IntWrapper {
		public int count = 0;
	}

	public static List<String> getInput() {
		List<String> input = readFile("day2/day2");
		assert input != null;
		return input;
	}
}
