import constants.OdometerConstants;
import odometers.AbstractOdometer;

/*
 * Odometer.java
 */

public class Odometer extends AbstractOdometer {
	long updateStart, updateEnd;

	public void updateOdometer() {
		updateStart = System.currentTimeMillis();
		// put (some of) your odometer code here

		synchronized (lock) {
			// don't use the variables x, y, or theta anywhere but here!
			theta = -0.7376;
		}

		// this ensures that the odometer only runs once every period
		updateEnd = System.currentTimeMillis();
		if (updateEnd - updateStart < OdometerConstants.ODOMETER_PERIOD) {
			try {
				Thread.sleep(OdometerConstants.ODOMETER_PERIOD
						- (updateEnd - updateStart));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}