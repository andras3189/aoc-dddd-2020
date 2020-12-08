package aoc.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.TheDayBase;
import aoc.common.instruction.Instruction;
import aoc.common.instruction.InstructionCommand;
import aoc.common.instruction.InstructionExecutionResult;
import aoc.common.instruction.InstructionExecutor;

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

	@Override
	protected void processInput() {
		for (String line : input) {
			Pattern pattern = Pattern.compile(REGEX);
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				InstructionCommand command = InstructionCommand.fromKeyword(matcher.group(1));
				boolean negative = matcher.group(2).equals("-");
				int amount = parseInt(matcher.group(3));
				Instruction instruction = new Instruction(command, negative ? -amount : amount);
				instructions.add(instruction);
			}
		}
	}

	@Override
	protected void puzzle1() {
		System.out.println(InstructionExecutor.execute(instructions).acc);
	}

	@Override
	protected void puzzle2() {
		for (int i = 0; i < instructions.size(); i++) {
			if (instructions.get(i).flippable()) {
				List<Instruction> currentList = createModifiedInstructionList(i);
				instructions.forEach(Instruction::reset);
				InstructionExecutionResult executeResult = InstructionExecutor.execute(currentList);
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
		for (int j = flipIndex + 1; j < instructions.size(); j++) {
			currentList.add(instructions.get(j));
		}
		return currentList;
	}

}