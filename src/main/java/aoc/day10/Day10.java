package aoc.day10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day10 extends DayBase {

	private Map<Integer, Long> memory = new HashMap<>();

	public static void main(String[] args) {
		new Day10().run();
	}

	@Override
	protected void processInput() {
	}

	@Override
	protected void puzzle1() {
		long current = 0;
		List<Long> sorted = inputLong.stream().sorted().collect(Collectors.toList());

		int n1 = 0;
		int n2 = 0;
		for (Long number : sorted) {
			if (number == current + 1) {
				n1++;
			} else if (number == current + 3) {
				n2++;
			}
			current = number;
		}
		n2++;
		System.out.println(n1 * n2);
	}

	@Override
	protected void puzzle2() {
		List<Long> sorted = inputLong.stream().sorted().collect(Collectors.toList());

		sorted.add(0, 0L);
		sorted.add(sorted.size(), sorted.get(sorted.size() - 1) + 3);

		System.out.println(getCount(sorted, 0));
	}

	private long getCount(List<Long> in, int s) {
		if (in.size() - s == 1) {
			return 1;
		}
		if (in.size() - s == 2) {
			return 1;
		}

		long n1 = in.get(s);
		long n3 = in.get(s+2);
		long n4 = in.size() - s >= 4 ? in.get(s+3) : -1;

		long t1 = getCountFromMemory(in, s + 1);
		long t2 = getCountFromMemory(in, s + 2);

		if ((n4 != -1) && n4 - n1 <= 3) {
			long t3 = getCountFromMemory(in, s + 3);
			return t1 + t2 + t3;
		} else if (n3 - n1 <= 3) {
			return t1 + t2;
		}

		return t1;
	}

	private long getCountFromMemory(List<Long> in, int s) {
		if (!memory.containsKey(s)) {
			memory.put(s, getCount(in, s));
		}
		return memory.get(s);
	}
}