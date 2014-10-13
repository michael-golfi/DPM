package lcd;

import lejos.nxt.LCD;
import lejos.util.TimerListener;
import odometry.Odometer;

public class LCDInfo implements TimerListener {
	private Odometer odometer;
	private double[] position = new double[3];

	/**
	 * Draws odometer position on screen
	 * 
	 * @param odo
	 */
	public LCDInfo(Odometer odo) {
		this.odometer = odo;
	}

	/**
	 * Periodically draw position on NXT screen
	 */
	public void timedOut() {
		position = odometer.getPosition();
		LCD.drawString("X: " + position[0], 0, 0);
		LCD.drawString("Y: " + position[1], 0, 1);
		LCD.drawString("H: " + Math.toDegrees(position[2]), 0, 2);
	}
}
