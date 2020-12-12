package aoc.common.navigation;

import static aoc.common.navigation.NavigationUtil.normalizeRotation;

public class Coordinate {
	public int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate add(Coordinate difference) {
		return new Coordinate(x + difference.x, y + difference.y);
	}

	public Coordinate multiply(int amount) {
		return new Coordinate(x * amount, y * amount);
	}

	public Coordinate rotateAroundOrigo(int rotation) {
		rotation = normalizeRotation(rotation);

		switch (rotation) {
		case 90:
			return new Coordinate(y, -x);
		case 180:
			return new Coordinate(-x, -y);
		case 270:
			return new Coordinate(-y, x);
		default:
			System.out.println("wrong rotation");
			return new Coordinate(x, y);
		}
	}

	public static Coordinate from(int rotation, int amount) {
		switch (rotation) {
		case 0:
			return new Coordinate(0, amount);
		case 90:
			return new Coordinate(amount, 0);
		case 180:
			return new Coordinate(0, -amount);
		case 270:
			return new Coordinate(-amount, 0);
		default:
			System.out.println("wrong rotation");
			return new Coordinate(0, 0);
		}
	}

	public int distanceFromOrigo() {
		return Math.abs(x) + Math.abs(y);
	}

	@Override
	public String toString() {
		return "{" + x + "," + y + "}";
	}
}
