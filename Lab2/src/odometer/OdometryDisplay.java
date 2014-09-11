package odometer;

import lejos.nxt.LCD;
import utils.LcdScreen;
import abstracts.AbstractOdometryDisplay;


public class OdometryDisplay extends AbstractOdometryDisplay {
	private static final long DISPLAY_PERIOD = 250;
	private Odometer odometer;
	long displayStart, displayEnd;
	double[] position = new double[3];
	// constructor
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
		if (displayEnd - displayStart < DISPLAY_PERIOD) {
			try {
				Thread.sleep(DISPLAY_PERIOD - (displayEnd - displayStart));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
