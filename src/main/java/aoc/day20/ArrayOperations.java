package aoc.day20;

import java.util.ArrayList;
import java.util.List;

public class ArrayOperations {

	public static Integer[][] flip(Integer[][] array) {
		Integer[][] newArray = createArray(array.length);
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array[y].length; x++) {
				newArray[y][x] = array[y][array.length - 1 - x];
			}
		}
		return newArray;
	}

	public static Integer[][] rotateRight(Integer[][] array) {
		// y = x
		// x = N - y
		Integer[][] newArray = createArray(array.length);
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array[y].length; x++) {
				newArray[x][array.length - 1 - y] = array[y][x];
			}
		}
		return newArray;
	}

	public static Integer[][] createArray(int size) {
		Integer[][] array = new Integer[size][];
		for (int y = 0; y < size; y++) {
			array[y] = new Integer[size];
		}
		return array;
	}

	public static Integer[][] getWithoutBorder(Integer[][] array) {
		Integer[][] inside = ArrayOperations.createArray(array.length - 2);
		for (int y = 1; y < array.length - 1; y++) {
			for (int x = 1; x < array.length - 1; x++) {
				inside[y - 1][x - 1] = array[y][x];
			}
		}
		return inside;
	}

	public static Integer[][] toArray(List<String> lines) {
		Integer[][] inside = ArrayOperations.createArray(lines.size());
		for (int y = 0; y < lines.size(); y++) {
			for (int x = 0; x < lines.size(); x++) {
				inside[y][x] = (int) lines.get(y).charAt(x);
			}
		}
		return inside;
	}

	public static List<String> toStringList(Integer[][] array) {
		List<String> list = new ArrayList<>();
		for (Integer[] rows : array) {
			StringBuilder line = new StringBuilder();
			for (Integer cell : rows) {
				line.append(Character.toString(cell));
			}
			list.add(line.toString());
		}
		return list;
	}
}
