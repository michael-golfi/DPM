package math;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class VectorOperations {
	private static final double CONVERT_TO_DEGREES = 180 / Math.PI;

	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
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
	 * Subtract v1 by v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector subtract(Vector v1, Vector v2) {
		return new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
	}
}
