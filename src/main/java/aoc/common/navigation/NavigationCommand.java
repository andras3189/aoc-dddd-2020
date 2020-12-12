package aoc.common.navigation;

public class NavigationCommand {
	public String cmd;
	public int amount;

	public NavigationCommand(String cmd, int amount) {
		this.cmd = cmd;
		this.amount = amount;
	}

	public boolean isAbsoluteDirection() {
		return "N".equals(cmd) || "E".equals(cmd) || "W".equals(cmd) || "S".equals(cmd);
	}

	public boolean isRotation() {
		return "L".equals(cmd) || "R".equals(cmd);
	}

	public int toRotation() {
		switch (cmd) {
		case "N":
			return 0;
		case "E":
			return 90;
		case "S":
			return 180;
		case "W":
			return 270;
		case "L":
			return -amount;
		case "R":
			return amount;
		default:
			System.out.println("wrong command");
			return 0;
		}
	}

	@Override
	public String toString() {
		return String.format("%s%d", cmd, amount);
	}
}