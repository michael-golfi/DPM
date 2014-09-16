package odometer;

import lejos.nxt.NXTRegulatedMotor;
import abstracts.AbstractOdometer;
import constants.OdometerConstants;

public class Odometer extends AbstractOdometer {
	// private double LEFT_RADIUS, RIGHT_RADIUS;
	// private double dx, dy, dt;
	// private long lastTimeInMillis;
	private double x2, y2;
	private static final double WHEEL_BASE = 15.5;
	private static final double WHEEL_RADIUS = 2.16;
	private NXTRegulatedMotor[] motors;
	private Object lock = new Object();
	long updateStart, updateEnd;

	public Odometer(NXTRegulatedMotor[] motors) {
		x = y = theta = 0.0;
		this.motors = motors;
	}

	public void updateOdometer() {
		updateStart = System.currentTimeMillis();
		x2 = motors[0].getTachoCount();
		y2 = motors[1].getTachoCount();

		synchronized (lock) {
			x = Math.PI * WHEEL_RADIUS * (x2 - x) / 180.0;
			y = Math.PI * WHEEL_RADIUS * (y2 - y) / 180.0;
			theta += (x - y) / WHEEL_BASE;
		}
		waitForPeriodEnd();
	}

	/**
	 * Waits until the end of the period.
	 */
	private void waitForPeriodEnd() {
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