package aoc.day11;

import java.util.ArrayList;
import java.util.List;

import aoc.TheDayBase;

public class Day11 extends TheDayBase {

	private List<Long> longInput = new ArrayList<>();

	public Day11(boolean multiLineInput) {
		super(multiLineInput);
		//input = readFile("day11/david");
		//input = readFile("day11/dummy");
		//input = readFile("day11/dummy2");
	}

	@Override
	protected String getInputFilename() {
		return "day11/day11";
	}

	public static void main(String[] args) {
		Day11 day = new Day11(false);
		day.processInput();
		day.puzzle1();
		day.puzzle2();
	}

	@Override
	protected void processInput() {
//		longInput = input.stream().map(this::parseLong).collect(Collectors.toList());
	}

	@Override
	protected void puzzle1() {

	}

	@Override
	protected void puzzle2() {

	}
}