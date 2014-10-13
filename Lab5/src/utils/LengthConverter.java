package utils;

import constants.MotorConstants;

public class LengthConverter {
	/**
	 * Converts distance to wheel rotations
	 * @param radius
	 * @param distance
	 * @return
	 */
	public static int convertDistance(double distance) {
		return (int) ((180.0 * distance) / (Math.PI * MotorConstants.WHEEL_RADIUS));
	}

	/**
	 * Converts robot rotation to wheel rotation
	 * @param radius
	 * @param width
	 * @param angle
	 * @return
	 */
	public static int convertAngle(double width, double angle) {
		return convertDistance(Math.PI * width * angle / 360.0);
	}
}