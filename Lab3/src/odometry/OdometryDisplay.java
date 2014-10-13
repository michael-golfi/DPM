package odometry;

import lejos.nxt.LCD;
import lejos.util.Delay;
import utils.LcdScreen;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class OdometryDisplay extends AbstractOdometryDisplay {
	private static final long DISPLAY_PERIOD = 250;
	long displayStart, displayEnd;
	private Odometer odometer;
	double[] position = new double[3];

	public OdometryDisplay(Odometer odometer) {
		this.odometer = odometer;
		LCD.clearDisplay();
	}

	@Override
	public void updateOdometer() {
		displayStart = System.currentTimeMillis();

		position = odometer.getPosition(new boolean[] { true, true, true });
		LcdScreen.displayOdometerInformation(position);

		displayEnd = System.currentTimeMillis();
		if (displayEnd - displayStart < DISPLAY_PERIOD)
			Delay.msDelay(DISPLAY_PERIOD - (displayEnd - displayStart));
	}
}
