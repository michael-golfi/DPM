package utils;
import lejos.nxt.LCD;


public class LcdScreen {
	/**
	 * Ask user to drive in a square or float
	 */
	public static void displayMainMenu(){
		LCD.clear();
		LCD.drawString("< Left | Right >", 0, 0);
		LCD.drawString("       |        ", 0, 1);
		LCD.drawString("Do     | With   ", 0, 2);
		LCD.drawString("route  | Obstac-", 0, 3);
		LCD.drawString("       | -cles  ", 0, 4);
	}

	public static void displayOdometerInformation(double[] position){
		LCD.drawString("X:              ", 0, 0);
		LCD.drawString("Y:              ", 0, 1);
		LCD.drawString("T:              ", 0, 2);
		
		for (int i = 0; i < 3; i++) {
			String rounded = Round.round(position[i], 2);
			LCD.drawString(rounded, 3, i);
		}
	}
}
