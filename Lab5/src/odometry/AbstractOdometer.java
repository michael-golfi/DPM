package odometry;

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
	public double[] getPosition() {
		double[] position = new double[3];
		synchronized (lock) {
			position[0] = x;
			position[1] = y;
			position[2] = theta;
		}
		return position;
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
	public void setPosition(double[] position) {
		synchronized (lock) {
			x = position[0];
			y = position[1];
			theta = position[2];
		}
	}

	/**
	 * Reset the odometer position to 0
	 */
	public void resetPosition() {
		synchronized (lock) {
			x = y = theta = 0.0;
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
	
	public double getThetaDegrees(){
		double angle;
		synchronized (lock) {
			angle = Math.toDegrees(theta);
		}
		return angle % 360.0;
	}
	
	/**
	 * Updates the odometer with current values
	 */
	public abstract void updateOdometer();
}
