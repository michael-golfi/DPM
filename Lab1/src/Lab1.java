import lejos.nxt.Button;
import Controllers.AbstractController;
import Controllers.BangBangController;
import Controllers.PController;

/**
 * Group 43 - ECSE 211
 * 
 * @author Michael Golfi - 260552298
 * @author Paul Albert-Lebrun - 260507074
 *
 */
public class Lab1 {

	public static void main(String[] args) {
		LcdPrinter.printControllerChoices();

		int buttonPressed = Button.waitForAnyPress();

		try {
			startController(buttonPressed);
			startPrinter(buttonPressed);
		} catch (Exception ex) {
			System.exit(-1);
		}

		Button.waitForAnyPress();
		System.exit(0);
	}

	/**
	 * Chooses the controller based on button input and starts it.
	 * 
	 * @param buttonPressed
	 * @return controller
	 * @throws Exception
	 */
	private static void startController(int buttonPressed) throws Exception {
		AbstractController controller;
		if (buttonPressed == Button.ID_LEFT)
			controller = new BangBangController();
		else if (buttonPressed == Button.ID_RIGHT)
			controller = new PController();
		else
			throw new Exception("Bad Input");

		controller.start();
	}

	/**
	 * Start printing to the NXT LCD
	 * 
	 * @param option
	 * @param controller
	 */
	private static void startPrinter(int option) {
		new LcdPrinter(option).start();
	}
}