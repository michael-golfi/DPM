package controller;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import constants.Sensor;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public abstract class AbstractController extends Thread {
	protected MotorController motorController = new MotorController();
	protected UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(
			Sensor.ULTRASONIC);

	/**
	 * Correct motors based on current ultrasonic distance
	 */
	public abstract void processNewDistance();

	public void run() {
		while (true) {
			processNewDistance();
			Delay.msDelay(10);
		}
	}
}
