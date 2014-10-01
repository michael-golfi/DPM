package main;

import interfaces.Navigation;
import lejos.nxt.UltrasonicSensor;
import odometry.Odometer;
import utils.Vector;
import utils.VectorOperations;
import controller.MotorController;

public class Navigator extends Thread implements Navigation {
	private Vector currentPosition = new Vector(0, 0);
	public Vector currentDestination;

	MotorController motorController;
	UltrasonicSensor ultrasonicSensor;
	Odometer odometer;

	public Navigator(MotorController motorController,
			UltrasonicSensor ultrasonicSensor, Odometer odometer) {
		this.motorController = motorController;
		this.ultrasonicSensor = ultrasonicSensor;
		this.odometer = odometer;
	}

	public Navigator() {
	}

	@Override
	public void run() {
		travelTo(new Vector(60, 30));
		travelTo(new Vector(30, 30));
		travelTo(new Vector(30, 60));
		travelTo(new Vector(60, 0));
	}

	@Override
	public void travelTo(double x, double y) {
		currentDestination = new Vector(x, y);
		travelTo(currentDestination);
	}

	/**
	 * Travel to given vector
	 * 
	 * @param vector
	 */
	public void travelTo(Vector vector) {
		Vector difference = VectorOperations.subtract(vector, currentPosition);
		turnTo(difference.getAngle() - getOldTheta());
		motorController.travel(difference.getLength());
		currentPosition = new Vector(odometer.getY(), odometer.getX());
	}

	public void travelWithoutWait(Vector vector) {
		Vector difference = VectorOperations.subtract(vector, currentPosition);
		motorController.rotate(difference.getAngle() - getOldTheta(), true,
				false);
		motorController.travel(difference.getLength(), true, true);
		currentPosition = new Vector(odometer.getY(), odometer.getX());
	}

	@Override
	public void turnTo(double theta) {
		motorController.rotate(theta);
	}
	
	public void travel(double distance){
		motorController.travel(distance);
	}

	@Override
	public boolean isNavigating() {
		return motorController.isLeftRotating()
				|| motorController.isRightRotating();
	}

	private double getOldTheta() {
		return odometer.getPosition(new boolean[] { true, true, true })[2];
	}

}