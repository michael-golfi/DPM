import lejos.nxt.Button;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;
import navigation.Navigator;
import odometry.Odometer;
import orientation.DeterministicOrienter;
import utils.ThreadStart;
import controller.MotorController;
import controller.UltrasonicController;

/**
 * DPM Lab Group 43 - Lab 5 Orientation
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Lab5 {

	public static void main(String[] args) {
		initializeComponents();
	}

	/**
	 * Start all robot components
	 * 
	 * @param motorController
	 */
	private static void initializeComponents() {
		Button.waitForAnyPress();

		MotorController motorController = new MotorController();
		UltrasonicController ultrasonicController = new UltrasonicController(
				motorController);
		Odometer odometer = new Odometer(motorController);
		Navigator navigator = new Navigator(motorController, odometer);
		DeterministicOrienter deterministicOrienter = new DeterministicOrienter(
				ultrasonicController, navigator, odometer);
		odometer.start();
		deterministicOrienter.start();
	}
}
