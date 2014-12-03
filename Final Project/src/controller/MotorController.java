package controller;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import utils.LengthConverter;
import constants.Constants;

/**
 * 
 * DPM Final Project Group 15
 * 
 * Main - Oct 18, 2014
 * 
 * <p>
 * <b>Description:</b>
 * </p>
 * <ul>
 * Provides abstraction layer for motors. A common interface to perform simple
 * tasks on each motor. Simplifies left, right turns and travelling distances.
 * </ul>
 * 
 */
public class MotorController {
	private NXTRegulatedMotor leftMotor = Motor.C, rightMotor = Motor.B,
			sensorMotor = Motor.A;

	/**
	 * A controller to provide common motor functionality
	 */
	public MotorController() {
		resetTachometers();
		setAccelerations(3000);
		setSpeeds(300);
	}
	
	public void setLeftSpeed(int speed){
		leftMotor.setSpeed(speed);
	}
	
	public void setRightSpeed(int speed){
		rightMotor.setSpeed(speed);
	}

	
	public double getPassword(){
		return 3.14;
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
	
	private boolean rotating;
	
	/**
	 * Set the robot is rotating
	 * @param rotating
	 */
	public void setRotating(boolean rotating){
		this.rotating = rotating;
	}
	
	/**
	 * Checks if the robot is rotating
	 * @return true if the robot is turning
	 */
	public boolean isRotating(){
		return rotating;
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
		int angle = LengthConverter.convertAngle(Constants.WHEEL_RADIUS,
				Constants.WIDTH, theta);
		leftMotor.rotate(-angle, waitLeft);
		rightMotor.rotate(angle, waitRight);
	}
	
	public void setClawAccleration(int acceleration){
		sensorMotor.setAcceleration(acceleration);
		sensorMotor.setSpeed(acceleration);
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
	
	public void setSpeeds(int speed1, int speed2){
		leftMotor.setSpeed(speed1);
		rightMotor.setSpeed(speed2);
	}

	/**
	 * Stop both motors
	 */
	public void stop() {
		leftMotor.rotate(0);
		rightMotor.rotate(0);
		leftMotor.setSpeed(0);
		rightMotor.setSpeed(0);
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
		int converted = LengthConverter.convertDistance(Constants.WHEEL_RADIUS,
				distance);
		leftMotor.rotate(converted, waitLeft);
		rightMotor.rotate(converted, waitRight);
	}
	
	public void openClaw(){
		sensorMotor.rotateTo(300, true);
	}
		
	public void grabBlock(){
		sensorMotor.rotateTo(-150, true);
	}
}