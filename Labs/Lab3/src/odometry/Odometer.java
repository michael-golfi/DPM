package odometry;

import lejos.util.Delay;
import math.LengthConverter;
import constants.MotorConstants;
import constants.OdometerConstants;
import controller.MotorController;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Odometer extends AbstractOdometer {
	private Object lock = new Object();
	private MotorController motorController;
	long updateStart, updateEnd;
	private double x2, y2, x1, y1, distanceX, distanceY, deltaD, deltaTheta;

	/**
	 * An odometer to measure distance travelled and keep track of location in a
	 * grid
	 * 
	 * @param motorController
	 */
	public Odometer(MotorController motorController) {
		x1 = x2 = y1 = y2 = distanceX = distanceY = deltaD = deltaTheta = 0.0;
		this.motorController = motorController;
	}

	/**
	 * Calculate the angle theta for the movement
	 */
	private void calculateTheta() {
		deltaD = (distanceX + distanceY) / 2;
		deltaTheta = (distanceY - distanceX) / MotorConstants.WIDTH;
	}

	/**
	 * Calculate the X component for the movement
	 */
	private void calculateX() {
		x2 = motorController.getXTachometer();
		distanceX = LengthConverter.convertRotationsToDistance(x2 - x1);
		x1 = x2;
	}

	/**
	 * Calculate the Y component for the movement
	 */
	private void calculateY() {
		y2 = motorController.getYTachometer();
		distanceY = LengthConverter.convertRotationsToDistance(y2 - y1);
		y1 = y2;
	}

	/**
	 * Update the odometer with current tachometry values
	 */
	public void updateOdometer() {
		updateStart = System.currentTimeMillis();

		calculateX();
		calculateY();
		calculateTheta();

		synchronized (lock) {
			theta += deltaTheta;
			x += deltaD * Math.sin(theta);
			y += deltaD * Math.cos(theta);
		}
		waitForPeriodEnd();
	}

	/**
	 * Waits until the end of the period.
	 */
	private void waitForPeriodEnd() {
		updateEnd = System.currentTimeMillis();
		if (updateEnd - updateStart < OdometerConstants.ODOMETER_PERIOD)
			Delay.msDelay(OdometerConstants.ODOMETER_PERIOD
					- (updateEnd - updateStart));
	}
}