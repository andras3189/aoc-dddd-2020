package aoc.day17;

import java.util.List;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day17 extends DayBase {

	public int[][][] matrix;
	public int[][][] newMatrix;

	public int N = 20;

	public static void main(String[] args) {
		Day17 day = new Day17();
//		day.setOnlyDummyInput(true);
		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void processInput() {
		matrix = new int[N][][];
		for (int i = 0; i < N; i++) {
			matrix[i] = new int[N][];
			for (int j = 0; j < N; j++) {
				matrix[i][j] = new int[N];
			}
		}

		initNewMatrix();

		int middle = N / 2;
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			List<Integer> lineCHars = line.chars().boxed().collect(Collectors.toList());
			for (int x = 0; x < lineCHars.size(); x++) {
				matrix[middle + x][middle + y][middle] = lineCHars.get(x);
			}
		}

	}

	private void initNewMatrix() {
		newMatrix = new int[N][][];
		for (int i = 0; i < N; i++) {
			newMatrix[i] = new int[N][];
			for (int j = 0; j < N; j++) {
				newMatrix[i][j] = new int[N];
			}
		}
	}

	@Override
	protected void reset() {

	}

	@Override
	protected void puzzle2() {

	}

	@Override
	protected void puzzle1() {
//		printMatrix();
		for (int r = 1; r <= 6; r++) {
			iterateMatrix();
			if (r == 1) {
//				printMatrix();
			}
		}
//		printMatrix();

		int aliveCount = 0;
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				for (int z = 0; z < N; z++) {
					int current = this.newMatrix[x][y][z];
					if (current == '#') aliveCount++;
				}
			}
		}
		System.out.println(aliveCount);

	}

	private void iterateMatrix() {
		initNewMatrix();

		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				for (int z = 0; z < N; z++) {
					int current = this.matrix[x][y][z];
					int nCount = getNCount(x, y, z);
					if (current == '#') {
						if (nCount != 2 && nCount != 3) {
							newMatrix[x][y][z] = '.';
						} else {
							newMatrix[x][y][z] = '#';
						}
					} else {
						if (nCount == 3) {
							newMatrix[x][y][z] = '#';
						} else {
							newMatrix[x][y][z] = '.';
						}

					}
				}
			}
		}
		matrix = newMatrix;
	}

	private int getNCount(int x, int y, int z) {
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
					if (cX == x && cY == y && cZ == z) {
						continue;
					}

					if (matrix[cX][cY][cZ] == '#') {
						nCount++;
					}
				}
			}
		}
		return nCount;
	}

	public void printMatrix() {
		for (int z=0; z < N; z++) {
			System.out.println("z = " + z);
			System.out.println("--:\t01234567890123456789");
			for (int y = 0; y < N; y++) {
				System.out.print(y + ":\t");
				for (int x = 0; x < N; x++) {
					char symbol = matrix[x][y][z] == '#' ? '#' : '.';
					System.out.print(symbol);
				}
				System.out.println("");
			}
		}
	}
}
