package aoc.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.DayBase;

public class Day22 extends DayBase {

	public static void main(String[] args) {
		Day22 day = new Day22();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	private final List<Integer> inputPlayer1 = new ArrayList<>();
	private final List<Integer> inputPlayer2 = new ArrayList<>();
	private final Map<String, Integer> memory = new HashMap<>();


	@Override
	protected void processInput() {
		int player = 1;
		inputPlayer1.clear();
		inputPlayer2.clear();

		for (String line : input) {
			if ("".equals(line)) {
				continue;
			}
			if (line.equals("Player 1:")) {
				player = 1;
			} else if (line.equals("Player 2:")) {
				player = 2;
			} else if (player == 1) {
				inputPlayer1.add(parseInt(line));
			} else {
				inputPlayer2.add(parseInt(line));
			}
		}
	}

	@Override
	protected void reset() {
		memory.clear();
	}

	@Override
	protected void puzzle1() {
		List<Integer> p1 = new ArrayList<>(this.inputPlayer1);
		List<Integer> p2 = new ArrayList<>(this.inputPlayer2);

		while (p1.size() != 0 && p2.size() != 0) {
			int c1 = p1.get(0);
			int c2 = p2.get(0);

			if (c1 > c2) {
				p1.add(c1);
				p1.add(c2);
			} else {
				p2.add(c2);
				p2.add(c1);
			}
			p1.remove(0);
			p2.remove(0);
		}
		printSum(p1, p2);
	}

	@Override
	protected void puzzle2() {
		List<Integer> p1 = new ArrayList<>(this.inputPlayer1);
		List<Integer> p2 = new ArrayList<>(this.inputPlayer2);

		playCards(p1, p2);
		printSum(p1, p2);
	}

	private int playCards(List<Integer> p1, List<Integer> p2) {
		String gameHash = hash(p1, p2);

		if (memory.containsKey(gameHash)) {
			return memory.get(gameHash);
		}

		Set<String> previousRounds = new HashSet<>();
		while (p1.size() != 0 && p2.size() != 0) {
			int roundWinner;

			String roundHash = hash(p1, p2);
			int c1 = p1.remove(0);
			int c2 = p2.remove(0);

			if (previousRounds.contains(roundHash)) {
				return 1;
			}
			previousRounds.add(roundHash);

			if (p1.size() >= c1 && p2.size() >= c2) {
				List<Integer> np1 = copy(p1, c1);
				List<Integer> np2 = copy(p2, c2);
				roundWinner = playCards(np1, np2);
			} else {
				roundWinner = c1 > c2 ? 1 : 2;
			}

			if (roundWinner == 1) {
				p1.add(c1);
				p1.add(c2);
			} else {
				p2.add(c2);
				p2.add(c1);
			}
		}
		memory.put(gameHash, p1.size() > 0 ? 1 : 2);
		return memory.get(gameHash);
	}

	private List<Integer> copy(List<Integer> source, int count) {
		List<Integer> copy = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			copy.add(source.get(i));
		}
		return copy;
	}

	private String hash(List<Integer> p1, List<Integer> p2) {
		StringBuilder b = new StringBuilder();
		for (Integer c : p1) {
			b.append(c);
			b.append(",");
		}
		b.append("-");
		for (Integer c : p2) {
			b.append(c);
			b.append(",");
		}
		return b.toString();
	}

	private void printSum(List<Integer> p1, List<Integer> p2) {
		long sum = 0;
		if (p2.size() > 0) {
			for (int i = 0; i < p2.size(); i++) {
				sum += (long) p2.get(i) * (p2.size() - i);
			}
		}

		if (p1.size() > 0) {
			for (int i = 0; i < p1.size(); i++) {
				sum += (long) p1.get(i) * (p1.size() - i);
			}
		}
		System.out.println(sum);
	}
}
