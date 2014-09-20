package odometer;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.ColorSensor.Color;
import lejos.util.Delay;
import abstracts.AbstractOdometryCorrection;
import constants.OdometerConstants;

@SuppressWarnings("unused")
public class OdometryCorrection extends AbstractOdometryCorrection {
	private Odometer odometer;
	private static final long CORRECTION = 10;
	private static int lastColor, currentColor, counter = 0;
	private static double startingX, startingY, x, y, theta;
	private static final double TILE_LENGTH = 30.48;
	private ColorSensor colorSensor = new ColorSensor(SensorPort.S1);

	long correctionStart, correctionEnd;

	public OdometryCorrection(Odometer odometer) {
		this.odometer = odometer;
		colorSensor.setFloodlight(Color.YELLOW);
	}

	private static final int BLACK = 400;

	@Override
	public void updateOdometer() {
		correctionStart = System.currentTimeMillis();

		currentColor = colorSensor.getNormalizedLightValue();

		if (lastColor - currentColor > 10) {
			counter++;
			Sound.beep();
			
			theta = odometer.getTheta();
			odometer.setX(counter * TILE_LENGTH * Math.cos(theta));
			odometer.setY(counter * TILE_LENGTH * Math.sin(theta));
		}

		correctionEnd = System.currentTimeMillis();
		if (correctionEnd - correctionStart < OdometerConstants.CORRECTION_PERIOD)
			Delay.msDelay(OdometerConstants.CORRECTION_PERIOD
					- (correctionEnd - correctionStart));

		lastColor = currentColor;
	}
}