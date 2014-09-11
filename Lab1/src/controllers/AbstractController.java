package controllers;

import lejos.nxt.UltrasonicSensor;
import constants.SensorConstants;

public abstract class AbstractController extends Thread {
	protected UltrasonicSensor sensor = new UltrasonicSensor(
			SensorConstants.ULTRASONIC_PORT);
	protected UltrasonicController ultrasonicController = new UltrasonicController(
			sensor);
	protected MotorController motorController = new MotorController();

	public void run() {
		while (true) {
			processNewDistance();
		}
	}

	/**
	 * Controls motors based on current ultrasonic distance value
	 */
	public abstract void processNewDistance();
}
