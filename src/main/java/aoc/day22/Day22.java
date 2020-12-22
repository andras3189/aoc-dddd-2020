package aoc.day22;

import java.util.ArrayList;
import java.util.List;

import aoc.DayBase;

public class Day22 extends DayBase {

	public static void main(String[] args) {
		Day22 day = new Day22();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	private List<Integer> p1 = new ArrayList<>();
	private List<Integer> p2 = new ArrayList<>();

	@Override
	protected void processInput() {
		int player = 1;
		p1.clear();
		p2.clear();

		for (String line : input) {
			if ("".equals(line)) {
				continue;
			}
			if (line.equals("Player 1:")) {
				player = 1;
			} else if (line.equals("Player 2:")) {
				player = 2;
			} else if (player == 1) {
				p1.add(parseInt(line));
			} else if (player == 2) {
				p2.add(parseInt(line));
			}
		}

	}

	@Override
	protected void reset() {

	}

	@Override
	protected void puzzle1() {
		System.out.println();

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
		System.out.println();

		long sum = 0;
		if (p2.size() > 0) {
			for (int i = 0; i < p2.size() ; i++) {
				sum += (long) p2.get(i) * (p2.size()-i);
			}
		}

		if (p1.size() > 0) {
			for (int i = 0; i < p1.size() ; i++) {
				sum += (long) p1.get(i) * (p1.size()-i);
			}
		}
		System.out.println(sum);
	}

	@Override
	protected void puzzle2() {

	}
}
