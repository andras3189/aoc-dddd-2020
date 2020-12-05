package aoc;

import java.util.List;

public abstract class TheDayBase {

	protected List<String> input;

	protected TheDayBase() {
		this.input = readFile(getInputFilename());
	}

	protected abstract String getInputFilename();

	protected abstract void puzzle1();

	protected abstract void puzzle2();

	protected List<String> readFile(String fileName) {
		return TheFileReader.readFile(fileName);
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

	protected int binaryToDecimal(long n) {
		int dec_value = 0;

		int base = 1;

		long temp = n;
		while (temp > 0) {
			long last_digit = temp % 10;
			temp = temp / 10;

			dec_value += last_digit * base;

			base = base * 2;
		}

		return dec_value;
	}

	protected int parseInt(String str) {
		return Integer.parseInt(str);
	}

	protected long parseLong(String str) {
		return Long.parseLong(str);
	}
}
