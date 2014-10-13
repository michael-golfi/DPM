package controllers;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import constants.SensorConstants;

public abstract class AbstractController extends Thread {
	protected UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorConstants.ULTRASONIC);
	protected MotorController motorController = new MotorController();

	public void run() {
		while (true) {
			processNewDistance();
			Delay.msDelay(10);
		}
	}

	/**
	 * Correct motors based on current ultrasonic distance
	 */
	public abstract void processNewDistance();
}
