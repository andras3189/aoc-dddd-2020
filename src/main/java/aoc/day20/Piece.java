package aoc.day20;

import java.util.ArrayList;
import java.util.List;

import aoc.common.converter.BinaryConverter;

public class Piece {
	private final int id;
	private final Integer[][] matrix;
	private final int N;
	private final List<String> lines;
	private List<PieceInstance> instances;

	private int topDecimal, bottomDecimal, leftDecimal, rightDecimal, topReversedDecimal, bottomReversedDecimal, leftReversedDecimal, rightReversedDecimal;

	public Piece(int id, List<String> lines) {
		this.id = id;
		this.lines = new ArrayList<>(lines);
		N = lines.size();
		matrix = new Integer[N][];
		for (int y = 0; y < N; y++) {
			String line = lines.get(y);
			matrix[y] = new Integer[N];
			for (int x = 0; x < N; x++) {
				matrix[y][x] = (int) line.charAt(x);
			}
		}
	}

	public List<PieceInstance> getInstances() {
		if (instances != null) {
			return instances;
		}
		instances = new ArrayList<>();
		String top = "";
		for (int x = 0; x < N; x++) {
			top += Character.toString(matrix[0][x]);
		}
		String bottom = "";
		for (int x = 0; x < N; x++) {
			bottom += Character.toString(matrix[N - 1][x]);
		}
		String left = "";
		for (int y = 0; y < N; y++) {
			left += Character.toString(matrix[y][0]);
		}
		String right = "";
		for (int y = 0; y < N; y++) {
			right += Character.toString(matrix[y][N - 1]);
		}

		String topReversed = new StringBuilder(top).reverse().toString();
		String bottomReversed = new StringBuilder(bottom).reverse().toString();
		String leftReversed = new StringBuilder(left).reverse().toString();
		String rightReversed = new StringBuilder(right).reverse().toString();

		topDecimal = toDecimal(top);
		bottomDecimal = toDecimal(bottom);
		leftDecimal = toDecimal(left);
		rightDecimal = toDecimal(right);

		topReversedDecimal = toDecimal(topReversed);
		bottomReversedDecimal = toDecimal(bottomReversed);
		leftReversedDecimal = toDecimal(leftReversed);
		rightReversedDecimal = toDecimal(rightReversed);

		PieceInstance instanceTemplate = new PieceInstance(N, topDecimal, bottomDecimal, leftDecimal, rightDecimal, topReversedDecimal,
				bottomReversedDecimal, leftReversedDecimal, rightReversedDecimal, this);
		instances.add(instanceTemplate.copy().rotate(0));
		instances.add(instanceTemplate.copy().rotate(1));
		instances.add(instanceTemplate.copy().rotate(2));
		instances.add(instanceTemplate.copy().rotate(3));

		instances.add(instanceTemplate.copy().rotate(0).flipHorizontal());
		instances.add(instanceTemplate.copy().rotate(1).flipHorizontal());
		instances.add(instanceTemplate.copy().rotate(2).flipHorizontal());
		instances.add(instanceTemplate.copy().rotate(3).flipHorizontal());

		return instances;

	}

	private int toDecimal(String str) {
		return BinaryConverter.binaryToDecimal(Long.parseLong(str.replaceAll("\\.", "0").replaceAll("#", "1")));
	}

	public int getN() {
		return N;
	}

	public int getId() {
		return id;
	}

	public List<String> getLines() {
		return lines;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("id=").append(id).append("\n");
		builder.append("top=").append(topDecimal);
		builder.append(" right=").append(rightDecimal);
		builder.append(" bottom=").append(bottomDecimal);
		builder.append(" left=").append(leftDecimal);
		builder.append("\n");
		builder.append("topRev=").append(topReversedDecimal);
		builder.append(" rightRev=").append(rightReversedDecimal);
		builder.append(" bottomRev=").append(bottomReversedDecimal);
		builder.append(" leftRev=").append(leftReversedDecimal);
		builder.append("\n");
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				builder.append(Character.toString(matrix[y][x]));
			}
			if (y < N - 1) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
}
