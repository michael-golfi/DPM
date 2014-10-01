package math;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Vector {
	private double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getAngle() {
		return VectorOperations.angle(x, y);
	}

	public double getLength() {
		return VectorOperations.normalize(x, y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "x: " + x + " y: " + y;
	}
}
