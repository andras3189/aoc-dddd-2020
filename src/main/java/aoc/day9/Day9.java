package aoc.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import aoc.TheDayBase;

public class Day9 extends TheDayBase {

	public static List<Long> longInput = new ArrayList<>();

	private int wrongIndex = -1;

	public Day9(boolean multiLineInput) {
		super(multiLineInput);
		//input = readFile("day9/dummy");
	}

	@Override
	protected String getInputFilename() {
		return "day9/day9";
	}

	public static void main(String[] args) {
		Day9 day = new Day9(false);
		day.processInput();
		day.puzzle1(); // 36845998
		day.puzzle2(); // 4830226
	}

	@Override
	protected void processInput() {
		System.out.println("input length = " + input.size());
		longInput = input.stream().map(this::parseLong).collect(Collectors.toList());
	}

	@Override
	protected void puzzle1() {
		findWrong();
	}

	private void findWrong() {
		int preamble = 25;
		for (int i = preamble; i < longInput.size(); i++) {
			long number = longInput.get(i);
			boolean found = false;
			for (int j = i - 1; j >= i - preamble; j--) {
				for (int k = i - 1; k >= i - preamble; k--) {
					if (j == k) {
						continue;
					}
					if (longInput.get(j) + longInput.get(k) == number) {
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}

			}
			if (!found) {
				System.out.println(number + " index = " + i);
				wrongIndex = i;
			}
		}
	}

	@Override
	protected void puzzle2() {
		int searchLength = 100;
		for (int i = 0; i < longInput.size(); i++) {
			if (i == wrongIndex) {
				continue;
			}

			long sum = 0;
			for (int j = i; j < Math.min(i + searchLength, longInput.size()); j++) {
				if (j == wrongIndex) {
					continue;
				}

				sum += parseLong(input.get(j));

				if (sum == longInput.get(wrongIndex)) {
					long min = LongStream.range(i, j + 1).map(k -> longInput.get((int) k)).min().orElseThrow();
					long max = LongStream.range(i, j + 1).map(k -> longInput.get((int) k)).max().orElseThrow();
					System.out.println("sum of min + max = " + (min + max) + " length = " + (j - i + 1));
					LongStream.range(i,j+1).map(k -> longInput.get((int) k)).forEach(System.out::println);
				}
			}
		}
	}
}