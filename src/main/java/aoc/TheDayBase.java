package aoc;

import java.util.List;

public abstract class TheDayBase {

	protected List<String> input;
	protected List<List<String>> groupedInput;

	protected TheDayBase(boolean multiLineInput) {
		if (multiLineInput) {
			groupedInput = readFileMultiLine(getInputFilename());
		} else {
			input = readFile(getInputFilename());
		}
	}

	protected abstract String getInputFilename();

	protected void processInput() {
	}

	protected abstract void puzzle2();

	protected abstract void puzzle1();

	protected List<String> readFile(String fileName) {
		return TheFileReader.readFile(fileName);
	}

	protected List<List<String>> readFileMultiLine(String fileName) {
		return TheFileReader.readFileMultiLineInput(fileName);
	}

	protected boolean isInt(String str) {
		try {
			parseInt(str);
			return true;

		} catch (NumberFormatException e) {
			return false;
		}
	}

	protected boolean isLong(String str) {
		try {
			parseLong(str);
			return true;

		} catch (NumberFormatException e) {
			return false;
		}
	}

	protected int parseInt(String str) {
		return Integer.parseInt(str);
	}

	protected long parseLong(String str) {
		return Long.parseLong(str);
	}
}
