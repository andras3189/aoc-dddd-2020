package aoc.common.converter;

public class BinaryConverter {

	public static int binaryToDecimal(long n) {
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
}
