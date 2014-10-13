import odometry.Odometer;
import navigation.Navigator;
import lcd.LCDInfo;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import lejos.util.Timer;
import constants.Constants;
import controller.MotorController;
import controller.UltrasonicController;
import orientation.OrientationType;
import orientation.Orienteer;
import orientation.Orienteer2;
import utils.ThreadStart;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Lab5 {

	public static void main(String[] args) {
		MotorController motorController = new MotorController();
		initializeComponents(motorController);
	}

	/**
	 * Start all robot components
	 * 
	 * @param motorController
	 */
	private static void initializeComponents(MotorController motorController) {		
		UltrasonicController ultrasonicController = new UltrasonicController(
				motorController);
		Button.waitForAnyPress();
		Odometer odometer = new Odometer(motorController);
		Navigator navigator = new Navigator(motorController, odometer);
		//Orienteer orienteer = chooseOrientation(ultrasonicController, navigator);
		Orienteer2 orienteer2 = new Orienteer2(ultrasonicController, navigator);
		orienteer2.deterministic();

		//startOdometerDisplay(odometer);
		ThreadStart.startAll(new Thread[] { odometer });
	}

	/**
	 * Starts printing the odometer readings to screen
	 * 
	 * @param odometer
	 */
	private static void startOdometerDisplay(Odometer odometer) {
		LCDInfo lcdInfo = new LCDInfo(odometer);
		new Timer(Constants.REFRESH_PERIOD, lcdInfo).start();
	}

	/**
	 * Choose the type of orientation to use based on button input
	 * 
	 * @param ultrasonicController
	 * @param navigator
	 * @return Orienteer chosen
	 */
	private static Orienteer chooseOrientation(
			UltrasonicController ultrasonicController, Navigator navigator) {
		LCD.drawString("< Det | Stoch >", 0, 0);
		if (Button.waitForAnyPress() == Button.ID_LEFT)
			return new Orienteer(ultrasonicController, navigator,
					OrientationType.DETERMINISTIC);
		else
			return new Orienteer(ultrasonicController, navigator,
					OrientationType.STOCHASTIC);
	}

}
