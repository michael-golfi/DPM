import orientation.DeterministicOrienter;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Timer;
import navigation.Navigator;
import odometry.Odometer;
import utils.ThreadStart;
import constants.Constants;
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
		MotorController motorController = new MotorController();
		initializeComponents(motorController);
	}

	/**
	 * Start all robot components
	 * 
	 * @param motorController
	 */
	private static void initializeComponents(MotorController motorController) {
		UltrasonicController ultrasonicController = 
				new UltrasonicController(motorController);
		Odometer odometer = new Odometer(motorController);
		Navigator navigator = new Navigator(motorController, odometer);
		DeterministicOrienter deterministicOrienter = 
				new DeterministicOrienter(ultrasonicController, navigator);
		
		deterministicOrienter.start();
		odometer.start();
	}
}
