package aoc.day3;

import static aoc.TheFileReader.readFile;

import java.util.List;

public class Day3 {

	public static int col;
	public static int row;
	public static int dCol;
	public static int dRow;
	public static int nCol;
	public static int nRow;
	public static int[][] matrix;


	public static void main(String[] args) {
		readInput();

		long sol = solve(1, 1);
		sol *= solve(3, 1);
		sol *= solve(5, 1);
		sol *= solve(7, 1);
		sol *= solve(1, 2);
		System.out.println(sol);
	}

	private static int solve(int dCol, int dRow) {
		Day3.dCol = dCol;
		Day3.dRow = dRow;
		col = 0;
		row = 0;
		int trees = 0;
		while (row < nRow) {
			trees += isTree() ? 1 : 0;
			step();
		}
		return trees;
	}

	private static void readInput() {
		List<String> input = readFile("day3/day3");
		assert input != null;
		nRow = input.size();
		nCol = input.get(0).length();
		matrix = new int[nRow][nCol];
		for (int i = 0; i < input.size(); i++) {
			matrix[i] = input.get(i).chars().toArray();
		}
	}

	private static boolean isTree() {
		return matrix[row][col] == '#';
	}

	public static void step() {
		col = (col + dCol) % nCol;
		row += dRow;
	}
}
