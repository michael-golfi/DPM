package controller;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class UltrasonicController {
	private MotorController motorController;
	private UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
	private final int FORWARD = 0, LEFT = -90, RIGHT = 90, BACKWARD = 180;
	
	public UltrasonicController(MotorController motorController) {
		this.motorController = motorController;
	}
	
	/**
	 * Read front distance
	 * @return front distance
	 */
	public int getFrontDistance() {
		motorController.rotate(FORWARD);
		return ultrasonicSensor.getDistance();
	}

	/**
	 * Turn sensor left and read distance
	 * @return left distance
	 */
	public int getLeftDistance() {
		motorController.rotate(LEFT);
		int distance = ultrasonicSensor.getDistance();
		Delay.msDelay(100);
		motorController.rotate(RIGHT);
		return distance;
	}

	/**
	 * Turn sensor right and read distance
	 * @return right distance
	 */
	public int getRightDistance() {
		motorController.rotate(RIGHT);
		int distance = ultrasonicSensor.getDistance();
		Delay.msDelay(100);
		motorController.rotate(LEFT);
		return distance;
	}
	
	/**
	 * Turn around and get the distance
	 * @return
	 */
	public int getBackwardsDistance(){
		motorController.rotate(BACKWARD);
		int sensorDistance = ultrasonicSensor.getDistance();
		Delay.msDelay(100);
		motorController.rotate(-BACKWARD);
		return sensorDistance;
	}
	
	/**
	 * Rotate sensor to face forward
	 */
	public void resetSensor(){
		motorController.rotateSensor(FORWARD);
	}
}
