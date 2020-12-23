package aoc.day23;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day23 extends DayBase {

	public static void main(String[] args) {
		Day23 day = new Day23();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	private List<Integer> numbers;

	@Override
	protected void processInput() {
		numbers = new LinkedList<>();
		if (input.size() > 0) {
			String line = input.get(0);
			List<Integer> collect = line.chars().boxed().mapToInt(Integer::intValue).boxed().map(c -> c - 48).collect(Collectors.toList());
			numbers.addAll(collect);
		}
	}

	@Override
	protected void reset() {
		currentCup = 0;
	}

	int currentCup;

	private void move() {
		int currentCupIndex = findIndex(currentCup);
		int next1Index = (currentCupIndex + 1) % numbers.size();
		int next2Index = (currentCupIndex + 2) % numbers.size();
		int next3Index = (currentCupIndex + 3) % numbers.size();
		int next4Index = (currentCupIndex + 4) % numbers.size();
		int next1 = numbers.get(next1Index);
		int next2 = numbers.get(next2Index);
		int next3 = numbers.get(next3Index);
		int next4 = numbers.get(next4Index);

		removeItemsAtIndexes(next1Index, next2Index, next3Index);

		int destinationIndex = findDestinationIndex(currentCup - 1);

//		System.out.printf("current %d\npickup %d %d %d\ndestination %d\n",currentCup, next1, next2, next3, numbers.get(destinationIndex));

		numbers.add(destinationIndex + 1, next3);
		numbers.add(destinationIndex + 1, next2);
		numbers.add(destinationIndex + 1, next1);
		this.currentCup = numbers.get(findIndex(next4));
	}

	private void removeItemsAtIndexes(int next1Index, int next2Index, int next3Index) {
		List<Integer> indexesToRemove = new ArrayList<>();
		indexesToRemove.add(next1Index);
		indexesToRemove.add(next2Index);
		indexesToRemove.add(next3Index);
		indexesToRemove = indexesToRemove.stream().sorted().collect(Collectors.toList());
		for (int i = indexesToRemove.size() - 1; i >= 0 ; i--) {
			numbers.remove((int) indexesToRemove.get(i));
		}
	}

	private int findIndex(int currentCup) {
		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i) == currentCup) {
				return i;
			}
		}
		return -1;
	}

	private int findDestinationIndex(int valueToFind) {
		int max = numbers.stream().mapToInt(Integer::intValue).max().orElseThrow();

		while (true) {

			if (valueToFind < 1) {
				valueToFind = max;
			}

			for (int i = 0; i < numbers.size(); i++) {
				if (valueToFind == numbers.get(i)) {
					return i;
				}
			}
			valueToFind--;
		}
	}

	@Override
	protected void puzzle1() {
		System.out.println(numbers);
		currentCup = numbers.get(0);
		for (int i = 0; i < 100; i++) {
			move();
		}
		System.out.println(numbers);
	}

	@Override
	protected void puzzle2() {

	}
}
