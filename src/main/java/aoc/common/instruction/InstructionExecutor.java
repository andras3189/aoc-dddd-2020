package aoc.common.instruction;

import java.util.List;

public class InstructionExecutor {

	public static InstructionExecutionResult execute(List<Instruction> instructions) {
		long acc = 0;
		int next = 0;
		while (true) {
			if (next >= instructions.size()) {
				return new InstructionExecutionResult(true, acc);
			}
			Instruction current = instructions.get(next);
			if (current.executed) {
				return new InstructionExecutionResult(false, acc);
			}
			current.executed = true;
			if (current.command == InstructionCommand.ACC) {
				acc += current.n;
				next += 1;
			} else if (current.command == InstructionCommand.NOP) {
				next += 1;
			} else if (current.command == InstructionCommand.JMP) {
				next += current.n;
			}
		}
	}
}
