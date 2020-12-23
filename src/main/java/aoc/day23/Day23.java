package aoc.day23;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day23 extends DayBase {

	private List<Integer> numbers;
	private List<Cup> cups;
	Cup theCurrentCup;

	public static void main(String[] args) {
		Day23 day = new Day23();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void processInput() {
		numbers = new LinkedList<>();
		if (input.size() > 0) {
			String line = input.get(0);
			List<Integer> collect = line.chars().boxed().mapToInt(Integer::valueOf).boxed().map(c -> c - 48).collect(Collectors.toList());
			numbers.addAll(collect);
		}
	}

	@Override
	protected void reset() {
		cups = new ArrayList<>();
	}

	@Override
	protected void puzzle1() {
		int ITERATIONS = 100;
		int max = numbers.stream().mapToInt(Integer::valueOf).max().orElseThrow();

		Cup prev = null;
		for (int i = 0; i < max; i++) {
			Integer current = numbers.get(i);
			Cup cup = new Cup();
			cup.value = current;
			if (prev != null) {
				prev.next = cup;
			}
			prev = cup;
			cups.add(cup);
		}
		Cup firstCupInList = cups.get(0);
		Cup lastCupInList = prev;
		Cup cupWithValue1 = findCupForValue(1);

		for (int i = 1; i < max; i++) {
			Cup cupForValue = findCupForValue(i + 1);
			cupForValue.lowerByOneCup = findCupForValue(i);
		}

		lastCupInList.next = firstCupInList;
		cupWithValue1.lowerByOneCup = findCupForValue(max);

		theCurrentCup = firstCupInList;

		for (int i = 0; i < ITERATIONS; i++) {
			move();
		}

		Cup current = cupWithValue1.next;
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < max; i++) {
			if (cupWithValue1 == current) {
				break;
			}
			result.append(current.value);
			current = current.next;
		}
		System.out.println(result);
	}

	@Override
	protected void puzzle2() {

		int ITERATIONS = 10000000;
		int OVERALL_CUP_NUMBER = 1000000;
		processInput();
		int max = numbers.stream().mapToInt(Integer::valueOf).max().orElseThrow();

		Cup prev = null;
		for (int i = 0; i < max; i++) {
			Integer current = numbers.get(i);
			Cup cup = new Cup();
			cup.value = current;
			if (prev != null) {
				prev.next = cup;
			}
			prev = cup;
			cups.add(cup);
		}
		Cup firstCupInList = cups.get(0);
		Cup lastCupInOriginalList = prev;
		Cup cupWithValue1 = findCupForValue(1);

		for (int i = 1; i < max; i++) {
			Cup cupForValue = findCupForValue(i + 1);
			cupForValue.lowerByOneCup = findCupForValue(i);
		}

		Cup lastCup;
		Cup firstCupInAdditionalList = null;
		boolean first = true;
		for (int i = max + 1; i <= OVERALL_CUP_NUMBER; i++) {
			Cup cup = new Cup();
			cup.value = i;
			if (first) {
				first = false;
				lastCupInOriginalList.next = cup;
				cup.lowerByOneCup = findCupForValue(max);
			} else {
				prev.next = cup;
				cup.lowerByOneCup = prev;
			}
			prev = cup;
			cups.add(cup);
			if (firstCupInAdditionalList == null) {
				firstCupInAdditionalList = cup;
			}
		}

		lastCupInOriginalList.next = firstCupInAdditionalList;
		lastCup = prev;
		lastCup.next = firstCupInList;
		cupWithValue1.lowerByOneCup = lastCup;

		theCurrentCup = firstCupInList;

		for (int i = 0; i < ITERATIONS; i++) {
			move();
		}

		Cup sol1 = cupWithValue1.next;
		Cup sol2 = cupWithValue1.next.next;
		System.out.printf("%d,%d,%d\n", sol1.value, sol2.value, sol1.value * (long) sol2.value);
	}

	private Cup findCupForValue(int value) {
		for (Cup cup : cups) {
			if (cup.value == value) {
				return cup;
			}
		}
		return null;

	}

	private void move() {
		Cup next1 = theCurrentCup.next;
		Cup next2 = theCurrentCup.next.next;
		Cup next3 = theCurrentCup.next.next.next;
		Cup next4 = theCurrentCup.next.next.next.next;

		theCurrentCup.next = next4;

		Cup lowerByOneFromCurrentCup = theCurrentCup.lowerByOneCup;
		while (lowerByOneFromCurrentCup.value.equals(next1.value) ||
				lowerByOneFromCurrentCup.value.equals(next2.value) || lowerByOneFromCurrentCup.value.equals(next3.value)) {
			lowerByOneFromCurrentCup = lowerByOneFromCurrentCup.lowerByOneCup;
		}

		Cup oldNext = lowerByOneFromCurrentCup.next;
		lowerByOneFromCurrentCup.next = next1;
		next3.next = oldNext;

		theCurrentCup = next4;
	}

	private static class Cup {
		Cup next;
		Cup lowerByOneCup;
		Integer value;
	}
}
