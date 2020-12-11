package aoc.day7;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.DayBase;
import aoc.common.bag.Bag;

public class Day7 extends DayBase {

	private Set<Bag> allBags = new HashSet<>();

	public static void main(String[] args) {
		new Day7().run();
	}

	@Override
	protected void puzzle1() {
		Set<Bag> shiny_gold = allBags.stream().filter(bag -> bag.canReach("shiny gold")).collect(Collectors.toSet());
		System.out.println(shiny_gold.size());
		// Bag.printBags(shiny_gold);
	}

	@Override
	protected void puzzle2() {
		Bag myBag = allBags.stream().filter(bag -> "shiny gold".equals(bag.name)).findFirst().get();
		System.out.println(myBag.countChildren());
	}

	@Override
	protected void processInput() {
		allBags = new HashSet<>();
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
		// Bag.printBags(allBags);
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
}