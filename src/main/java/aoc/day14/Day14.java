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
		new Day14().run();
	}

	public Map<Long, Long> memory = new HashMap<>();

	public String mask = "";

	@Override
	protected List<String> getInputFilenames() {
//		return super.getInputFilenames();
		return Arrays.asList("day14");
	}

	@Override
	protected void processInput() {

		memory.clear();
		for (String line : input) {
			if (line.startsWith("mask")) {
				setMask(line);
			} else {
				writeMem(line);
			}
		}
	}

	private void writeMem(String line) {
		Pattern pattern = Pattern.compile("mem\\[(\\d*)\\] = (\\d*)");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			long index = parseLong(matcher.group(1));
			long value = parseLong(matcher.group(2));
			value = applyMask(value,mask);
			memory.put(index, value);
		}
	}

	@Override
	protected void puzzle2() {

		memory.clear();
		for (String line : input) {
			if (line.startsWith("mask")) {
				setMask(line);
			} else {
				writeMem2(line);
			}
		}

		long sum = memory.keySet().stream().map(key -> memory.get(key)).mapToLong(Long::longValue).sum();
		System.out.println(sum);

	}

	private void writeMem2(String line) {
		Pattern pattern = Pattern.compile("mem\\[(\\d*)\\] = (\\d*)");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			long index = parseLong(matcher.group(1));
			long value = parseLong(matcher.group(2));

			for (Long i : applyMask2(index)) {
				memory.put(i, value);
			}
		}
	}

	private List<Long> applyMask2(long value) {
		List<Long> result = new ArrayList<>();
		long value2 = applyMask(value, mask);
		List<String> masks = generateMasks(0);

		for (String lMask : masks) {
			result.add(applyMask(value2, lMask));
		}

		return result;
	}

	private Map<Integer, List<String>> memoryMasks = new HashMap<>();

	private List<String> generateMasks(int index) {
		List<Integer> collect = mask.chars().boxed().collect(Collectors.toList());
		List<String> result = new ArrayList<>();
		Integer c = collect.get(index);

		System.out.println(index);

		if (memoryMasks.containsKey(index)) {
			return memoryMasks.get(index);
		}

		if (index == 35) {
			if (c == '0') {
				result.add("X");
			}
			if (c == '1') {
				result.add("X");
			}
			if (c == 'X') {
				result.add("0");
				result.add("1");
			}
		} else if (c == '0' || c == '1') {
			List<String> generated = generateMasks(index + 1);
			generated.forEach(g -> result.add("X" + g));
		} else if (c == 'X') {
			List<String> generated = generateMasks(index + 1);
			generated.forEach(g -> result.add("0" + g));
			generated.forEach(g -> result.add("1" + g));
		}

		memoryMasks.put(index, result);

		System.out.println("index done " + index);
		return result;
	}

	private long applyMask(long value, String mask) {
		List<Integer> collect = mask.chars().boxed().collect(Collectors.toList());
		for (int i = 0; i < collect.size(); i++) {
			Integer c = collect.get(i);
			if (c == 'X') {
				continue;
			}
			if (c == '1') {
				long binary = 1L << (collect.size() - i - 1);
				value |= binary;
			}
			if (c == '0') {
				long binary;
				binary = 1L << (collect.size() - i - 1L);
				binary = binary & ~(1L << 63);
				binary = ~binary & ~(1L << 63);
				value &= binary;
			}
		}
		return value;
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
	}

	@Override
	protected void puzzle1() {
		long sum = memory.keySet().stream().map(key -> memory.get(key)).mapToLong(Long::longValue).sum();
		System.out.println(sum);
	}

	public static class Memory {
		long index;
		long value;
	}
}
