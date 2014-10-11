package utils;

public class Vector {
	private int x, y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(double x, double y){
		this.x = (int)x;
		this.y = (int)y;
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
