package aoc.day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

import aoc.TheDayBase;

public class Day6 extends TheDayBase {

	public Day6(boolean multiLineInput) {
		super(multiLineInput);
	}

	public static void main(String[] args) {
		Day6 day6 = new Day6(true);
		day6.puzzle1();
		day6.puzzle2();
	}

	@Override
	protected String getInputFilename() {
		return "day6/day6";
	}

	@Override
	protected void puzzle1() {
		List<Group> groups = collectGroups(groupedInput);
		LongSummaryStatistics collect = groups.stream().map(map -> map.answers.keySet().size()).collect(Collectors.summarizingLong(el -> el));
		System.out.println(collect.getSum()); // 6437
	}


	@Override
	protected void puzzle2() {
		List<Group> groups = collectGroups(groupedInput);
		LongSummaryStatistics collect = groups.stream()
				.map(group -> group.answers.keySet().stream().filter(key -> group.answers.get(key) == group.groupSize).count())
				.collect(Collectors.summarizingLong(count -> count));
		System.out.println(collect.getSum()); // 3229
	}

	private List<Group> collectGroups(List<List<String>> input) {
		List<Group> groups = new ArrayList<>();
		for (List<String> linesForGroup : input) {
			final Group group = new Group();
			group.groupSize = linesForGroup.size();
			for (String lineForPerson : linesForGroup) {
				lineForPerson.chars().forEach(ch -> group.answers.put(ch, group.answers.getOrDefault(ch, 0) + 1));
			}
			groups.add(group);
		}
		return groups;
	}


	public static class Group {
		public Map<Integer, Integer> answers = new HashMap<>();
		public int groupSize;
	}
}
