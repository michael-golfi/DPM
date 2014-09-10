

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class LcdPrinter extends Thread {
	private final int BUTTON_PRESSED;

	/**
	 * Handles printing to the NXT LCD display
	 * 
	 * @param buttonPressed
	 * @param controller
	 */
	public LcdPrinter(int buttonPressed) {
		BUTTON_PRESSED = buttonPressed;
	}

	/**
	 * Start the printer
	 */
	public void run() {
		while (true) {
			LCD.clear();
			printType(BUTTON_PRESSED);
			sleep();
		}
	}

	/**
	 * Print Controller type in use
	 * 
	 * @param buttonPressed
	 */
	private void printType(int buttonPressed) {
		if (BUTTON_PRESSED == Button.ID_LEFT)
			LCD.drawString("Bang-Bang", 0, 0);
		else if (BUTTON_PRESSED == Button.ID_RIGHT)
			LCD.drawString("P-type", 0, 0);
	}

	/**
	 * Sleeps for 200ms
	 */
	private void sleep() {
		try {
			sleep(200);
		} catch (InterruptedException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	public static void printControllerChoices() {
		LCD.clear();
		LCD.drawString("Left: BangBang", 0, 0);
		LCD.drawString("Right: P", 0, 1);
	}
}