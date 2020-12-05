package aoc.day5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import aoc.TheDayBase;

public class Day5 extends TheDayBase {

	public static void main(String[] args) {
		Day5 day5 = new Day5();
		day5.puzzle1();
		day5.puzzle2();
	}

	@Override
	protected void puzzle1() {
		Integer max = input.stream().map(this::toSeat).map(seat -> seat.id).max(Comparator.comparing(Integer::valueOf)).get();
		System.out.println(max);
	}

	@Override
	protected void puzzle2() {
		List<Seat> allSeats = getAllPossibleSeats();
		List<Seat> takenSeats = input.stream().map(this::toSeat).collect(Collectors.toList());
		List<Seat> freeSeats = allSeats.stream().filter(seat -> mySeat(takenSeats, seat)).collect(Collectors.toList());
		System.out.println(freeSeats);
	}

	private List<Seat> getAllPossibleSeats() {
		List<Seat> allSeats = new ArrayList<>();
		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < 8; j++) {
				allSeats.add(new Seat(i, j));
			}
		}
		return allSeats;
	}

	private boolean mySeat(List<Seat> takenSeats, Seat seat) {
		return takenSeats.stream().noneMatch(s -> seat.id == s.id) && //
				takenSeats.stream().anyMatch(s -> seat.id + 1 == s.id) && //
				takenSeats.stream().anyMatch(s -> seat.id - 1 == s.id);
	}

	private Seat toSeat(String in) {
		String binary = in.replaceAll("B", "1").replaceAll("F", "0").replaceAll("R", "1").replaceAll("L", "0");
		String row = binary.substring(0, 7);
		String column = binary.substring(7, 10);

		int rowInt = binaryToDecimal(parseLong(row));
		int columnInt = binaryToDecimal(parseLong(column));

		return new Seat(rowInt, columnInt);
	}

	@Override
	protected String getInputFilename() {
		return "day5/day5";
	}

	public static class Seat {
		public int row;
		public int column;
		public int id;

		public Seat(int row, int column) {
			this.row = row;
			this.column = column;
			this.id = row * 8 + column;
		}

		@Override
		public String toString() {
			return "Seat{" + "row=" + row + ", column=" + column + ", id=" + id + '}' + "\n";
		}
	}
}
