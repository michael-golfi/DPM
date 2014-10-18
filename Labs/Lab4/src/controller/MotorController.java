package controller;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import utils.LengthConverter;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class MotorController {
	private NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;

	/**
	 * A controller to provide common motor functionality
	 */
	public MotorController() {
		//setAccelerations(3000);
		setSpeeds(150);
		resetTachometers();
	}

	public NXTRegulatedMotor[] getMotors() {
		return new NXTRegulatedMotor[] { leftMotor, rightMotor };
	}

	/**
	 * 
	 * @return rotations from left motor
	 */
	public int getXTachometer() {
		return leftMotor.getTachoCount();
	}

	/**
	 * 
	 * @return rotations from right motor
	 */
	public int getYTachometer() {
		return rightMotor.getTachoCount();
	}

	/**
	 * @return true if either motor is currently rotating
	 */
	public boolean isNavigating() {
		return leftMotor.isMoving() || rightMotor.isMoving();
	}

	/**
	 * Reset robot motor tachometers
	 */
	public void resetTachometers() {
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
	}

	/**
	 * Rotate robot by an angle theta
	 * 
	 * @param degrees
	 */
	public void rotate(double degrees) {
		rotate(degrees, true, false);
	}

	/**
	 * Rotate the robot by an angle theta. Wait left and right will make the
	 * right and left motors wait for rotation to finish before continuing
	 * 
	 * @param theta
	 * @param waitLeft
	 * @param waitRight
	 */
	public void rotate(double theta, boolean waitLeft, boolean waitRight) {
		int angle = LengthConverter.convertAngle(constants.MotorConstants.WIDTH, theta);
		leftMotor.rotate(-angle, waitLeft);
		rightMotor.rotate(angle, waitRight);
	}

	/**
	 * Set the maximum wheel accelerations
	 * 
	 * @param acceleration
	 */
	public void setAccelerations(int acceleration) {
		leftMotor.setAcceleration(acceleration);
		rightMotor.setAcceleration(acceleration);
	}
	
	/**
	 * Set the maximum wheel speeds
	 * 
	 * @param speed
	 */
	public void setSpeeds(int speed) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}

	/**
	 * Stop both motors
	 */
	public void stop() {
		leftMotor.rotate(0);
		rightMotor.rotate(0);
		leftMotor.stop();
		rightMotor.stop();
	}

	/**
	 * Go straight a certain distance
	 * 
	 * @param distance
	 */
	public void travel(double distance) {
		travel(distance, true, false);
	}

	public void travel(double distance, boolean waitLeft, boolean waitRight) {
		int converted = LengthConverter.convertDistance(distance);
		leftMotor.rotate(converted, waitLeft);
		rightMotor.rotate(converted, waitRight);
	}
}