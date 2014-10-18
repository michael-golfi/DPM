package odometer;

import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;
import abstracts.AbstractOdometer;
import constants.MotorConstants;
import constants.OdometerConstants;

public class Odometer extends AbstractOdometer {
	private double x2, y2, x1, y1, distanceX, distanceY, deltaD, deltaTheta;	
	private NXTRegulatedMotor[] motors;
	private Object lock = new Object();
	long updateStart, updateEnd;
	
	public Odometer(NXTRegulatedMotor[] motors) {		
		x1 = x2 = y1 = y2 = distanceX = distanceY = deltaD = deltaTheta = 0.0;
		this.motors = motors;
		motors[0].resetTachoCount();
		motors[1].resetTachoCount();
	}

	public void updateOdometer() {
		updateStart = System.currentTimeMillis();
		
		calculateX();
		calculateY();
		calculateTheta();
		
		synchronized (lock) {		
			theta += deltaTheta;
			x += deltaD * Math.sin(theta);
			y += deltaD * Math.cos(theta);
		}	
		waitForPeriodEnd();
	}
	
	public void calculateX(){
		x2 = motors[0].getTachoCount();
		distanceX = Math.PI * MotorConstants.LEFT_RADIUS * (x2 - x1) / 180.0;
		x1 = x2;
	}
	
	public void calculateY(){
		y2 = motors[1].getTachoCount();
		distanceY = Math.PI * MotorConstants.RIGHT_RADIUS * (y2 - y1) / 180.0;
		y1 = y2;
	}
	
	public void calculateTheta(){
		deltaD = (distanceX + distanceY) / 2;
		deltaTheta = (distanceX - distanceY) / MotorConstants.WIDTH;
	}

	/**
	 * Waits until the end of the period.
	 */
	private void waitForPeriodEnd() {
		updateEnd = System.currentTimeMillis();
		if (updateEnd - updateStart < OdometerConstants.ODOMETER_PERIOD)			
			Delay.msDelay(OdometerConstants.ODOMETER_PERIOD - (updateEnd - updateStart));		
	}
}