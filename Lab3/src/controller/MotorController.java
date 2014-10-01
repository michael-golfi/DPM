package controller;

import utils.LengthConverter;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import constants.MotorConstants;

public class MotorController implements RegulatedMotorListener {
	private NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B,
			sensorMotor = Motor.C;
	
	public boolean isLeftRotating(){
		return leftMotor.isMoving();
	}
	
	public boolean isRightRotating(){
		return rightMotor.isMoving();
	}

	public MotorController() {
		leftMotor.setAcceleration(750);
		rightMotor.setAcceleration(750);
		leftMotor.addListener(this);
		rightMotor.addListener(this);
	}

	/**
	 * Rotate robot by degrees
	 * 
	 * @param degrees
	 */
	public void rotate(double degrees){
		rotate(degrees, true, false);
	}
	
	public void rotate(double degrees, boolean waitLeft, boolean waitRight) {
		int angle = LengthConverter.convertAngle(MotorConstants.RADIUS,
				MotorConstants.WIDTH, degrees);
		leftMotor.rotate(-angle, waitLeft);
		rightMotor.rotate(angle, waitRight);
	}
	
	public void turnLeft() {
		setLeftMotorSpeed(125);
		setRightMotorSpeed(250);
	}
	
	public void setLeftMotorSpeed(int radians) {
		leftMotor.setSpeed(radians);
		/*if (radians < 0)
			leftMotor.backward();
		else*/
			leftMotor.forward();
	}

	public void setRightMotorSpeed(int radians) {
		rightMotor.setSpeed(radians);
		/*if (radians < 0)
			rightMotor.backward();
		else*/
			rightMotor.forward();
	}

	public void turnRight() {
		leftMotor.setSpeed(MotorConstants.MOTOR_LOW);
		rightMotor.setSpeed(MotorConstants.MOTOR_HIGH);
	}

	/**
	 * Go straight a certain distance
	 * 
	 * @param distance
	 */
	public void travel(double distance){
		travel(distance, true, false);
	}
	
	public void travel(double distance, boolean waitLeft, boolean waitRight) {
		int converted = LengthConverter.convertDistance(MotorConstants.RADIUS,
				distance);
		leftMotor.rotate(converted, waitLeft);
		rightMotor.rotate(converted, waitRight);
	}

	private int currentAngle = 0;

	public void rotateSensor(int degree) {
		sensorMotor.rotate(degree - currentAngle);
		currentAngle = degree;
	}

	public NXTRegulatedMotor[] getMotors() {
		return new NXTRegulatedMotor[] { leftMotor, rightMotor };
	}

	public void stop() {
		leftMotor.rotate(0);
		rightMotor.rotate(0);
		leftMotor.stop();
		rightMotor.stop();
	}
	
	public void setSpeed(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}
	
	public void start() {
		start(leftMotor);
		start(rightMotor);
	}
	
	/**
	 * Start the motor at default speed
	 * 
	 * @param motor
	 */
	private void start(NXTRegulatedMotor motor) {
		motor.setSpeed(MotorConstants.MOTOR_SPEED);
		motor.forward();
	}

	/**
	 * Stop the motor immediately
	 * 
	 * @param motor
	 */
	private void stop(NXTRegulatedMotor motor) {
		motor.stop();
	}
	
	/**
	 * Start the motor in reverse at default speed
	 * 
	 * @param motor
	 */
	private void reverse(NXTRegulatedMotor motor) {
		motor.setSpeed(MotorConstants.MOTOR_SPEED);
		motor.backward();
	}


	public void reverse() {
		reverse(leftMotor);
		reverse(rightMotor);
	}
	
	public void inplaceRight() {
		leftMotor.setSpeed(100);
		leftMotor.forward();
		rightMotor.setSpeed(100);
		rightMotor.backward();
	}

	public void inplaceLeft() {
		start(leftMotor);
		reverse(rightMotor);
	}

	@Override
	public void rotationStarted(RegulatedMotor motor, int tachoCount,
			boolean stalled, long timeStamp) {
	}

	@Override
	public void rotationStopped(RegulatedMotor motor, int tachoCount,
			boolean stalled, long timeStamp) {				
	}
}