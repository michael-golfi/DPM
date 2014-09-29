package controller;

import utils.UnitConverter;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import constants.MotorConstants;

public class MotorController {
	private NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;

	public MotorController() {
		leftMotor.setAcceleration(750);
		rightMotor.setAcceleration(750);
	}

	/**
	 * Rotate robot by degrees
	 * 
	 * @param degrees
	 */
	public void rotate(double degrees) {
		int angle = UnitConverter.convertAngle(MotorConstants.RADIUS,
				MotorConstants.WIDTH, degrees);
		leftMotor.rotate(-angle, true);
		rightMotor.rotate(angle, false);
	}

	/**
	 * Go straight a certain distance
	 * 
	 * @param distance
	 */
	public void travel(double distance) {
		int converted = UnitConverter.convertDistance(MotorConstants.RADIUS,
				distance);
		leftMotor.rotate(converted, true);
		rightMotor.rotate(converted, false);
	}

	public NXTRegulatedMotor[] getMotors() {
		return new NXTRegulatedMotor[] { leftMotor, rightMotor };
	}
	
	public void stop() {
		leftMotor.stop();
		rightMotor.stop();
	}
}