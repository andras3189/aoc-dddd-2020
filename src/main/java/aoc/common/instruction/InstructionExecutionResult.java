package aoc.common.instruction;

public class InstructionExecutionResult {
	public boolean terminated;
	public long acc;

	public InstructionExecutionResult(boolean terminated, long acc) {
		this.terminated = terminated;
		this.acc = acc;
	}
}