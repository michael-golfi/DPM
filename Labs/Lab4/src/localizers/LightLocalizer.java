package localizers;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.Sound;
import navigation.Navigator;
import odometry.Odometer;

public class LightLocalizer {
	private int lastColor;
	private Odometer odometer;
	private Navigator navigator;
	private ColorSensor colorSensor;

	public LightLocalizer(Odometer odometer, ColorSensor colorSensor,
			Navigator navigator) {
		this.odometer = odometer;
		this.colorSensor = colorSensor;
		this.navigator = navigator;
		colorSensor.setFloodlight(Color.RED);
	}

	public void doLocalization() {
		navigator.turnWithoutWait(-360);
		sleep();
		int counter = 0;
		double[] lineAngles = new double[4];
		while (navigator.isNavigating()) {
			if (isBlackLine()) {
				lineAngles[counter] = odometer.getTheta();
				counter++;
				Sound.beep();
				sleep();
			}
		}
		navigator.stop();
		correctXY(lineAngles);
	}

	private int currentColor;
	boolean isBlackLine;

	/**
	 * Checks for black line
	 * 
	 * @return true if there's a black line
	 */
	private boolean isBlackLine() {
		currentColor = colorSensor.getNormalizedLightValue();
		isBlackLine = lastColor - currentColor > 15;
		lastColor = currentColor;
		return isBlackLine;
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static final int SENSOR_DISTANCE = -12;

	private void correctXY(double[] lineAngles) {
		double x = SENSOR_DISTANCE
				* Math.cos((lineAngles[3] - lineAngles[1]) / 2);
		double y = SENSOR_DISTANCE
				* Math.cos((lineAngles[2] - lineAngles[0]) / 2);
		odometer.setX(x);
		odometer.setY(y);
		navigator.travelToOrigin(x, y, 90);
		odometer.setPosition(new double[] { 0.0, 0.0, 0.0 });
	}
}
