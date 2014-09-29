package odometry;

import utils.LcdScreen;
import lejos.nxt.LCD;
import lejos.util.Delay;


public class OdometryDisplay extends AbstractOdometryDisplay {
	private static final long DISPLAY_PERIOD = 250;
	private Odometer odometer;
	long displayStart, displayEnd;
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
