package odometry;

import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;
import constants.MotorConstants;
import constants.OdometerConstants;
import controller.MotorController;

/**
 * A simple implementation of an odometer that records motor rotations and
 * computes angle of rotation values.
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 *
 */
public class Odometer extends AbstractOdometer {
	private double x2, y2, x1, y1, distanceX, distanceY, deltaD, deltaTheta;
	private NXTRegulatedMotor[] motors;
	private Object lock = new Object();
	private long updateStart, updateEnd;

	public Odometer(MotorController motorController) {
		x1 = x2 = y1 = y2 = distanceX = distanceY = deltaD = deltaTheta = 0.0;
		this.motors = motorController.getMotors();
	}

	@Override
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
	 * Calculate the distance travelled by the left motor.
	 */
	public void calculateX() {
		x2 = motors[0].getTachoCount();
		distanceX = Math.PI * MotorConstants.WHEEL_RADIUS * (x2 - x1) / 180.0;
		x1 = x2;
	}

	/**
	 * Calculate the distance travelled by the right motor.
	 */
	public void calculateY() {
		y2 = motors[1].getTachoCount();
		distanceY = Math.PI * MotorConstants.WHEEL_RADIUS * (y2 - y1) / 180.0;
		y1 = y2;
	}

	/**
	 * Calculate the theta turn angle
	 */
	public void calculateTheta() {
		deltaD = (distanceX + distanceY) / 2;
		deltaTheta = (distanceX - distanceY) / MotorConstants.WIDTH;
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