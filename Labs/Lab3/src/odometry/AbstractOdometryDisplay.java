package odometry;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public abstract class AbstractOdometryDisplay extends Thread {
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
}
