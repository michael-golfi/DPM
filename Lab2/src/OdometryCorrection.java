import constants.OdometerConstants;
import odometers.AbstractOdometer;

/* 
 * OdometryCorrection.java
 */

public class OdometryCorrection extends AbstractOdometer {
	long correctionStart, correctionEnd;

	public void updateOdometer() {
		correctionStart = System.currentTimeMillis();

		// put your correction code here

		// this ensure the odometry correction occurs only once every period
		correctionEnd = System.currentTimeMillis();
		if (correctionEnd - correctionStart < OdometerConstants.CORRECTION_PERIOD) {
			try {
				Thread.sleep(OdometerConstants.CORRECTION_PERIOD
						- (correctionEnd - correctionStart));
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not
				// expected that the odometry correction will be
				// interrupted by another thread
			}
		}
	}
}