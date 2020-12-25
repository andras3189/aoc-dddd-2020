package aoc.day25;

import aoc.DayBase;

public class Day25 extends DayBase {

	public static void main(String[] args) {
		Day25 day = new Day25();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void processInput() {

	}

	@Override
	protected void reset() {

	}

	@Override
	protected void puzzle1() {
		long publicKey1 = Long.parseLong(input.get(0));
		long publicKey2 = Long.parseLong(input.get(1));

		int loopSize1 = getLoopSize(publicKey1);
		long encryptionKey1 = getEncryptionKey(publicKey2, loopSize1);

		int loopSize2 = getLoopSize(publicKey2);
		long encryptionKey2 = getEncryptionKey(publicKey1, loopSize2);

		System.out.println(encryptionKey1);
		System.out.println(encryptionKey2);
	}

	private long getEncryptionKey(long subjectNumber, int loopSize) {
		long value = 1;
		for (int i = 0; i < loopSize; i++) {
			value = value * subjectNumber;
			value = value % 20201227;
		}
		return value;
	}

	private int getLoopSize(long valueToFind) {
		long subjectNumber = 7;
		long value = 1;
		int loopSize = 0;
		while (true) {
			value = value * subjectNumber;
			value = value % 20201227;
			loopSize++;
			if (value == valueToFind) {
				break;
			}
		}
		return loopSize;
	}

	@Override
	protected void puzzle2() {

	}
}
