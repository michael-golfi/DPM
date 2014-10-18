package math;

import constants.MotorConstants;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class LengthConverter {
	/**
	 * Conversion factor to convert from wheel rotations to distance travelled
	 */
	private static final double ROTATIONS_CONVERSION = (Math.PI
			* MotorConstants.RADIUS / 180.0);

	/**
	 * Converts robot rotation to wheel rotation
	 * 
	 * @param radius
	 * @param width
	 * @param angle
	 * @return
	 */
	public static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}

	/**
	 * Converts distance to wheel rotations
	 * 
	 * @param radius
	 * @param distance
	 * @return
	 */
	public static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

	/**
	 * Convert Wheel rotations to distances travelled
	 * 
	 * @param rotations
	 * @return
	 */
	public static double convertRotationsToDistance(double rotations) {
		return rotations * ROTATIONS_CONVERSION;
	}
}