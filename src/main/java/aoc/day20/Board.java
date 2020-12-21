package aoc.day20;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Board {

	private final PieceInstance[][] pieces;
	private final int pieceSize;
	private final int boardSize;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int maxY = Integer.MIN_VALUE;

	public Board(int pieceSize, int boardSize) {
		this.pieceSize = pieceSize;
		this.boardSize = boardSize;
		pieces = new PieceInstance[boardSize][];
		for (int y = 0; y < boardSize; y++) {
			pieces[y] = new PieceInstance[boardSize];
		}
	}

	public Board copy() {
		Board copy = new Board(pieceSize, boardSize);
		for (int y = minY; y < maxY + 1; y++) {
			for (int x = minX; x < maxX + 1; x++) {
				copy.pieces[y][x] = pieces[y][x];
			}
		}
		copy.minX = minX;
		copy.minY = minY;
		copy.maxX = maxX;
		copy.maxY = maxY;
		return copy;
	}

	public void place(int y, int x, PieceInstance pieceInstance) {
		pieces[y][x] = pieceInstance;
		if (y > maxY) {
			maxY = y;
		}
		if (y < minY) {
			minY = y;
		}
		if (x > maxX) {
			maxX = x;
		}
		if (x < minX) {
			minX = x;
		}
	}

	public Set<Coordinate> findEmptyPlacesWithAnyNeighbours() {
		Set<Coordinate> emptyPlacesWithNeighbours = new HashSet<>();
		for (int y = minY - 1; y < maxY + 2; y++) {
			for (int x = minX - 1; x < maxX + 2; x++) {
				if (hasNeighbours(y, x) && isEmpty(y, x) && stillSquare(y, x)) {
					emptyPlacesWithNeighbours.add(new Coordinate(x, y));
				}
			}
		}
		return emptyPlacesWithNeighbours;
	}

	private boolean stillSquare(int y, int x) {
		if ((getXSize() == boardSize / 2) && (x < minX || maxX < x)) {
			return false;
		}
		if ((getYSize() == boardSize / 2) && (y < minY || maxY < y)) {
			return false;
		}
		return true;
	}

	public Set<Coordinate> findEmptyPlacesWithMostNeighbours() {
		int maxNeighbours = findMaxNeighbourCountOfEmptyPlaces();
		Set<Coordinate> emptyPlacesWithMostNeighbours = new HashSet<>();
		for (int y = minY - 1; y < maxY + 2; y++) {
			for (int x = minX - 1; x < maxX + 2; x++) {
				if (getNeighbourCount(y, x) == maxNeighbours && isEmpty(y, x) && stillSquare(y, x)) {
					emptyPlacesWithMostNeighbours.add(new Coordinate(x, y));
				}
			}
		}
		return emptyPlacesWithMostNeighbours;
	}

	private int findMaxNeighbourCountOfEmptyPlaces() {
		int maxNeighbours = 0;
		for (int y = minY - 1; y < maxY + 2; y++) {
			for (int x = minX - 1; x < maxX + 2; x++) {
				if (getNeighbourCount(y, x) > maxNeighbours && isEmpty(y, x) && stillSquare(y, x)) {
					maxNeighbours = getNeighbourCount(y, x);
				}
			}
		}
		return maxNeighbours;
	}

	public Set<PieceInstance> getMatches(Coordinate emptyPlace, Set<Piece> remainingPieces) {
		Set<PieceInstance> matches = new HashSet<>();
		for (Piece remainingPiece : remainingPieces) {
			matches.addAll(getMatchingInstances(emptyPlace, remainingPiece));
		}
		return matches;
	}

	public Set<PieceInstance> getMatchingInstances(Coordinate emptyPlace, Piece matchingPiece) {
		if (!isEmpty(emptyPlace.y, emptyPlace.x)) {
			System.out.println("not empty place");
		}
		int y = emptyPlace.y;
		int x = emptyPlace.x;
		int topNeed = -1;
		int bottomNeed = -1;
		int leftNeed = -1;
		int rightNeed = -1;

		if (!isEmpty(y - 1, x)) {
			topNeed = pieces[y - 1][x].getBottom();
		}
		if (!isEmpty(y + 1, x)) {
			bottomNeed = pieces[y + 1][x].getTop();
		}
		if (!isEmpty(y, x - 1)) {
			leftNeed = pieces[y][x - 1].getRight();
		}
		if (!isEmpty(y, x + 1)) {
			rightNeed = pieces[y][x + 1].getLeft();
		}

		Set<PieceInstance> matches = new HashSet<>();
		List<PieceInstance> instances = matchingPiece.getInstances();
		for (PieceInstance instance : instances) {
			if (instance.fulfills(topNeed, bottomNeed, leftNeed, rightNeed)) {
				matches.add(instance);
			}
		}
		return matches;
	}

	public boolean isSquare() {
		for (int y = minY; y < maxY + 1; y++) {
			for (int x = minX; x < maxX + 1; x++) {
				if (isEmpty(y, x)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean wontBeSquare() {
		return getXSize() > boardSize / 2 || getYSize() > boardSize / 2;
	}

	private int getXSize() {
		return maxX - minX + 1;
	}

	private int getYSize() {
		return maxY - minY + 1;
	}

	private boolean isEmpty(int y, int x) {
		if (y >= minY && y <= maxY && x >= minX && x <= maxX) {
			return pieces[y][x] == null;
		}
		return true;
	}

	private boolean hasNeighbours(int y, int x) {
		return !isEmpty(y - 1, x) || !isEmpty(y + 1, x) || !isEmpty(y, x - 1) || !isEmpty(y, x + 1);
	}

	private int getNeighbourCount(int y, int x) {
		return (!isEmpty(y - 1, x) ? 1 : 0) + (!isEmpty(y + 1, x) ? 1 : 0) + (!isEmpty(y, x - 1) ? 1 : 0) + (!isEmpty(y, x + 1) ? 1 : 0);
	}

	public boolean equalsWithBoard(Board board) {
		if (minY != board.minY || maxY != board.maxY || minX != board.minX || maxX != board.maxX) {
			return false;
		}
		for (int y = minY; y < maxY + 1; y++) {
			for (int x = minX; x < maxX + 1; x++) {
				if (board.pieces[y][x] != pieces[y][x]) {
					return false;
				}
			}
		}
		return true;
	}

	public int getId(boolean top, boolean left) {
		int y = top ? minY : maxY;
		int x = left ? minX : maxX;
		if (isEmpty(y, x)) {
			System.out.println("Corner is empty x=" + x + ",y=" + y);
		}
		return pieces[y][x].getMaster().getId();
	}

	@Override
	public String toString() {
		return print(true, true);
	}

	public String print(boolean humanFriendly, boolean showBorder) {
		StringBuilder builder = new StringBuilder();
		for (int y = minY * pieceSize; y < (maxY + 1) * pieceSize; y++) {
			for (int x = minX; x < maxX + 1; x++) {
				PieceInstance pieceInstance = pieces[y / pieceSize][x];
				if (pieceInstance != null) {
					int lineNumberOfPiece = y % pieceSize;
					String lineOfPiece = "";
					if (humanFriendly) {
						lineOfPiece = pieceInstance.printHumanFriendly(lineNumberOfPiece);
					} else if (showBorder) {
						lineOfPiece = pieceInstance.print(lineNumberOfPiece);
					} else {
						lineOfPiece = pieceInstance.printWithoutBorder(lineNumberOfPiece);
					}
					builder.append(lineOfPiece);
				} else {
					builder.append(" ".repeat(pieceSize));
				}
			}
			if (y < (maxY + 1) * pieceSize - 1) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Board board = (Board) o;
		return equalsWithBoard(board);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(minX, minY, maxX, maxY);

		for (int y = minY; y < maxY + 1; y++) {
			result = 31 * result + Arrays.deepHashCode(pieces[y]);
		}
		return result;
	}
}
