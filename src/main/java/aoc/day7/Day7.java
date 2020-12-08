package aoc.day7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.TheDayBase;

public class Day7 extends TheDayBase {

	private final Set<Bag> allBags = new HashSet<>();

	public Day7(boolean multiLineInput) {
		super(multiLineInput);
		//input = readFile("day7/dummy");
	}

	public static void main(String[] args) {
		Day7 day = new Day7(false);
		day.processInput();
		day.puzzle1(); // 348
		day.puzzle2(); // 18885
	}

	@Override
	protected String getInputFilename() {
		return "day7/day7";
	}

	@Override
	protected void puzzle1() {
		Set<Bag> shiny_gold = allBags.stream().filter(bag -> bag.canReach("shiny gold")).collect(Collectors.toSet());
		System.out.println(shiny_gold.size());
		// printBags(shiny_gold);
	}

	@Override
	protected void puzzle2() {
		Bag myBag = allBags.stream().filter(bag -> "shiny gold".equals(bag.name)).findFirst().get();
		System.out.println(myBag.countChildren());
	}

	private void processInput() {
		for (String line : input) {
			String[] split = line.split("bags contain");
			String name = split[0].trim();
			Bag bag = getExistingBagIfPresent(name).orElse(new Bag(name));
			if (!split[1].contains("no other bags")) {
				String[] childrenSplit = split[1].split(",");
				for (String child : childrenSplit) {
					int number = getChildBagNumber(child);
					String childName = getChildBagName(child);
					Bag childBag = getExistingBagIfPresent(childName).orElse(new Bag(childName));
					bag.children.put(childBag, number);
					allBags.add(childBag);
				}
			}
			allBags.add(bag);
		}
		// printBags(allBags);
	}

	private Optional<Bag> getExistingBagIfPresent(String nameToFind) {
		return allBags.stream().filter(bag -> bag.name.equals(nameToFind)).findFirst();
	}

	private int getChildBagNumber(String childString) {
		int number = 0;
		Pattern pattern = Pattern.compile("([0-9]*).*");
		Matcher matcher = pattern.matcher(childString.trim());
		if (matcher.find()) {
			number = parseInt(matcher.group(1).trim());
		}
		return number;
	}

	private String getChildBagName(String childString) {
		String childName = "";
		Pattern pattern2 = Pattern.compile("[0-9]*([\\w\\s]*)bag[s]?[.]?");
		Matcher matcher2 = pattern2.matcher(childString.trim());
		if (matcher2.find()) {
			childName = matcher2.group(1).trim();
		}
		return childName.trim();
	}

	private void printBags(Set<Bag> bags) {
		for (Bag bag : bags) {
			System.out.println(bag);
		}
	}

	public static class Bag {
		public String name;
		public Map<Bag, Integer> children = new HashMap<>();

		public Bag(String name) {
			this.name = name;
		}

		public boolean canReach(String targetName) {
			if (children.isEmpty()) {
				return false;
			}
			boolean canReach = false;
			for (Bag child : children.keySet()) {
				if (targetName.equals(child.name)) {
					return true;
				} else {
					canReach |= child.canReach(targetName);
				}

			}
			return canReach;
		}

		public long countChildren() {
			long count = 0;
			for (Bag child : children.keySet()) {
				count += children.get(child) * child.countChildren() + children.get(child);
			}
			return count;
		}

		@Override
		public String toString() {
			String childNames = children.keySet().stream().map(child -> child.name).collect(Collectors.joining(", "));
			return "Bag{" + "name='" + name + '\'' + ", children=" + childNames + '}';
		}
	}
}