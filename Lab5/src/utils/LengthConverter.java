package utils;

import constants.MotorConstants;

/**
 * Provide methods to convert robot travel distances and angles to wheel
 * rotational values
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 *
 */
public class LengthConverter {
	/**
	 * Converts travel distance to wheel rotations
	 * 
	 * @param radius
	 * @param distance
	 * @return necessary wheel rotations
	 */
	public static int convertDistance(double distance) {
		return (int) ((180.0 * distance) / (Math.PI * MotorConstants.WHEEL_RADIUS));
	}

	/**
	 * Converts robot rotation angle to wheel rotation values
	 * 
	 * @param radius
	 * @param width
	 * @param angle
	 * @return necessary wheel rotations
	 */
	public static int convertAngle(double width, double angle) {
		return convertDistance(Math.PI * width * angle / 360.0);
	}
}