package controller;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import constants.Constants;
import constants.Sensor;

/**
 * Provide an abstraction layer for ultrasonic controller to read distances
 * around the robot, in 90 degree segments.
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class UltrasonicController {
	private MotorController motorController;
	private UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(
			Sensor.ULTRASONIC);

	public UltrasonicController(MotorController motorController) {
		this.motorController = motorController;
	}

	/**
	 * Read distance ahead
	 * 
	 * @return front distance
	 */
	public int getDistance() {
		return ultrasonicSensor.getDistance();
	}

	/**
	 * Gets the distances in front, to the left, behind, and to the right of the
	 * robot.
	 * 
	 * @return int[4] of distances read in clockwise order
	 */
	public int[] getAllDistances() {
		int[] distances = new int[4];

		distances[0] = getFilteredData();
		motorController.rotate(-90);
		distances[1] = getFilteredData();
		motorController.rotate(-90);
		distances[2] = getFilteredData();
		motorController.rotate(-90);
		distances[3] = getFilteredData();

		return distances;
	}

	/**
	 * Gets filtered distance values from the ultrasonic sensor
	 * 
	 * @return distance
	 */
	public int getFilteredData() {
		ultrasonicSensor.ping();
		Delay.msDelay(100);
		int distance = ultrasonicSensor.getDistance();
		return distance > 90 ? 90 : distance;
	}

	/**
	 * Get number of tiles ahead of robot
	 * 
	 * @return tiles ahead
	 */
	public int getTilesAhead() {
		return getFilteredData() / 30;
	}

	/**
	 * Check if the next tile is blocked
	 * 
	 * @return true if it is blocked
	 */
	public boolean isBlocked() {
		return getTilesAhead() < Constants.TILE_LENGTH;
	}
}
