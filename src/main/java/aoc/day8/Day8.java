package aoc.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.TheDayBase;

public class Day8 extends TheDayBase {

	public static String REGEX = "(acc|jmp|nop)\\s([+-])([\\d]*)";
	public List<Instruction> instructions = new ArrayList<>();


	public Day8(boolean multiLineInput) {
		super(multiLineInput);
		//input = readFile("day8/dummy");
	}

	@Override
	protected String getInputFilename() {
		return "day8/day8";
	}

	public static void main(String[] args) {
		Day8 day = new Day8(false);
		day.processInput();
		day.puzzle1();
		day.puzzle2();
	}

	private void processInput() {
		for (String line : input) {
			Pattern pattern = Pattern.compile(REGEX);
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String instructionString = matcher.group(1);
				Ins ins;
				if ("acc".equals(instructionString)) {
					ins = Ins.ACC;
				} else if ("jmp".equals(instructionString)) {
					ins = Ins.JMP;
				} else {
					ins = Ins.NOP;
				}

				boolean negative = matcher.group(2).equals("-");

				int amount = parseInt(matcher.group(3));

				Instruction instruction = new Instruction(ins, negative ? -amount : amount);
				instructions.add(instruction);
			}
		}
	}

	@Override
	protected void puzzle1() {
		System.out.println(execute(instructions).acc);
	}

	@Override
	protected void puzzle2() {
		for (int i = 0; i < instructions.size(); i++) {
			if (instructions.get(i).flippable()) {
				List<Instruction> currentList = createModifiedInstructionList(i);
				instructions.forEach(Instruction::reset);
				Result executeResult = execute(currentList);
				if (executeResult.terminated) {
					System.out.println(executeResult.acc);
				}
			}
		}
	}

	private List<Instruction> createModifiedInstructionList(int flipIndex) {
		Instruction copy = instructions.get(flipIndex).copy();
		copy.flip();
		List<Instruction> currentList = new ArrayList<>();
		for (int j = 0; j < flipIndex; j++) {
			currentList.add(instructions.get(j));
		}
		currentList.add(copy);
		for (int j = flipIndex+1; j < instructions.size(); j++) {
			currentList.add(instructions.get(j));
		}
		return currentList;
	}

	private Result execute(List<Instruction> instructions) {
		long acc = 0;
		int next = 0;
		while (true) {
			if (next >= instructions.size()) {
				return new Result(true, acc);
			}
			Instruction current = instructions.get(next);
			if (current.executed) {
				return new Result(false, acc);
			}
			current.executed = true;
			if (current.instruction == Ins.ACC) {
				acc += current.n;
				next += 1;
				continue;
			}
			if (current.instruction == Ins.NOP) {
				next += 1;
				continue;
			}
			if (current.instruction == Ins.JMP) {
				next += current.n;
				continue;
			}
		}
	}

	public enum Ins {
		ACC, JMP, NOP;
	}

	public static class Result {
		public boolean terminated;
		public long acc;

		public Result(boolean terminated, long acc) {
			this.terminated = terminated;
			this.acc = acc;
		}
	}

	public static class Instruction {
		public Ins instruction;
		public int n;
		public boolean executed;

		public Instruction(Ins instruction, int n) {
			this.instruction = instruction;
			this.n = n;
		}

		public Instruction copy() {
			return new Instruction(instruction, n);
		}

		public void flip() {
			if (instruction == Ins.JMP) {
				instruction = Ins.NOP;
			} else if (instruction == Ins.NOP) {
				instruction = Ins.JMP;
			}
		}

		public boolean flippable() {
			return instruction == Ins.JMP || instruction == Ins.NOP;
		}

		public void reset() {
			executed = false;
		}

		@Override
		public String toString() {
			return "Instruction{" + "instruction=" + instruction + ", n=" + n + ", executed=" + executed + '}';
		}
	}
}