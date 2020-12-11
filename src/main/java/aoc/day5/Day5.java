package aoc.day5;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import aoc.DayBase;
import aoc.common.converter.BinaryConverter;

public class Day5 extends DayBase {

	public static void main(String[] args) {
		new Day5().run();
	}

	@Override
	protected void processInput() {

	}

	@Override
	protected void puzzle1() {
		Integer max = input.stream().map(this::toSeat).max(Comparator.comparing(Integer::valueOf)).get();
		System.out.println(max);
	}

	@Override
	protected void puzzle2() {
		List<Integer> takenSeats = input.stream().map(this::toSeat).collect(Collectors.toList());
		List<Integer> freeSeats = IntStream.range(0, 1024).filter(seat -> mySeat(takenSeats, seat)).boxed().collect(Collectors.toList());
		System.out.println(freeSeats);
	}

	private boolean mySeat(List<Integer> takenSeats, Integer seat) {
		return takenSeats.stream().noneMatch(seat::equals) && //
				takenSeats.stream().anyMatch(s -> seat + 1 == s) && //
				takenSeats.stream().anyMatch(s -> seat - 1 == s);
	}

	private int toSeat(String in) {
		String binary = in.replaceAll("B", "1").replaceAll("F", "0").replaceAll("R", "1").replaceAll("L", "0");
		return BinaryConverter.binaryToDecimal(parseLong(binary));
	}

}
