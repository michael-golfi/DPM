package odometer;
import abstracts.AbstractOdometryCorrection;
import constants.OdometerConstants;

@SuppressWarnings("unused")
public class OdometryCorrection extends AbstractOdometryCorrection {
	private Odometer odometer;
	long correctionStart, correctionEnd;
	public OdometryCorrection(Odometer odometer) {
		this.odometer = odometer;
	}

	@Override
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
				e.printStackTrace();
			}
		}
	}
}