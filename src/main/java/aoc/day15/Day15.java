package aoc.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.DayBase;

public class Day15 extends DayBase {

	public static int[] startNumbers;

	public List<Integer> numbers;

	public static void main(String[] args) {
		Day15 day = new Day15();
//		day.setOnlyDummyInput(true);
		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected List<String> getAdditionalInputFileNames() {
		return Arrays.asList("dummy2");
	}

	@Override
	protected void processInput() {
		String line = input.get(0);
		String[] split = line.split(",");
		startNumbers = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			startNumbers[i] = (Integer.parseInt(split[i]));
		}
	}

	@Override
	protected void reset() {
		numbers = new ArrayList<>();
		memory = new HashMap<>();
	}

	public Map<Long, Long> memory = new HashMap<>();

	@Override
	protected void puzzle1() {
		for (int startNumber : startNumbers) {
			numbers.add(startNumber);
		}
		numbers.add(0);
		while (numbers.size() < 2020) {
			int prev = numbers.get(numbers.size() - 1);
			numbers.add(numbers.size() - 1 - findLastOccurrence(prev));

		}
		System.out.println(numbers.get(numbers.size() - 1));
	}

	private int findLastOccurrence(int number) {
		for (int i = numbers.size() - 2; i >= 0; i--) {
			if (numbers.get(i) == number) {
				return i;
			}
		}
		return numbers.size() - 1;
	}

	@Override
	protected void puzzle2() {
		for (int i = 0; i < startNumbers.length; i++) {
			memory.put((long) startNumbers[i], (long) i);
		}
		if (!memory.containsKey(0L)) {
			memory.put(0L, (long) startNumbers.length);
		}
		long prev;
		long latest = 0;

		long index = startNumbers.length + 1;
		while (index < 30000000) {
			prev = latest;
			Long lastIndex = memory.getOrDefault(prev, index - 1);
			latest = index - lastIndex - 1;
			memory.put(prev, index - 1);
			index++;
		}
		System.out.println(latest);
	}
}
