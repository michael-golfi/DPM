package main;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import odometry.Odometer;
import odometry.OdometryDisplay;
import utils.LcdScreen;
import utils.ThreadStart;
import controller.MotorController;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Lab3 {
	public static void main(String[] args) {
		MotorController motorController = new MotorController();
		promptUser(motorController);
	}

	/**
	 * Choose navigation mode
	 * 
	 * @param motorController
	 */
	static void promptUser(MotorController motorController) {
		LcdScreen.displayMainMenu();
		if (Button.waitForAnyPress() == Button.ID_LEFT) {
			startNavigation(motorController);
		} else {
			startNavigationWithoutAvoidance(motorController);
		}
	}

	/**
	 * Start robot navigation segment with obstacle avoidance
	 * 
	 * @param motorController
	 */
	static void startNavigation(MotorController motorController) {
		Odometer odometer = new Odometer(motorController);
		OdometryDisplay odometryDisplay = new OdometryDisplay(odometer);
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
		Navigator navigator = new Navigator(motorController, odometer);
		ObstacleNavigator obstacleNavigator = new ObstacleNavigator(
				ultrasonicSensor, navigator);

		ThreadStart.startAll(new Thread[] { odometer, odometryDisplay,
				obstacleNavigator });

		navigator.travelTo(0, 60);
		navigator.travelTo(40, 10);
	}

	/**
	 * Start robot navigation segment
	 * 
	 * @param motorController
	 */
	static void startNavigationWithoutAvoidance(MotorController motorController) {
		Odometer odometer = new Odometer(motorController);
		OdometryDisplay odometryDisplay = new OdometryDisplay(odometer);
		Navigator navigator = new Navigator(motorController, odometer);

		ThreadStart.startAll(new Thread[] { odometer, odometryDisplay,
				navigator });
	}

	MotorController motorController = new MotorController();
}
