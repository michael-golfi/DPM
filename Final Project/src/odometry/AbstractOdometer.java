package odometry;

import lejos.nxt.comm.RConsole;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public abstract class AbstractOdometer extends Thread {
	protected Object lock = new Object();
	protected double x = 0.0, y = 0.0, theta = 0.0;

	/**
	 * Gets the position of x, y, and theta
	 */
	public double[] getPosition(boolean[] update) {
		double[] position = new double[3];
		synchronized (lock) {
			if (update[0])
				position[0] = x;
			if (update[1])
				position[1] = y;
			if (update[2])
				position[2] = theta / (2 * 3.141592) * 360;
		}
		return position;
	}

	/**
	 * Gets the rotation
	 * 
	 * @return
	 */
	public double getTheta() {
		return theta % 360;
	}

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
	 * Updates the odometer in a separate thread
	 */
	public void run() {
		while (true) {
			updateOdometer();
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
	 * Sets the rotation
	 * 
	 * @param theta
	 */
	public void setTheta(double theta) {
		synchronized (lock) {
			this.theta = theta;
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
	 * Updates the odometer with current values
	 */
	public abstract void updateOdometer();
}
