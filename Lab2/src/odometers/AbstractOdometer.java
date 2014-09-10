package odometers;

public abstract class AbstractOdometer extends Thread {
	protected double x, y, theta;
	protected Object lock = new Object();

	/**
	 * Updates the odometer in a separate thread
	 */
	public void run() {
		while (true) {
			updateOdometer();
		}
	}

	/**
	 * Updates the odometer with current values
	 */
	public abstract void updateOdometer();

	/**
	 * Gets the X position
	 * 
	 * @return
	 */
	public double getX() {
		double result;
		synchronized (lock) {
			result = x;
		}
		return result;
	}

	/**
	 * Gets the Y position
	 * 
	 * @return
	 */
	public double getY() {
		double result;
		synchronized (lock) {
			result = y;
		}
		return result;
	}

	/**
	 * Gets the rotation
	 * 
	 * @return
	 */
	public double getTheta() {
		return theta;
	}

	/**
	 * Gets the position of x, y, and theta
	 */
	public void getPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				position[0] = x;
			if (update[1])
				position[1] = y;
			if (update[2])
				position[2] = theta / (2 * 3.141592) * 360;
		}
	}

	/**
	 * Sets the position of x, y, and theta
	 * 
	 * @param position
	 * @param update
	 */
	public void setPosition(double[] position, boolean[] update) {
		synchronized (lock) {
			x = (update[0]) ? position[0] : x;
			y = (update[1]) ? position[1] : y;
			theta = (update[2]) ? position[2] : theta;
		}
	}

	/**
	 * Sets the X position
	 * 
	 * @param x
	 */
	public void setX(double x) {
		synchronized (lock) {
			this.x = x;
		}
	}

	/**
	 * Sets the Y position
	 * 
	 * @param y
	 */
	public void setY(double y) {
		synchronized (lock) {
			this.y = y;
		}
	}

	/**
	 * Sets the rotation
	 * 
	 * @param theta
	 */
	public void setTheta(double theta) {
		synchronized (lock) {
			this.theta = theta;
		}
	}
}
