package aoc.common.instruction;

public enum InstructionCommand {
	ACC("acc"), JMP("jmp"), NOP("nop");

	private final String keyword;

	InstructionCommand(String keyword) {
		this.keyword = keyword;
	}

	public static InstructionCommand fromKeyword(String keyword) {
		for (InstructionCommand command : InstructionCommand.values()) {
			if (command.keyword.equals(keyword)) {
				return command;
			}
		}
		throw new IllegalArgumentException("Unknown instruction command: " + keyword);
	}
}
