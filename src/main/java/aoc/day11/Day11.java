package aoc.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day11 extends DayBase {

	private List<String> currentSeats = new ArrayList<>();
	private List<String> updatedSeats = new ArrayList<>();
	private int maxX;
	private int maxY;

    public static void main(String[] args) {
		new Day11().run();
	}

	@Override
	protected void processInput() {
		currentSeats = input.stream().map(String::new).collect(Collectors.toList());
		maxY = input.size();
		maxX = input.get(0).length();
	}

	@Override
	protected void puzzle1() {
		updateUntilSeatsChange(1, 4);
		System.out.println(countOccupiedSeats());
	}

	@Override
	protected void puzzle2() {
		currentSeats = input.stream().map(String::new).collect(Collectors.toList());
		updateUntilSeatsChange(Integer.MAX_VALUE, 5);
		System.out.println(countOccupiedSeats());
	}

	private void updateUntilSeatsChange(int distanceToCheckNeighbours, int neighbourLimit) {
		boolean changed = true;
		while (changed) {
			updatedSeats = currentSeats.stream().map(String::new).collect(Collectors.toList());
			changed = updateSeats(distanceToCheckNeighbours, neighbourLimit);
			currentSeats = updatedSeats;
		}
	}

	private boolean updateSeats(int distanceToCheckNeighbours, int neighbourLimit) {
		boolean changed = false;
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				char currentSeat = currentSeats.get(y).charAt(x);
				int nCount = countNeighbours(y, x, distanceToCheckNeighbours);

				if (nCount == 0 && currentSeat == 'L') {
					replaceChar(y, x, '#');
					changed = true;
				}
				if (nCount >= neighbourLimit && currentSeat == '#') {
					replaceChar(y, x, 'L');
					changed = true;
				}
			}
		}
		return changed;
	}

	private int countNeighbours(int y, int x, int distanceToCheckNeighbours) {
		int nCount = 0;
		for (int dY = -1; dY <= 1; dY++) {
			for (int dX = -1; dX <= 1; dX++) {
				if (dY == 0 && dX == 0) {
					continue;
				}
				nCount += findNeighbourInDirection(y, x, dY, dX, distanceToCheckNeighbours);
			}
		}
		return nCount;
	}

	private int findNeighbourInDirection(int y, int x, int dy, int dx, int maxDistance) {
		int currentX = x + dx;
		int currentY = y + dy;
		int distance = 0;

		while (onBoard(currentY, currentX) && distance < maxDistance) {
			if (getCharSafe(currentY, currentX) == 'L') {
				return 0;
			}
			if (getCharSafe(currentY, currentX) == '#') {
				return 1;
			}

			currentX += dx;
			currentY += dy;
			distance++;
		}
		return 0;
	}

	private char getCharSafe(int y, int x) {
		if (!onBoard(y, x)) {
			return 0;
		}
		return currentSeats.get(y).charAt(x);
	}

	private boolean onBoard(int y, int x) {
		return 0 <= x && x < maxX && 0 <= y && y < maxY;
	}

	private long countOccupiedSeats() {
		return updatedSeats.stream().map(line -> line.chars().filter(ch -> ch == '#').count()).mapToLong(Long::longValue).sum();
	}

	private void replaceChar(int i, int j, char newChar) {
		char[] chars = updatedSeats.get(i).toCharArray();
		chars[j] = newChar;
		updatedSeats.set(i, String.valueOf(chars));
	}
}