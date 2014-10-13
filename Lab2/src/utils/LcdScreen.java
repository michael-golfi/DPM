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
		LCD.drawString(" Float | Drive  ", 0, 2);
		LCD.drawString("motors | in a   ", 0, 3);
		LCD.drawString("       | square ", 0, 4);
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
	
	public static void displayStartingXY(double x, double y){
		LCD.drawString("XY:             ", 0, 3);
		LCD.drawString(Round.round(x,2) + " " + Round.round(y,2), 0, 3);
	}
	
	public static void displayCount(int count){
		LCD.drawString("Count " + count, 0, 4);
	}
	
	public static void displayNewTheta(double roundTheta){
		LCD.drawString("Theta: " + Round.round(roundTheta, 0), 0, 5);
	}
}
