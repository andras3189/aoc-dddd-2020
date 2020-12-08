package aoc.common.bag;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Bag {
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

	public static void printBags(Set<Bag> bags) {
		for (Bag bag : bags) {
			System.out.println(bag);
		}
	}

	@Override
	public String toString() {
		String childNames = children.keySet().stream().map(child -> child.name).collect(Collectors.joining(", "));
		return "Bag{" + "name='" + name + '\'' + ", children=" + childNames + '}';
	}
}