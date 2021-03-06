package controller;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import utils.LengthConverter;
import constants.MotorConstants;

/**
 * Provides abstraction layer for motors. A common interface to perform simple
 * tasks on each motor. Simplifies left, right turns and travelling distances.
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class MotorController {
	private NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B,
			sensorMotor = Motor.C;

	/**
	 * A controller to provide common motor functionality
	 */
	public MotorController() {
		resetTachometers();
	}

	/**
	 * Gets a reference to both motors
	 * 
	 * @return NXTRegulatedMotor[] for both wheel motors
	 */
	public NXTRegulatedMotor[] getMotors() {
		return new NXTRegulatedMotor[] { leftMotor, rightMotor };
	}

	/**
	 * Gets the tachometer value for the left motor
	 * 
	 * @return rotations from left motor
	 */
	public int getXTachometer() {
		return leftMotor.getTachoCount();
	}

	/**
	 * Gets the tachometer value for the right motor
	 * 
	 * @return rotations from right motor
	 */
	public int getYTachometer() {
		return rightMotor.getTachoCount();
	}

	/**
	 * Checks whether the robot is currently moving
	 * 
	 * @return true if either motor is currently rotating
	 */
	public boolean isNavigating() {
		return leftMotor.isMoving() || rightMotor.isMoving();
	}

	/**
	 * Set motors' tachometer counts to 0
	 * 
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
		int angle = LengthConverter.convertAngle(MotorConstants.WIDTH, theta);
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
	 * Rotate sensor
	 * 
	 * @param angle
	 *            to rotate to
	 */
	public void rotateSensor(int angle) {
		sensorMotor.rotateTo(angle);
	}

	/**
	 * Go straight a certain distance
	 * 
	 * @param distance
	 */
	public void travel(double distance) {
		travel(distance, true, false);
	}

	/**
	 * Travel a given distance, if waitLeft/waitRight are false then the thread
	 * will wait for this action to complete
	 * 
	 * @param distance
	 * @param waitLeft
	 * @param waitRight
	 */
	public void travel(double distance, boolean waitLeft, boolean waitRight) {
		int converted = LengthConverter.convertDistance(distance);
		leftMotor.rotate(converted, waitLeft);
		rightMotor.rotate(converted, waitRight);
	}
}