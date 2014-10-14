package constants;

import lejos.nxt.SensorPort;

/**
 * Place holder for sensor constants
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class Sensor {
	/**
	 * Distance to maintain from sensor to block: {@value} cm
	 */
	public static final int SENSOR_DISTANCE = 20;

	/**
	 * SensorPort S1 being used for the ultrasonic sensor
	 */
	public static final SensorPort ULTRASONIC = SensorPort.S1;

	/**
	 * SensorPort S1 being used for the ultrasonic sensor
	 */
	public static final SensorPort COLOR = SensorPort.S2;
}