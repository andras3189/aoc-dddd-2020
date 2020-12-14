package aoc.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day14 extends DayBase {

	public static void main(String[] args) {
		Day14 day = new Day14();
//		day.setOnlyDummyInput(true);
		day.setOnlyRealInput(true);
		day.run();
	}

	private final Map<Long, Long> memory = new HashMap<>();
	private final Map<Integer, List<String>> maskMemory = new HashMap<>();
	public String mask = "";

	@Override
	protected void processInput() {

	}

	@Override
	protected void puzzle1() {
		solvePuzzle(false);
	}

	@Override
	protected void puzzle2() {
		solvePuzzle(true);
	}

	private void solvePuzzle(boolean floatingMask) {
		for (String line : input) {
			setMask(line);
			writeMem(line, floatingMask);
		}
		long sum = memory.keySet().stream().map(memory::get).mapToLong(Long::longValue).sum();
		System.out.println(sum);
	}

	private void writeMem(String line, boolean floating) {
		Pattern pattern = Pattern.compile("mem\\[(\\d*)\\] = (\\d*)");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			long index = parseLong(matcher.group(1));
			long value = parseLong(matcher.group(2));

			if (floating) {
				for (Long i : applyFloatingMask(index)) {
					memory.put(i, value);
				}
			} else {
				value = applyMask(value, mask);
				memory.put(index, value);
			}
		}
	}

	private List<Long> applyFloatingMask(long value) {
		List<Long> result = new ArrayList<>();
		maskMemory.clear();
		for (String mask : generateMasks(0)) {
			result.add(applyMask(value, mask));
		}
		return result;
	}

	private long applyMask(long value, String mask) {
		List<Integer> maskCharacters = mask.chars().boxed().collect(Collectors.toList());
		for (int i = maskCharacters.size() - 1; i >= 0; i--) {
			Integer c = maskCharacters.get(maskCharacters.size() - i - 1);
			if (c == 'X') {
				continue;
			}
			if (c == '1') {
				long binary = 1L << i;
				value |= binary;
			}
			if (c == '0') {
				long binary = 1L << i;
				binary = ~binary & ~(1L << 63);
				value &= binary;
			}
		}
		return value;
	}

	private List<String> generateMasks(int start) {
		List<String> result = new ArrayList<>();
		List<Integer> maskCharacters = mask.chars().boxed().collect(Collectors.toList());
		Integer c = maskCharacters.get(start);

		if (maskMemory.containsKey(start)) {
			return maskMemory.get(start);
		}

		if (start == mask.length() - 1) {
			if (c == '0') {
				result.add("X");
			}
			if (c == '1') {
				result.add("1");
			}
			if (c == 'X') {
				result.add("0");
				result.add("1");
			}
		} else if (c == '0') {
			List<String> generated = generateMasks(start + 1);
			generated.forEach(g -> result.add("X" + g));
		} else if (c == '1') {
			List<String> generated = generateMasks(start + 1);
			generated.forEach(g -> result.add("1" + g));
		} else if (c == 'X') {
			List<String> generated = generateMasks(start + 1);
			generated.forEach(g -> result.add("0" + g));
			generated.forEach(g -> result.add("1" + g));
		}

		maskMemory.put(start, result);
		return result;
	}

	private void setMask(String line) {
		Pattern pattern = Pattern.compile("mask = ([X01]*)");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			mask = matcher.group(1);
		}
	}

	@Override
	protected void reset() {
		memory.clear();
	}
}
