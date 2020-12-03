package aoc.day1;

import static aoc.TheFileReader.readFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day1 {

	public static void main(String[] args) {
		solvePuzzle1();
		solvePuzzle2();
	}

	public static void solvePuzzle1() {
		List<Integer> numbers = getInput();
		List<Long> sols = new ArrayList<>();
		for (Integer n : numbers) {
			for (Integer m : numbers) {
				if (n + m == 2020) {
					sols.add((long) n * m);
				}
			}
		}
		System.out.println(sols.toString());
	}

	public static void solvePuzzle2() {
		List<Integer> numbers = getInput();
		Set<Long> sols = new HashSet<>();
		for (Integer n : numbers) {
			for (Integer m : numbers) {
				for (Integer z : numbers) {
					if (n + m + z == 2020) {
						sols.add((long) n * m * z);
					}
				}
			}
		}
		System.out.println(sols.toString());
	}


	public static List<Integer> getInput() {
		List<String> input = readFile("day1/day1");
		assert input != null;
		return input.stream().map(n -> Integer.valueOf(n.trim())).collect(Collectors.toList());
	}
}
