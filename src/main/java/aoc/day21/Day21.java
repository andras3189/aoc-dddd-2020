package aoc.day21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.DayBase;

public class Day21 extends DayBase {

	public static final String REGEX = "([a-z\\s]+)\\s\\(contains\\s([a-z\\s,]+)\\)";
	Pattern pattern = Pattern.compile(REGEX);

	List<Food> foods;

	Map<String, List<String>> allergenToIngredients;
	Map<String, Integer> ingredientCount;
	List<String> dangerousIngredients;

	public static void main(String[] args) {
		Day21 day = new Day21();
//		day.setOnlyDummyInput(true);
//		day.setOnlyRealInput(true);
		day.run();
	}

	@Override
	protected void processInput() {
		foods = new ArrayList<>();
		for (String line : input) {
			Matcher m = pattern.matcher(line);
			if (m.find()) {
				Food food = new Food();
				food.ingredients = Arrays.stream(m.group(1).split(" ")).map(String::trim).collect(Collectors.toList());
				food.allergens =  Arrays.stream(m.group(2).split(",")).map(String::trim).collect(Collectors.toList());
				foods.add(food);
			}
		}
		allergenToIngredients = new HashMap<>();
		dangerousIngredients = new ArrayList<>();
		ingredientCount =  new HashMap<>();

		for (Food food : foods) {
			for (String ingredient : food.ingredients) {
				if (!ingredientCount.containsKey(ingredient)) {
					ingredientCount.put(ingredient, 0);
				}
				ingredientCount.put(ingredient, ingredientCount.get(ingredient) + 1);
			}
		}


		for (Food food : foods) {
			for (String allergen : food.allergens) {
				List<String> candidates = allergenToIngredients.getOrDefault(allergen, new ArrayList<>());
				if (candidates.isEmpty()) {
					candidates.addAll(food.ingredients);
				}
				candidates.retainAll(food.ingredients);
				allergenToIngredients.put(allergen, candidates);
				if (candidates.size() == 1) {
					dangerousIngredients.add(candidates.get(0));
				}
			}
		}

		boolean changed = true;
		while (changed) {
			changed = false;
			for (String allergen : allergenToIngredients.keySet()) {
				List<String> candidates = allergenToIngredients.get(allergen);
				if (candidates.size() == 1) {
					continue;
				}
				candidates.removeAll(dangerousIngredients);
				if (candidates.size() == 1) {
					changed = true;
					dangerousIngredients.add(candidates.get(0));
				}
			}
		}

		for (String allergen : allergenToIngredients.keySet()) {
			List<String> candidates = allergenToIngredients.get(allergen);
			if (candidates.size() > 1) {
				dangerousIngredients.addAll(candidates);
			}
		}

		long count = 0;
		for (Food food : foods) {
			for (String ingredient : food.ingredients) {
				if (!dangerousIngredients.contains(ingredient)) {
					count++;
				}
			}
		}

		Map<String, String> sorted = new HashMap<>();
		for (String allergen : allergenToIngredients.keySet()) {
			List<String> candidates = allergenToIngredients.get(allergen);

			if (candidates.size() == 1) {
				sorted.put(allergen, candidates.get(0));
			}
		}

		String result = "";
		List<String> sortedKeys = sorted.keySet().stream().sorted().collect(Collectors.toList());
		for (String key : sortedKeys) {
			result += sorted.get(key);
			result += ",";
		}

		System.out.println(count);
		System.out.println(result.substring(0, result.length() -1));

	}

	@Override
	protected void puzzle1() {
		System.out.println();
	}

	@Override
	protected void reset() {

	}

	@Override
	protected void puzzle2() {

	}

	public static class Food {
		public List<String> ingredients = new ArrayList<>();
		public List<String> allergens= new ArrayList<>();
	}
}
