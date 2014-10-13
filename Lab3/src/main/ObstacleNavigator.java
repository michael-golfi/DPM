package main;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import constants.SensorConstants;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class ObstacleNavigator extends Thread {
	private Navigator navigator;
	private UltrasonicSensor ultrasonicSensor;
	private double x, y;

	/**
	 * An obstacle navigator that runs the default navigation but guides the
	 * robot around blocks.
	 * 
	 * @param ultrasonicSensor
	 * @param navigator
	 */
	public ObstacleNavigator(UltrasonicSensor ultrasonicSensor,
			Navigator navigator) {
		this.ultrasonicSensor = ultrasonicSensor;
		this.navigator = navigator;
	}

	/**
	 * Avoid block by navigating around and re-setting destination.
	 */
	public void avoidBlock() {
		navigator.turnTo(90);
		navigator.travelDistance(30);
		navigator.turnTo(-90);
		navigator.travelDistance(30);
		navigator.turnTo(-45);
		navigator.travelTo(x, y);
	}

	/**
	 * Query the ultrasonic sensor for obstacles and update the navigators
	 * current location.
	 */
	private void lookForObjects() {
		x = navigator.currentDestination.getX();
		y = navigator.currentDestination.getY();

		if (ultrasonicSensor.getDistance() < SensorConstants.SENSOR_DISTANCE)
			avoidBlock();
	}

	@Override
	public void run() {
		while (true) {
			lookForObjects();
			Delay.msDelay(100);
		}
	}
}