package odometer;

import utils.LcdScreen;
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
	/**
	 * Displacement of sensor: {@value} cm
	 */
	private static final double SENSOR_DISPLACEMENT = 4.8;
	private int lastColor, currentColor, counter = 0;
	private double x, y, theta, startingX, startingY, roundTheta;
	private static final double TILE_LENGTH = 30.48;
	private static final double HALF_TILE_LENGTH = 15.24;
	private ColorSensor colorSensor = new ColorSensor(SensorPort.S1);

	long correctionStart, correctionEnd;

	public OdometryCorrection(Odometer odometer) {
		this.odometer = odometer;
		colorSensor.setFloodlight(Color.GREEN);
	}

	@Override
	public void updateOdometer() {
		correctionStart = System.currentTimeMillis();
		currentColor = colorSensor.getNormalizedLightValue();
		theta = odometer.getTheta();

		if (lastColor - currentColor > 10) {
			counter++;
			Sound.beep();

			/*
			 * if (counter % 2 == 0) { x += (TILE_LENGTH - SENSOR_DISPLACEMENT)
			 * * Math.sin(theta); y += (TILE_LENGTH - SENSOR_DISPLACEMENT) *
			 * Math.cos(theta); }
			 */

			LcdScreen.displayCount(counter);
			setByHardCoded(counter);
			//odometer.setX(x);
			//odometer.setY(y);

			try {
				sleep(500);
			} catch (InterruptedException ex) {
			}
		}

		lastColor = currentColor;
		correctionEnd = System.currentTimeMillis();
		if (correctionEnd - correctionStart < OdometerConstants.CORRECTION_PERIOD) {
			try {
				sleep(OdometerConstants.CORRECTION_PERIOD
						- (correctionEnd - correctionStart));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private double round(double theta) {
		if (theta >= 85 && theta <= 95)
			return 90.0;
		else if (theta >= 175 && theta <= 185)
			return 180.0;
		else if (theta >= 265 && theta <= 275)
			return 270.0;
		else
			return 0.0;
	}

	private void setByHardCoded(int counter) {
		switch (counter) {
		case 1:
			odometer.setX(0);
			odometer.setY(15.24 - SENSOR_DISPLACEMENT);
			break;
		case 2:
			odometer.setY(45.72 - SENSOR_DISPLACEMENT);
			break;
		case 3:
			odometer.setX(15.24 - SENSOR_DISPLACEMENT);
			break;
		case 4:
			odometer.setX(45.72 - SENSOR_DISPLACEMENT);
			break;
		case 5:
			odometer.setY(45.72 + SENSOR_DISPLACEMENT);
			break;
		case 6:
			odometer.setY(15.24 + SENSOR_DISPLACEMENT);
			break;
		case 7:
			odometer.setX(45.72 + SENSOR_DISPLACEMENT);
			break;
		case 8:
			odometer.setX(15.24 + SENSOR_DISPLACEMENT);
			break;
		default:
			break;
		}
	}
}