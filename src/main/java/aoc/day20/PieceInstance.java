package aoc.day20;

import static aoc.day20.ArrayOperations.flip;
import static aoc.day20.ArrayOperations.getWithoutBorder;
import static aoc.day20.ArrayOperations.rotateRight;
import static aoc.day20.ArrayOperations.toArray;
import static aoc.day20.ArrayOperations.toStringList;

import java.util.List;
import java.util.Objects;

public class PieceInstance {
	private final int N;
	private int top;
	private int bottom;
	private int left;
	private int right;
	private int topRev;
	private int bottomRev;
	private int leftRev;
	private int rightRev;
	private final Piece master;
	private int rotation;
	private boolean flipped;

	public PieceInstance(int N, int top, int bottom, int left, int right, int topRev, int bottomRev, int leftRev, int rightRev, Piece master) {
		this.N = N;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.topRev = topRev;
		this.bottomRev = bottomRev;
		this.leftRev = leftRev;
		this.rightRev = rightRev;
		this.master = master;
	}

	public PieceInstance copy() {
		return new PieceInstance(N, top, bottom, left, right, topRev, bottomRev, leftRev, rightRev, master);
	}

	public PieceInstance rotate(int turns) {
		rotation += turns;
		for (int i = 0; i < turns; i++) {
			int oldTop = top;
			int oldLeft = left;
			int oldRight = right;
			int oldTopRev = topRev;
			top = leftRev;
			left = bottom;
			bottom = rightRev;
			right = oldTop;
			topRev = oldLeft;
			leftRev = bottomRev;
			bottomRev = oldRight;
			rightRev = oldTopRev;
		}
		return this;
	}

	public PieceInstance flipHorizontal() {
		flipped = !flipped;
		int oldTop = top;
		top = topRev;
		topRev = oldTop;
		int oldBottom = bottom;
		bottom = bottomRev;
		bottomRev = oldBottom;
		int oldLeft = left;
		left = right;
		right = oldLeft;
		int oldLeftRev = leftRev;
		leftRev = rightRev;
		rightRev = oldLeftRev;
		return this;
	}

	public boolean fulfills(int topNeed, int bottomNeed, int leftNeed, int rightNeed) {
		if (top != topNeed && topNeed != -1) {
			return false;
		}
		if (bottom != bottomNeed && bottomNeed != -1) {
			return false;
		}
		if (left != leftNeed && leftNeed != -1) {
			return false;
		}
		if (right != rightNeed && rightNeed != -1) {
			return false;
		}
		return true;
	}

	public String print(int lineNumber) {
		return master.getLines().get(lineNumber);
	}

	public String printWithoutBorder(int lineNumber) {
		StringBuilder builder = new StringBuilder();
		List<String> inside = getInside();
		if (lineNumber == 0) {
			builder.append(" ".repeat(N));
		} else if (lineNumber == N - 1) {
			builder.append(" ".repeat(N));
		} else {
			builder.append(" ");
			builder.append(inside.get(lineNumber - 1));
			builder.append(" ");
		}
		return builder.toString();
	}

	private List<String> getInside() {
		Integer[][] inside = toArray(master.getLines());
		inside = getWithoutBorder(inside);
		for (int i = 0; i < rotation; i++) {
			inside = rotateRight(inside);
		}
		if (flipped) {
			inside = flip(inside);
		}
		return toStringList(inside);
	}

	public String printHumanFriendly(int line) {
		StringBuilder builder = new StringBuilder();
		if (line == 0) {
			builder.append("*");
			builder.append("-".repeat(N - 2));
			builder.append("*");
		} else if (line == 1) {
			builder.append("|");
			builder.append(" ".repeat((int) (Math.floor((N - 2 - Integer.toString(top).length()) / 2.0))));
			builder.append(top);
			builder.append(" ".repeat((int) (Math.ceil((N - 2 - Integer.toString(top).length()) / 2.0))));
			builder.append("|");
		} else if (line == N / 2 - 2) {
			builder.append("|");
			builder.append(" ");
			builder.append("#");
			builder.append(master.getId());
			builder.append("#");
			builder.append(" ");
			builder.append("|");
		} else if (line == N / 2 - 1) {
			builder.append("|");
			builder.append(left);
			builder.append(" ".repeat(N - 2 - Integer.toString(left).length()));
			builder.append("|");
		} else if (line == N / 2) {
			builder.append("|");
			builder.append(" ".repeat(N - 2 - Integer.toString(right).length()));
			builder.append(right);
			builder.append("|");
		} else if (line == N - 1) {
			builder.append("*");
			builder.append("-".repeat(N - 2));
			builder.append("*");
		} else if (line == N - 2) {
			builder.append("|");
			builder.append(" ".repeat((int) (Math.floor((N - 2 - Integer.toString(bottom).length()) / 2.0))));
			builder.append(bottom);
			builder.append(" ".repeat((int) (Math.ceil((N - 2 - Integer.toString(bottom).length()) / 2.0))));
			builder.append("|");
		} else {
			builder.append("|");
			builder.append(" ".repeat(N - 2));
			builder.append("|");
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("top=").append(top);
		builder.append(" right=").append(right);
		builder.append(" bottom=").append(bottom);
		builder.append(" left=").append(left);
		builder.append("\n");
		builder.append("topRev=").append(topRev);
		builder.append(" rightRev=").append(rightRev);
		builder.append(" bottomRev=").append(bottomRev);
		builder.append(" leftRev=").append(leftRev);
		return builder.toString();
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public Piece getMaster() {
		return master;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PieceInstance that = (PieceInstance) o;
		return top == that.top && bottom == that.bottom && left == that.left && right == that.right && master.getId() == that.master.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(top, bottom, left, right, master.getId());
	}
}
