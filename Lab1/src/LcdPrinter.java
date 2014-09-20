import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Delay;

/**
 * An abstraction class to handle writing to the NXT LCD screen 
 * @author Michael
 *
 */
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

	public void run() {
		while (true) {
			LCD.clear();
			printType(BUTTON_PRESSED);
			Delay.msDelay(200);
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
	 * Print controller choices
	 */
	public static void printControllerChoices() {
		LCD.clear();
		LCD.drawString("Left: BangBang", 0, 0);
		LCD.drawString("Right: P", 0, 1);
	}
}