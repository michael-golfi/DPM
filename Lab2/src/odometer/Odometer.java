package odometer;

import abstracts.AbstractOdometer;
import constants.OdometerConstants;

public class Odometer extends AbstractOdometer {
	private Object lock = new Object();
	long updateStart, updateEnd;
	
	public Odometer(){
		x = y = theta = 0.0;
	}
	
	public void updateOdometer(){
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
				Thread.sleep(OdometerConstants.ODOMETER_PERIOD - (updateEnd - updateStart));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}