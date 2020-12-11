package aoc.day4;
// Pretty cool solution

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.DayBase;

public class Day4 extends DayBase {

	public static void main(String[] args) {
		new Day4().run();
	}

	@Override
	protected void processInput() {

	}

	@Override
	protected boolean isMultiLineInput() {
		return true;
	}

	@Override
	protected void puzzle1() {
		List<Map<String, String>> passports = collectPassports(inputGrouped);
		long validPassports = passports.stream()
				.filter(passport -> passport.keySet().size() == 8 || passport.keySet().size() == 7 && !passport.containsKey("cid")).count();
		System.out.println(validPassports); // 247
	}

	@Override
	protected void puzzle2() {
		List<Map<String, String>> passports = collectPassports(inputGrouped);
		long validPassports = passports.stream()
				.filter(passport -> passport.keySet().size() == 8 || passport.keySet().size() == 7 && !passport.containsKey("cid")) //
				.filter(passport -> isInt(passport.get("byr"))) //
				.filter(passport -> isInt(passport.get("iyr"))) //
				.filter(passport -> isInt(passport.get("eyr"))) //
				.filter(passport -> between(passport, "byr", 1920, 2002)) //
				.filter(passport -> between(passport, "iyr", 2010, 2020)) //
				.filter(passport -> between(passport, "eyr", 2020, 2030)) //
				.filter(this::validHeight) //
				.filter(this::validHair) //
				.filter(this::validEye) //
				.filter(this::validID).count();//
		System.out.println(validPassports); // 145
	}

	private boolean validID(Map<String, String> passport) {
		String pid = passport.get("pid");
		boolean valid = pid.length() == 9 && isLong(pid);
		if (!valid) {
			System.out.println("invalid id: " + pid);
		}
		return valid;
	}

	private boolean validEye(Map<String, String> passport) {
		String regex = "(amb|blu|brn|gry|grn|hzl|oth)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(passport.get("ecl"));
		boolean valid = matcher.find();
		if (!valid) {
			System.out.println("invalid eye: " + passport.get("ecl"));
		}
		return valid;
	}

	private boolean validHair(Map<String, String> passport) {
		String regex = "#([0-9a-f]{6})";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(passport.get("hcl"));
		boolean valid = matcher.find();
		if (!valid) {
			System.out.println("invalid hair: " + passport.get("hcl"));
		}
		return valid;
	}

	private boolean validHeight(Map<String, String> passport) {
		String regex = "(\\d*)(cm|in)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(passport.get("hgt"));
		if (matcher.find()) {
			String valueStr = matcher.group(1);
			if (!isInt(valueStr)) {
				System.out.println("height not int");
				return false;
			}
			Integer value = parseInt(valueStr);
			if ("cm".equals(matcher.group(2))) {
				boolean valid = 150 <= value && value <= 193;
				if (!valid) {
					System.out.println("invalid height in cm: " + value);
				}
				return valid;
			} else if ("in".equals(matcher.group(2))) {
				boolean valid = 59 <= value && value <= 76;
				if (!valid) {
					System.out.println("invalid height in inch: " + value);
				}
				return valid;
			} else {
				System.out.println("invalid height unit");
				return false;
			}

		}
		System.out.println("did not found height");
		return false;
	}

	private boolean between(Map<String, String> passport, String key, int min, int max) {
		int value = parseInt(passport.get(key));
		return min <= value && value <= max;
	}

	private List<Map<String, String>> collectPassports(List<List<String>> input) {
		Pattern pattern = Pattern.compile("(\\w{3})(\\:)([\\w#]*)");
		List<Map<String, String>> passports = new ArrayList<>();
		for (List<String> linesOnePassport : input) {
			Map<String, String> passport = new HashMap<>();
			for (String line : linesOnePassport) {
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					String key = matcher.group(1);
					String value = matcher.group(3);
					passport.put(key, value);
				}
			}
			passports.add(passport);
		}
		return passports;
	}

}
