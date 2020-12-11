package aoc;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class DayBase {

	protected List<String> input;
	protected List<List<String>> inputGrouped;
	protected List<Long> inputLong;

	protected abstract void processInput();

	protected abstract void puzzle2();

	protected abstract void puzzle1();

	protected boolean isMultiLineInput() {
		return false;
	}

	protected final void run() {
		for (String inputFilename : getInputFilenames()) {
			System.out.println(format("### %s ###", inputFilename));
			boolean notEmptyInput = readInput(getFolderName() + "/" + inputFilename);
			if (notEmptyInput) {
				processInput();
				puzzle1();
				puzzle2();
			} else {
				System.out.println("Input is empty");
			}
			System.out.println(IntStream.range(0, inputFilename.length() + 8).boxed().map(i -> "#").collect(Collectors.joining("")));
			System.out.println();
		}
	}

	private boolean readInput(String fileName) {
		if (isMultiLineInput()) {
			inputGrouped = readFileMultiLine(fileName);
		} else {
			input = readFile(fileName);
			if (input != null && input.stream().allMatch(this::isLong)) {
				inputLong = input.stream().map(this::parseLong).collect(Collectors.toList());
			}
		}
		return inputGrouped != null && inputGrouped.size() > 0 || input != null && input.size() > 0;
	}

	protected List<String> readFile(String fileName) {
		return TheFileReader.readFile(fileName);
	}

	protected List<List<String>> readFileMultiLine(String fileName) {
		return TheFileReader.readFileMultiLineInput(fileName);
	}

	private String getFolderName() {
		String[] packages = this.getClass().getPackageName().split("\\.");
		return packages[packages.length - 1];
	}

	private List<String> getInputFilenames() {
		List<String> inputFiles = new ArrayList(Arrays.asList(getFolderName() + "-david", getFolderName(), "dummy"));
		inputFiles.addAll(getAdditionalInputFileNames());
		return inputFiles;
	}

	protected List<String> getAdditionalInputFileNames() {
		return Collections.emptyList();
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
