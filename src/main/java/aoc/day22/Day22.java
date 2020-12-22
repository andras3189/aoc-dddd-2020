package aoc.day22;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day22 extends DayBase {

	public static void main(String[] args) {
		Day22 day = new Day22();
		// day.setOnlyDummyInput(true);
		// day.setOnlyRealInput(true);
		day.run();
	}

	private List<Integer> deck1 = new ArrayList<>();
	private List<Integer> deck2 = new ArrayList<>();
	private List<Integer> sub1 = new ArrayList<>();
	private List<Integer> sub2 = new ArrayList<>();
	private List<List<String>> allStates = new ArrayList<>();

	@Override
	protected void processInput() {
		int player = 1;
		deck1.clear();
		deck2.clear();

		for (String line : input) {
			if ("".equals(line)) {
				continue;
			}
			if (line.equals("Player 1:")) {
				player = 1;
			} else if (line.equals("Player 2:")) {
				player = 2;
			} else if (player == 1) {
				deck1.add(parseInt(line));
			} else if (player == 2) {
				deck2.add(parseInt(line));
			}
		}

	}

	@Override
	protected void reset() {
		this.processInput();
	}

	@Override
	protected void puzzle1() {
		// System.out.println();

		while (deck1.size() != 0 && deck2.size() != 0) {
			int c1 = deck1.get(0);
			int c2 = deck2.get(0);

			if (c1 > c2) {
				deck1.add(c1);
				deck1.add(c2);
			} else {
				deck2.add(c2);
				deck2.add(c1);
			}
			deck1.remove(0);
			deck2.remove(0);
		}
		// System.out.println();

		long sum = 0;
		if (deck2.size() > 0) {
			for (int i = 0; i < deck2.size(); i++) {
				sum += (long) deck2.get(i) * (deck2.size() - i);
			}
		}

		if (deck1.size() > 0) {
			for (int i = 0; i < deck1.size(); i++) {
				sum += (long) deck1.get(i) * (deck1.size() - i);
			}
		}
		// System.out.println(sum);
	}

	@Override
	protected void puzzle2() {
		int winner = recCombat(deck1, deck2, 0);

		long sum = 0;
		if (winner == 2) {
			for (int i = 0; i < deck2.size(); i++) {
				sum += (long) deck2.get(i) * (deck2.size() - i);
			}
		}

		if (winner == 1) {
			for (int i = 0; i < deck1.size(); i++) {
				sum += (long) deck1.get(i) * (deck1.size() - i);
			}
		}
		System.out.println(sum);

	}

	private int recCombat(List<Integer> deck1, List<Integer> deck2, int depth) {

		allStates.add(new ArrayList<>());
		List<String> gameStates = allStates.get(allStates.size()-1);

		int round = 0;
		while (deck1.size() != 0 && deck2.size() != 0) {
			// System.out.println("Game " + allStates.size() + ", Round " + round);

			String config = deck1.toString() + "|" + deck2.toString();
			if (gameStates.contains(config)) {
				return 1;
			}
			gameStates.add(config);

			int c1 = deck1.get(0);
			int c2 = deck2.get(0);

			sub1 = deck1.subList(1, deck1.size()).stream().collect(Collectors.toList());
			sub2 = deck2.subList(1, deck2.size()).stream().collect(Collectors.toList());

			int roundWinner = 0;
			if (sub1.size() >= c1 && sub2.size() >= c2) {
				roundWinner = recCombat(sub1, sub2, depth + 1);
			} else {
				roundWinner = c1 > c2 ? 1 : 2;
			}

			if (roundWinner == 1) {
				deck1.add(c1);
				deck1.add(c2);
			} else if (roundWinner == 2) {
				deck2.add(c2);
				deck2.add(c1);
			} else {
				System.out.println("Youdonefuckedup! Winner is " + roundWinner);
			}
			deck1.remove(0);
			deck2.remove(0);
			round++;
		}

		int gameWinner = deck2.size() == 0 ? 1 : 2;
		return gameWinner;
	}
}
