package aoc.day9;

import java.util.stream.LongStream;

import aoc.DayBase;

public class Day9 extends DayBase {

	private int wrongIndex = -1;

	public static void main(String[] args) {
		new Day9().run();
		// 36845998
		// 4830226
	}

	@Override
	protected void processInput() {
		System.out.println("input length = " + input.size());
	}

	@Override
	protected void puzzle1() {
		findWrong();
	}

	private void findWrong() {
		int preamble = 25;
		for (int i = preamble; i < inputLong.size(); i++) {
			long number = inputLong.get(i);
			boolean found = false;
			for (int j = i - 1; j >= i - preamble; j--) {
				for (int k = i - 1; k >= i - preamble; k--) {
					if (j == k) {
						continue;
					}
					if (inputLong.get(j) + inputLong.get(k) == number) {
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
		for (int i = 0; i < inputLong.size(); i++) {
			if (i == wrongIndex) {
				continue;
			}

			long sum = 0;
			for (int j = i; j < Math.min(i + searchLength, inputLong.size()); j++) {
				if (j == wrongIndex) {
					continue;
				}

				sum += parseLong(input.get(j));

				if (sum == inputLong.get(wrongIndex)) {
					long min = LongStream.range(i, j + 1).map(k -> inputLong.get((int) k)).min().orElseThrow();
					long max = LongStream.range(i, j + 1).map(k -> inputLong.get((int) k)).max().orElseThrow();
					System.out.println("sum of min + max = " + (min + max) + " length = " + (j - i + 1));
					LongStream.range(i, j + 1).map(k -> inputLong.get((int) k)).forEach(System.out::println);
				}
			}
		}
	}
}