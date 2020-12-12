package aoc.day12;

import static aoc.common.navigation.NavigationUtil.normalizeRotation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.DayBase;
import aoc.common.navigation.Coordinate;
import aoc.common.navigation.NavigationCommand;

public class Day12 extends DayBase {

	public Coordinate ship, waypoint;
	public int rotation;

	private List<NavigationCommand> commands;

	public static void main(String[] args) {
		new Day12().run();
	}

	@Override
	protected void processInput() {
		commands = new ArrayList<>();
		Pattern pattern = Pattern.compile("(\\w)(\\d*)");
		for (String line : input) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String command = matcher.group(1);
				int amount = parseInt(matcher.group(2));
				commands.add(new NavigationCommand(command, amount));
			}
		}
	}

	@Override
	protected void reset() {
		ship = new Coordinate(0, 0);
		rotation = 90;
		waypoint = new Coordinate(10, 1);
	}

	@Override
	protected void puzzle1() {
		for (NavigationCommand command : commands) {
			if (command.isRotation()) {
				rotation += command.toRotation();
				rotation = normalizeRotation(rotation);
			} else if (command.isAbsoluteDirection()) {
				Coordinate coordinate = Coordinate.from(command.toRotation(), command.amount);
				ship = ship.add(coordinate);
			} else {
				Coordinate coordinate = Coordinate.from(rotation, command.amount);
				ship = ship.add(coordinate);
			}
			//System.out.printf("%s rotation=%d ship=%s%n", command, rotation, ship);
		}
		System.out.println(ship.distanceFromOrigo());
	}

	@Override
	protected void puzzle2() {
		for (NavigationCommand command : commands) {
			if (command.isRotation()) {
				waypoint = waypoint.rotateAroundOrigo(command.toRotation());
			} else if (command.isAbsoluteDirection()) {
				Coordinate coordinate = Coordinate.from(command.toRotation(), command.amount);
				waypoint = waypoint.add((coordinate));
			} else {
				ship = ship.add(waypoint.multiply(command.amount));
			}
			//System.out.printf("%s waypoint=%s ship=%s%n", command, waypoint, ship);
		}
		System.out.println(ship.distanceFromOrigo());
	}


}
