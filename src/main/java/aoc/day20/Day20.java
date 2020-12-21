package aoc.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day20 extends DayBase {

	public static final Pattern tileIdPattern = Pattern.compile("Tile\\s([\\d]*):");
	public static final Pattern pieceLinePattern = Pattern.compile("([.#]+)");
	public static final Pattern monsterLine1 = Pattern.compile("..................#.");
	public static final Pattern monsterLine2 = Pattern.compile("#....##....##....###");
	public static final Pattern monsterLine3 = Pattern.compile(".#..#..#..#..#..#...");
	private static final int MONSTER_WIDTH = 20;
	private static final int MONSTER_HEIGHT = 3;
	private static final int MONSTER_MASS = 15;

	private static final boolean PRINT_SOLUTION_BOARD = false;


	private List<Piece> allPieces;
	private Set<Piece> remainingPieces;
	private Board puzzle1Sol;
	private Set<Board> visitedStates;

	public static void main(String[] args) {
		Day20 day = new Day20();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void processInput() {
		allPieces = new ArrayList<>();
		List<String> pieceLines = new ArrayList<>();
		int id = -1;
		for (String line : input) {
			Matcher m = tileIdPattern.matcher(line);
			if (m.find()) {
				id = parseInt(m.group(1));
			}
			Matcher m2 = pieceLinePattern.matcher(line);
			if (m2.find()) {
				pieceLines.add(line);
			}
			if ("".equals(line)) {
				allPieces.add(new Piece(id, pieceLines));
				pieceLines.clear();
			}
		}
		if (!pieceLines.isEmpty()) {
			allPieces.add(new Piece(id, pieceLines));
		}
	}

	@Override
	protected void reset() {
	}

	@Override
	protected void puzzle1() {
		puzzle1Sol = null;
		visitedStates = new HashSet<>();
		remainingPieces = new HashSet<>(allPieces);

		Board board = createBoardWithFirstItemInCenter();
		findSolution(board);

		if (puzzle1Sol != null) {
			long sol = multiplyCornerIds(puzzle1Sol);
			System.out.println("puzzle 1 solution = " + sol);
		}
	}

	private Board createBoardWithFirstItemInCenter() {
		int pieceSize = allPieces.get(0).getN();
		int boardSize = (int) Math.sqrt(allPieces.size()) * 2;
		Board board = new Board(pieceSize, boardSize);
		board.place(boardSize / 2 - 1, boardSize / 2 - 1, allPieces.get(0).getInstances().get(0));
		remainingPieces.remove(allPieces.get(0));
		return board;
	}

	private void findSolution(Board board) {
		if (visitedStates.contains(board)) {
			return;
		}
		visitedStates.add(board);
		if (remainingPieces.isEmpty()) {
			if (board.isSquare()) {
				System.out.println("solution found");
				if (PRINT_SOLUTION_BOARD) {
					System.out.println(board);
				}
				puzzle1Sol = board;
			}
			return;
		}
		if (board.wontBeSquare()) {
			return;
		}
		Set<Coordinate> emptyPlaces = board.findEmptyPlacesWithMostNeighbours();
		for (Coordinate emptyPlace : emptyPlaces) {
			Set<PieceInstance> matches = board.getMatches(emptyPlace, remainingPieces);
			for (PieceInstance matchingPieceInstance : matches) {
				Board newBoard = board.copy();
				newBoard.place(emptyPlace.y, emptyPlace.x, matchingPieceInstance);
				remainingPieces.remove(matchingPieceInstance.getMaster());
				findSolution(newBoard);
				remainingPieces.add(matchingPieceInstance.getMaster());
			}
		}
	}

	private long multiplyCornerIds(Board board) {
		long sol = 1L;
		sol *= board.getId(true, true);
		sol *= board.getId(true, false);
		sol *= board.getId(false, true);
		sol *= board.getId(false, false);
		return sol;
	}

	@Override
	protected void puzzle2() {
		String puzzle1Solution = puzzle1Sol.print(false, false);
		puzzle1Solution = puzzle1Solution.trim().replaceAll(" ", "");
		List<String> puzzle1SolutionLines = Arrays.stream(puzzle1Solution.split("\n")).filter(line -> !"".equals(line)).collect(Collectors.toList());
		for (int rotation = 0; rotation < 4; rotation++) {
			for (int flipped = 0; flipped < 2; flipped++) {
				int monsterCount = 0;
				List<String> orientedBoard = orientBoard(puzzle1SolutionLines, rotation, flipped);

				for (int y = 0; y < orientedBoard.size() - MONSTER_HEIGHT + 1; y++) {
					for (int x = 0; x < orientedBoard.get(y).length() - MONSTER_WIDTH + 1; x++) {
						monsterCount += hasMonsters(orientedBoard, y, x) ? 1 : 0;
					}
				}
				if (monsterCount > 0) {
					if (PRINT_SOLUTION_BOARD) {
						System.out.println("rotation=" + rotation + " flipped=" + flipped + " monsters="+ monsterCount);
						System.out.println(String.join("\n", orientedBoard));
						System.out.println();
					}
					long hashtagCount = puzzle1Solution.chars().filter(c -> c == '#').count();
					long waterRoughness = hashtagCount - (long) monsterCount * MONSTER_MASS;
					System.out.println("puzzle 2 solution: water roughness = " + waterRoughness);
				}
			}
		}
	}

	private List<String> orientBoard(List<String> puzzle1SolutionLines, int rotation, int flipped) {
		Integer[][] arrayBoard = ArrayOperations.toArray(puzzle1SolutionLines);
		for (int rotationCount = 0; rotationCount < rotation; rotationCount++) {
			arrayBoard = ArrayOperations.rotateRight(arrayBoard);
		}
		if (flipped == 1) {
			arrayBoard = ArrayOperations.flip(arrayBoard);
		}
		return ArrayOperations.toStringList(arrayBoard);
	}

	private boolean hasMonsters(List<String> board, int y, int x) {
		String line1 = board.get(y);
		String line2 = board.get(y + 1);
		String line3 = board.get(y + 2);

		String subLine1 = line1.substring(x, x + MONSTER_WIDTH);
		String subLine2 = line2.substring(x, x + MONSTER_WIDTH);
		String subLine3 = line3.substring(x, x + MONSTER_WIDTH);
		Matcher matcher1 = monsterLine1.matcher(subLine1);
		Matcher matcher2 = monsterLine2.matcher(subLine2);
		Matcher matcher3 = monsterLine3.matcher(subLine3);
		return matcher1.find() && matcher2.find() && matcher3.find();
	}

	private void printAllPieces(boolean printInstances) {
		for (Piece piece : allPieces) {
			piece.getInstances();
			System.out.println(piece);
			if (printInstances) {
				for (PieceInstance instance : piece.getInstances()) {
					System.out.println(instance);
				}
			}
			System.out.println();
		}
	}
}
