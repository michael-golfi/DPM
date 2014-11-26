package odometry;

import java.util.Stack;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;
import constants.Constants;
import controller.MotorController;
import orientation.*;

public class Odometer extends AbstractOdometer {
	private double x2, y2, x1, y1, distanceX, distanceY, deltaD, deltaTheta;	
	private NXTRegulatedMotor[] motors;
	private Object lock = new Object();
	long updateStart, updateEnd;
	
	public Orientation orientation;
	
	public Odometer(MotorController motorController) {		
		x1 = x2 = y1 = y2 = distanceX = distanceY = deltaD = deltaTheta = 0.0;
		this.motors = motorController.getMotors();
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
	
	public void setPosition(double x, double y, double theta){
		synchronized (lock) {
			this.x = x;
			this.y = y;
			this.theta = Math.toDegrees(theta);
		}
	}
	
	public void calculateX(){
		x2 = motors[0].getTachoCount();
		distanceX = Math.PI * Constants.WHEEL_RADIUS * (x2 - x1) / 180.0;
		x1 = x2;
	}
	
	public void calculateY(){
		y2 = motors[1].getTachoCount();
		distanceY = Math.PI * Constants.WHEEL_RADIUS * (y2 - y1) / 180.0;
		y1 = y2;
	}
	
	public void calculateTheta(){
		deltaD = (distanceX + distanceY) / 2;
		deltaTheta = (distanceY - distanceX) / Constants.WIDTH;
	}

	/**
	 * Waits until the end of the period.
	 */
	private void waitForPeriodEnd() {
		updateEnd = System.currentTimeMillis();
		if (updateEnd - updateStart < Constants.ODOMETER_PERIOD)			
			Delay.msDelay(Constants.ODOMETER_PERIOD - (updateEnd - updateStart));		
	}

	/**
	 * @param orientation2
	 * @return
	 */
	public Orientation invertOrientation(Orientation orientation2) {
		switch(orientation2){
		case NORTH : return Orientation.SOUTH;
		case EAST : return Orientation.WEST;
		case SOUTH : return Orientation.NORTH;
		case WEST : return Orientation.EAST;
		default : return null;
		}
		
	}
}