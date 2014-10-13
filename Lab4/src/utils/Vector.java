package utils;

public class Vector {
	private double x, y;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

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

	@Override
	public String toString() {
		return "x: " + x + " y: " + y;
	}
}
