package utils;

/**
 * Provide a simple implementation to perform common vector operations.
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074 
 *         
 */
public class VectorOperations {
	/**
	 * Radian to Degree conversion factor: 180 degrees / Pi radians
	 */
	private static final double CONVERT_TO_DEGREES = 180 / Math.PI;

	/**
	 * Adds the two vectors by vector addition.
	 * 
	 * @return the resultant of the addition
	 */
	public static Vector add(Vector one, Vector two) {
		return new Vector(one.getX() + two.getX(), one.getY() + two.getY());
	}

	/**
	 * Get the length of the vector with components x, y.
	 * 
	 * @param x
	 * @param y
	 * @return vector length
	 */
	public static double normalize(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Get the angle of the vector with components x, y.
	 * 
	 * @param x
	 * @param y
	 * @return angle of the vector in degrees
	 */
	public static double angle(double x, double y) {
		return Math.atan2(y, x) * CONVERT_TO_DEGREES;
	}

	/**
	 * Subtract v1 by v2
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static Vector subtract(Vector one, Vector two) {
		return new Vector(one.getX() - two.getX(), one.getY() - two.getY());
	}
}
