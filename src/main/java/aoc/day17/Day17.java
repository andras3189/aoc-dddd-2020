package aoc.day17;

import java.util.List;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day17 extends DayBase {

	public int[][][][] matrix;
	public int[][][][] newMatrix;

	private static final int steps = 6;
	public int N = 27;

	public static void main(String[] args) {
		Day17 day = new Day17();
		// day.setOnlyDummyInput(true);
		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void processInput() {
		matrix = new int[N][][][];
		for (int i = 0; i < N; i++) {
			matrix[i] = new int[N][][];
			for (int j = 0; j < N; j++) {
				matrix[i][j] = new int[N][];
				for (int k = 0; k < N; k++) {
					matrix[i][j][k] = new int[N];
				}
			}
		}

		// we need at most steps cells in each direction from the grid
		N = input.get(1).length() + 2*steps;
		
		initNewMatrix();

		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);

			// actually center the input in the grid
			int middle = N/2 - line.length()/2;

			List<Integer> lineChars = line.chars().boxed().collect(Collectors.toList());
			for (int x = 0; x < lineChars.size(); x++) {
				matrix[middle + x][middle + y][middle][middle] = lineChars.get(x);
			}
		}

	}

	private void initNewMatrix() {
		newMatrix = new int[N][][][];
		for (int i = 0; i < N; i++) {
			newMatrix[i] = new int[N][][];
			for (int j = 0; j < N; j++) {
				newMatrix[i][j] = new int[N][];
				for (int k = 0; k < N; k++) {
					newMatrix[i][j][k] = new int[N];
				}
			}
		}
	}

	@Override
	protected void reset() {

	}

	@Override
	protected void puzzle2() {
		for (int r = 1; r <= 6; r++) {
			iterateMatrix();
			// if (r==3) printMatrix(r);
		}

		int aliveCount = 0;
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				for (int z = 0; z < N; z++) {
					for (int w = 0; w < N; w++) {
						int current = this.matrix[x][y][z][w];
						if (current == '#')
							aliveCount++;
					}
				}
			}
		}
		System.out.println(aliveCount);
	}

	@Override
	protected void puzzle1() {

	}

	private void iterateMatrix() {
		initNewMatrix();

		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				for (int z = 0; z < N; z++) {
					for (int w = 0; w < N; w++) {
						int current = this.matrix[x][y][z][w];
						int nCount = getNCount(x, y, z, w);
						if (current == '#') {
							if (nCount != 2 && nCount != 3) {
								newMatrix[x][y][z][w] = '.';
							} else {
								newMatrix[x][y][z][w] = '#';
							}
						} else {
							if (nCount == 3) {
								newMatrix[x][y][z][w] = '#';
							} else {
								newMatrix[x][y][z][w] = '.';
							}

						}
					}
				}
			}
		}
		matrix = newMatrix;
	}

	private int getNCount(int x, int y, int z, int w) {
		int nCount = 0;

		for (int cX = x - 1; cX <= x + 1; cX++) {
			if (cX < 0 || cX >= N) {
				continue;
			}
			for (int cY = y - 1; cY <= y + 1; cY++) {
				if (cY < 0 || cY >= N) {
					continue;
				}
				for (int cZ = z - 1; cZ <= z + 1; cZ++) {
					if (cZ < 0 || cZ >= N) {
						continue;
					}
					for (int cW = w - 1; cW <= w + 1; cW++) {
						if (cW < 0 || cW >= N) continue;
						if (cX == x && cY == y && cZ == z && cW == w) {
							continue;
						}

						if (matrix[cX][cY][cZ][cW] == '#') {
							nCount++;
						}
					}
				}
			}
		}
		return nCount;
	}

	// public void printMatrix(int range) {
	// 	for (int z = 0; z < N; z++) {
	// 		if (z >= N / 2 - range && z <= N / 2 + range) {

	// 			System.out.println("z = " + z);
	// 			System.out.println("--:\t01234567890123456789");
	// 			for (int y = 0; y < N; y++) {
	// 				System.out.print(y + ":\t");
	// 				for (int x = 0; x < N; x++) {
	// 					char symbol = matrix[x][y][z] == '#' ? '#' : '.';
	// 					System.out.print(symbol);
	// 				}
	// 				System.out.println("");
	// 			}

	// 		}
	// 	}
	// }
}
