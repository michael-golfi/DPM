package abstracts;

public abstract class AbstractOdometryCorrection extends Thread {
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
