package aoc.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day19 extends DayBase {

	private static final String REGEX_FULL_RULE = "(\\d*):([\\s|\\d\"a-z]*)";
	private static final String REGEX_EXACT_VALUE_RULE = "\"([a-z]+)\"";
	private static final String REGEX_MESSAGE = "^[a-z]+$";

	private final Pattern fullRulePattern = Pattern.compile(REGEX_FULL_RULE);
	private final Pattern exactValueRulePattern = Pattern.compile(REGEX_EXACT_VALUE_RULE);
	private final Pattern messagePattern = Pattern.compile(REGEX_MESSAGE);

	private List<Rule> allRules;
	private List<String> messages;

	public static void main(String[] args) {
		Day19 day = new Day19();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void reset() {
	}

	@Override
	protected void processInput() {
		processInputRules();
		processInputMessages();
	}

	private void processInputRules() {
		allRules = new ArrayList<>();
		for (String line : input) {
			Matcher fullRuleMatcher = fullRulePattern.matcher(line);
			if (fullRuleMatcher.find()) {
				String id = fullRuleMatcher.group(1);
				String rule = fullRuleMatcher.group(2);
				Matcher exactValueRuleMatcher = exactValueRulePattern.matcher(rule);
				Rule currentRule = getOrCreateRule(id);
				if (exactValueRuleMatcher.find()) {
					currentRule.exactValue = exactValueRuleMatcher.group(1);
				} else {
					for (String subRules : rule.split("\\|")) {
						String[] split = subRules.trim().split("\\s");
						List<Rule> subRuleList = Arrays.stream(split).map(this::getOrCreateRule).collect(Collectors.toList());
						currentRule.subRulesList.add(subRuleList);
					}
				}
			}
		}
	}

	private void processInputMessages() {
		messages = new ArrayList<>();
		for (String line : input) {
			Matcher messageMatcher = messagePattern.matcher(line);
			if (messageMatcher.find()) {
				messages.add(messageMatcher.group());
			}
		}
	}

	private Rule getOrCreateRule(String id) {
		Optional<Rule> existingRule = allRules.stream().filter(rule -> rule.id == parseInt(id)).findFirst();
		if (existingRule.isPresent()) {
			return existingRule.get();
		}
		Rule rule = new Rule(id);
		allRules.add(rule);
		return rule;
	}

	@Override
	protected void puzzle1() {
		Set<String> options = allRules.stream().filter(rule -> rule.id == 0).findFirst().orElseThrow().getOptions();
		long validSum = messages.stream().filter(options::contains).count();
		System.out.println(validSum);
	}

	@Override
	protected void puzzle2() {
		Set<String> options = allRules.stream().filter(rule -> rule.id == 0).findFirst().orElseThrow().getOptions();
		long validSum = messages.stream().filter(options::contains).count();

		List<String> invalidMessages = messages.stream().filter(message -> !options.contains(message)).collect(Collectors.toList());
		String[] options42 = getOrCreateRule("42").getOptions().toArray(new String[0]);
		String[] options31 = getOrCreateRule("31").getOptions().toArray(new String[0]);
		long unInvalidSum = 0;
		for (String message : invalidMessages) {
			ReduceResult reduce42Result = reduce(options42, message);
			ReduceResult reduce31Result = reduce(options31, reduce42Result.message);
			if ("".equals(reduce31Result.message) && reduce42Result.count > reduce31Result.count && reduce31Result.count > 0) {
				unInvalidSum++;
			}
		}
		System.out.println(validSum + unInvalidSum);
	}

	private ReduceResult reduce(String[] options, String message) {
		ReduceResult result = new ReduceResult();
		result.message = message;
		int i = 0;
		while (i < options.length && !"".equals(result.message)) {
			String option = options[i];
			if (result.message.startsWith(option)) {
				result.count++;
				result.message = result.message.substring(option.length());
				i = 0;
			} else {
				i++;
			}
		}
		return result;
	}

	private static class ReduceResult {
		int count;
		String message;
	}

	private static class Rule {
		List<List<Rule>> subRulesList = new ArrayList<>();
		Integer id;
		String exactValue = null;
		Set<String> possibleValues = new HashSet<>();

		private Rule(String id) {
			this.id = Integer.parseInt(id);
		}

		public Set<String> getOptions() {
			if (!possibleValues.isEmpty()) {
				return possibleValues;
			}
			if (exactValue != null) {
				possibleValues.add(exactValue);
				return possibleValues;
			}

			for (List<Rule> subRules : subRulesList) {
				Set<String> possiblePrefixes = new HashSet<>();
				Set<String> newPossiblePrefixes = new HashSet<>();
				for (Rule subRule : subRules) {
					for (String subRuleOption : subRule.getOptions()) {
						for (String possiblePrefix : possiblePrefixes) {
							String newNewPossiblePrefix = possiblePrefix + subRuleOption;
							newPossiblePrefixes.add(newNewPossiblePrefix);
						}
						if (possiblePrefixes.isEmpty()) {
							newPossiblePrefixes.add(subRuleOption);
						}
					}
					possiblePrefixes = newPossiblePrefixes;
					newPossiblePrefixes = new HashSet<>();
				}
				possibleValues.addAll(possiblePrefixes);
			}
			return possibleValues;
		}
	}
}
