package controller;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import constants.Constants;
import constants.Sensor;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
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
	public int getFrontDistance() {
		return ultrasonicSensor.getDistance();
	}

	/**
	 * Turn left and read distance
	 * 
	 * @return left distance
	 */
	public int getLeftDistance() {
		motorController.rotate(Constants.LEFT);
		int sensorDistance = getFilteredData();
		motorController.rotate(Constants.RIGHT);
		return sensorDistance;
	}

	/**
	 * Turn right and read distance
	 * 
	 * @return right distance
	 */
	public int getRightDistance() {
		motorController.rotate(Constants.RIGHT);
		int sensorDistance = getFilteredData();
		motorController.rotate(Constants.LEFT);
		return sensorDistance;
	}

	/**
	 * Turn around and get the distance
	 * 
	 * @return distance behind
	 */
	public int getBackwardsDistance() {
		motorController.rotate(Constants.BACKWARD);
		int sensorDistance = getFilteredData();
		motorController.rotate(-Constants.BACKWARD);
		return sensorDistance;
	}
	
	/**
	 * Turn and get all distances
	 * @return
	 */
	public int[] getAllDistances(){
		int[] distances = new int[4];
		for(int rotation = Constants.LEFT; rotation > -360; rotation += Constants.LEFT){
			distances[rotation % 90] = getFilteredData(); 
			motorController.rotate(rotation);
		}
		return distances;
	}

	/**
	 * Gets filtered distance values from the ultrasonic sensor
	 * 
	 * @return distance
	 */
	public int getFilteredData() {
		ultrasonicSensor.ping();		
		Delay.msDelay(50);		
		int distance = ultrasonicSensor.getDistance();
		return distance > 50 ? 50 : distance;
	}
}
