import lejos.nxt.LCD;
import odometers.AbstractOdometer;
import constants.OdometerConstants;

public class OdometryDisplay extends AbstractOdometer {
	long displayStart, displayEnd;
	double[] position = new double[3];

	public OdometryDisplay() {
		LCD.clearDisplay();
	}

	public void updateOdometer() {
		displayStart = System.currentTimeMillis();
		
		getPosition(position, new boolean[] { true, true, true });
		LcdScreen.displayOdometerInformation(position);

		displayEnd = System.currentTimeMillis();
		if (displayEnd - displayStart < OdometerConstants.DISPLAY_PERIOD) {
			try {
				Thread.sleep(OdometerConstants.DISPLAY_PERIOD - (displayEnd - displayStart));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
