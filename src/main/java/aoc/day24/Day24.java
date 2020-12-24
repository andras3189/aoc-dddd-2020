package aoc.day24;

import static aoc.day24.Day24.Bound.MAX;
import static aoc.day24.Day24.Bound.MIN;
import static aoc.day24.Day24.Coord.X;
import static aoc.day24.Day24.Coord.Y;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day24 extends DayBase {

	enum Bound {
		MIN,MAX
	}

	enum Coord {
		X,Y
	}

	public static void main(String[] args) {
		Day24 day = new Day24();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	Set<String> blacks;
	Set<String> newBlacks;
	int N = 200;

	@Override
	protected void processInput() {
		blacks = new HashSet<>();

		for (String line : input) {
			List<Integer> lineChars = line.chars().boxed().collect(Collectors.toList());

			Coordinate finalCoordinate = new Coordinate();
			for (int i = 0; i < lineChars.size(); i++) {
				Coordinate coordinate = new Coordinate();
				Integer currentChar = lineChars.get(i);
				int nextChar = i < lineChars.size() - 1 ? lineChars.get(i + 1) : '-';
				if (currentChar == 's') {
					if (nextChar == 'e') {
						coordinate.x = 1;
						coordinate.y = -1;
					}
					if (nextChar == 'w') {
						coordinate.x = 0;
						coordinate.y = -1;
					}
					i++;
				}
				if (currentChar == 'n') {
					if (nextChar == 'e') {
						coordinate.x = 0;
						coordinate.y = 1;
					}
					if (nextChar == 'w') {
						coordinate.x = -1;
						coordinate.y = 1;
					}
					i++;
				}
				if (currentChar == 'e') {
					coordinate.x = 1;
					coordinate.y = 0;
				}
				if (currentChar == 'w') {
					coordinate.x = -1;
					coordinate.y = 0;
				}
				finalCoordinate.x += coordinate.x;
				finalCoordinate.y += coordinate.y;
			}
			String finalCoordinateStr = asString(finalCoordinate);
			if (blacks.contains(finalCoordinateStr)) {
				blacks.remove(finalCoordinateStr);
			} else {
				blacks.add(finalCoordinateStr);
			}
		}
	}

	@Override
	protected void reset() {

	}

	@Override
	protected void puzzle1() {
		System.out.println(blacks.size());
	}

	@Override
	protected void puzzle2() {
		int ROUNDS = 100;
		for (int round = 0; round < ROUNDS; round++) {
			newBlacks = new HashSet<>();
			int minY = getBound(Y, MIN);
			int maxY = getBound(Y, MAX);
			int minX = getBound(X, MIN);
			int maxX = getBound(X, MAX);
			for (int y = minY - 1; y <= maxY + 1; y++) {
				for (int x = minX - 1; x <= maxX + 1; x++) {
					int blackNeighbours = countBlackNeighbours(x, y);
					String current = asString(x, y);
					if ((blacks.contains(current) && blackNeighbours == 1) || blackNeighbours == 2) {
						newBlacks.add(current);
					}

				}
			}
			blacks = newBlacks;
		}

		System.out.println(newBlacks.size());
	}

	private int getBound(Coord coord, Bound bound) {
		int boundValue = bound == MAX ? -N : N;
		for (String black : blacks) {
			String str = black.split(",")[coord == Y ? 1 : 0];
			int value = Integer.parseInt(str);
			if (value < boundValue && bound == MIN) {
				boundValue = value;
			}
			if (boundValue < value && bound == MAX) {
				boundValue = value;
			}
		}
		return boundValue;
	}

	private int countBlackNeighbours(int x, int y) {
		int count = 0;
		count += blacks.contains(asString(x - 1, y + 1)) ? 1 : 0;
		count += blacks.contains(asString(x, y + 1)) ? 1 : 0;
		count += blacks.contains(asString(x + 1, y)) ? 1 : 0;
		count += blacks.contains(asString(x + 1, y - 1)) ? 1 : 0;
		count += blacks.contains(asString(x, y - 1)) ? 1 : 0;
		count += blacks.contains(asString(x - 1, y)) ? 1 : 0;
		return count;

	}

	private String asString(Coordinate coordinate) {
		return coordinate.x + "," + coordinate.y;
	}

	private String asString(int x, int y) {
		return x + "," + y;
	}
}
