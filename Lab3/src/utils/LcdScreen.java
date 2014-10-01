package utils;

import lejos.nxt.LCD;
import math.Round;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class LcdScreen {
	/**
	 * Ask user to choose navigation mode
	 */
	public static void displayMainMenu() {
		LCD.clear();
		LCD.drawString("< Left | Right >", 0, 0);
		LCD.drawString("       |        ", 0, 1);
		LCD.drawString("Avoid  | Navi   ", 0, 2);

		LCD.drawString("Left: Avoidance", 0, 0);
		LCD.drawString("Right: Navigation", 0, 1);
	}

	/**
	 * Display coordinates and direction currently in odometer
	 * 
	 * @param position
	 */
	public static void displayOdometerInformation(double[] position) {
		LCD.drawString("X:              ", 0, 0);
		LCD.drawString("Y:              ", 0, 1);
		LCD.drawString("T:              ", 0, 2);
		for (int i = 0; i < 3; i++) {
			String rounded = Round.round(position[i], 2);
			LCD.drawString(rounded, 3, i);
		}
	}
}
