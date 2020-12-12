package aoc.common.navigation;

public class NavigationUtil {

	public static int normalizeRotation(int rotation) {
		int newRotation = rotation;
		while (newRotation < 0) {
			newRotation += 360;
		}
		while (newRotation >= 360) {
			newRotation -= 360;
		}
		return newRotation;
	}
}
