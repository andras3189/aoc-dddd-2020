package aoc.common.instruction;

public class Instruction {

	public InstructionCommand command;
	public int n;
	public boolean executed;

	public Instruction(InstructionCommand command, int n) {
		this.command = command;
		this.n = n;
	}

	public Instruction copy() {
		return new Instruction(command, n);
	}

	public void flip() {
		if (command == InstructionCommand.JMP) {
			command = InstructionCommand.NOP;
		} else if (command == InstructionCommand.NOP) {
			command = InstructionCommand.JMP;
		}
	}

	public boolean flippable() {
		return command == InstructionCommand.JMP || command == InstructionCommand.NOP;
	}

	public void reset() {
		executed = false;
	}

	@Override
	public String toString() {
		return "Instruction{" + "command=" + command + ", n=" + n + ", executed=" + executed + '}';
	}
}
