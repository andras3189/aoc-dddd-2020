package aoc.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day16 extends DayBase {

	public static void main(String[] args) {
		Day16 day = new Day16();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	public static final String regex = "([\\w\\s]*):\\s([\\d]*)-([\\d]*) or ([\\d]*)-([\\d]*)";
	public Map<String, Range> ticketFields;
	public List<List<Integer>> otherTickets;
	public List<Integer> myTicket;
	public List<List<Integer>> validOtherTickets;

	@Override
	protected List<String> getAdditionalInputFileNames() {
		return Arrays.asList("real2");
	}


	@Override
	protected void processInput() {

		ticketFields = new HashMap<>();
		otherTickets = new ArrayList<>();
		validOtherTickets = new ArrayList<>();
		for (String line : input) {


			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String field = matcher.group(1);
				int s1 = parseInt(matcher.group(2));
				int e1 = parseInt(matcher.group(3));
				int s2 = parseInt(matcher.group(4));
				int e2 = parseInt(matcher.group(5));

				ticketFields.put(field, new Range(s1, e1, s2, e2));
			}
		}


		boolean process = false;
		boolean myTicketRow = false;
		for (String line : input) {
			if (line.contains("nearby tickets:")) {
				process = true;
				continue;
			}
			if (line.contains("your ticket:")) {
				myTicketRow = true;
				continue;
			}
			if (myTicketRow) {
				myTicket = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
				myTicketRow = false;
			}
			if (process) {
				otherTickets.add(new ArrayList(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList())));
			}
		}
	}


	@Override
	protected void reset() {
	}

	@Override
	protected void puzzle1() {
		long sum = 0;
		for (List<Integer> otherTicket : otherTickets) {
			boolean validTicket = true;
			for (Integer value : otherTicket) {
				if (!isValid(value)) {
					sum += value;
					validTicket = false;
				}
			}
			if (validTicket) {
				validOtherTickets.add(otherTicket);
			}
		}
		System.out.println(sum);
	}

	public Map<Integer, List<String>> possibilities;

	@Override
	protected void puzzle2() {
		possibilities = new HashMap<>();
		for (int j = 0; j < validOtherTickets.get(0).size(); j++) { // columns
			Map<String, Range> remainingTicketFields = new HashMap<>(ticketFields);
			for (List<Integer> otherTicket : validOtherTickets) { // rows
				List<String> notInRangeOfFields = new ArrayList<>();
				for (String remaining : remainingTicketFields.keySet()) {
					if (!isInRange(otherTicket.get(j), remainingTicketFields.get(remaining))) {
						notInRangeOfFields.add(remaining);
					}
				}
				notInRangeOfFields.forEach(remainingTicketFields::remove);
			}
			possibilities.put(j, new ArrayList<>(remainingTicketFields.keySet()));
		}

		long sol = 1;

		List<String> usedFields = new ArrayList<>();
		List<Integer> usedIndexes = new ArrayList<>();

		int indexForMinPossibilities;
		while (usedFields.size() < myTicket.size()) {
			indexForMinPossibilities = getIndexWithMinPossibilities(usedIndexes);
			String newField = getNewField(usedFields, possibilities.get(indexForMinPossibilities));
			if (newField.startsWith("departure")) {
				sol *= myTicket.get(indexForMinPossibilities);
			}
			usedIndexes.add(indexForMinPossibilities);
			usedFields.add(newField);
		}

		System.out.println(sol);
	}

	private int getIndexWithMinPossibilities(List<Integer> usedIndexes) {
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < possibilities.size(); i++) {
			if (!usedIndexes.contains(i) && possibilities.get(i).size() < min) {
				min = possibilities.get(i).size();
				minIndex = i;
			}
		}
		return minIndex;
	}

	private String getNewField(List<String> usedFields, List<String> possibleFieldsForIndex) {
		if (usedFields.size() + 1 != possibleFieldsForIndex.size()) {
			System.out.println("not prepared for this case");
		}
		return possibleFieldsForIndex.stream().filter(field -> !usedFields.contains(field)).findFirst().orElseThrow();
	}

	private boolean isValid(Integer value) {
		boolean valid = false;
		for (String key : ticketFields.keySet()) {
			Range range = ticketFields.get(key);

			if (isInRange(value, range)) {
				valid = true;
				break;
			}
		}
		return valid;
	}

	private boolean isInRange(Integer value, Range range) {
		return (range.a <= value && value <= range.b) || (range.c <= value && value <= range.d);
	}

	private static class Range {
		public int a, b, c, d;

		private Range(int a, int b, int c, int d) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}
	}

}
