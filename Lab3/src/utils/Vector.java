package utils;

public class Vector {
	private static final double CONVERT_TO_DEGREES = 180 / Math.PI;
	private int x, y;

	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Vector add(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
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
	 * @return
	 */
	public static double angle(double x, double y) {
		double angle = Math.atan2(y, x) * CONVERT_TO_DEGREES;
		System.out.println("Arctan: " + y + " / " + x + " = " + angle);
		return angle; 
	}
	
	public double getAngle(){
		return angle(x, y);
	}
	
	public double getLength(){
		return normalize(x, y);
	}

	/**
	 * Subtract v1 by v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector subtract(Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}
	
	@Override
	public String toString(){
		return "x: " + x + " y: " + y;
	}
}
