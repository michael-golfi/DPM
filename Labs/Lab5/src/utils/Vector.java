package utils;

import javax.microedition.location.Orientation;

/**
 * A representation of a vector with common attributes: x, y, and common
 * functionality: computing angle and vector length.
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class Vector {
	private int x, y;

	public Vector(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}

	public Vector(int x, int y, Orientation orientation) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Compute the angle of the vector
	 * 
	 * @return angle
	 */
	public double getAngle() {
		return VectorOperations.angle(x, y);
	}

	/**
	 * Compute the length of the vector
	 * 
	 * @return length
	 */
	public double getLength() {
		return VectorOperations.normalize(x, y);
	}

	/**
	 * Get the x component of the vector
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y component of the vector
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	@Override
	/**
	 * String representation of x and y components of the vector
	 */
	public String toString() {
		return "x: " + x + " y: " + y;
	}
}
