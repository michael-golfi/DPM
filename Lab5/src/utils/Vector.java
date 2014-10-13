package utils;

import orientation.Orientation;

public class Vector {
	private Orientation orientation;
	private int x, y;

	public Vector(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}

	public Vector(int x, int y, Orientation orientation) {
		this.x = x;
		this.y = y;
		this.setOrientation(orientation);
	}

	public double getAngle() {
		return VectorOperations.angle(x, y);
	}

	public double getLength() {
		return VectorOperations.normalize(x, y);
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "x: " + x + " y: " + y + " dir: " + orientation;
	}
}
