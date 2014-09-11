package controllers;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import Interfaces.MotorControl;
import constants.MotorConstants;

public class MotorController implements MotorControl{
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	private final NXTRegulatedMotor ultrasonicSensorMotor = Motor.C;
	
	public void start() {
		start(leftMotor);
		start(rightMotor);
	}

	public void stop() {
		stop(leftMotor);
		stop(rightMotor);
	}

	public void reverse() {
		reverse(leftMotor);
		reverse(rightMotor);
	}

	public void inplaceRight() {
		start(leftMotor);
		reverse(rightMotor);
	}

	public void inplaceLeft() {
		start(leftMotor);
		reverse(rightMotor);
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

	@Override
	public void setLeftMotorSpeed(int radians) {
		leftMotor.setSpeed(radians);
		if (radians < 0)
			leftMotor.backward();
		else
			leftMotor.forward();
	}

	@Override
	public void setRightMotorSpeed(int radians) {
		rightMotor.setSpeed(radians);
		if (radians < 0)
			rightMotor.backward();
		else
			rightMotor.forward();
	}
	
	@Override
	public void rotateSensor(int angle){
		ultrasonicSensorMotor.resetTachoCount();
		ultrasonicSensorMotor.rotate(angle, true);		
	}
}
