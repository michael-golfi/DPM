package odometer;

import java.awt.Color;

import org.w3c.dom.css.Counter;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import abstracts.AbstractOdometryCorrection;
import constants.OdometerConstants;

@SuppressWarnings("unused")
public class OdometryCorrection extends AbstractOdometryCorrection {
	private Odometer odometer;
	private static final long CORRECTION = 10;
	private static final int BLACK = 250;
	private int counter = 0;
	private ColorSensor colorSensor = new ColorSensor(SensorPort.S1);
	long correctionStart, correctionEnd;

	public OdometryCorrection(Odometer odometer) {
		this.odometer = odometer;
	}

	@Override
	public void updateOdometer() {
		correctionStart = System.currentTimeMillis();
		
		if (colorSensor.getNormalizedLightValue() < BLACK){
			counter++;
			sleep();
		}
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
	
	private static void sleep(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}