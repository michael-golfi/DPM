package main;

import odometry.Odometer;
import lejos.nxt.UltrasonicSensor;
import lejos.util.TimerListener;
import interfaces.Navigation;
import utils.Vector;
import controller.MotorController;

public class Navigator extends Thread implements Navigation, TimerListener {
	private static boolean isNavigating = false;
	private static double currentX, currentY, currentAngle = 0, angle,
			oldDistance, distance;

	private static double[] currentVector = new double[] { 0, 0 };
	// private static final int DISTANCE_THRESHOLD = 30, BLOCK_LENGTH = 40;

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
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		Vector v2 = new Vector(60,30);
		Vector v3 = new Vector(30,30);
		Vector v4 = new Vector(30,60);
		Vector v5 = new Vector(60,0);
		travelTo(v2);
		travelTo(v3);
		travelTo(v4);
		travelTo(v5);
	}

	@Override
	public void travelTo(double x, double y) {
		isNavigating = true;

		x -= currentX;
		y -= currentY;

		angle = Vector.angle(x, y);
		distance = Vector.normalize(x, y);

		turnTo(currentAngle - angle);
		motorController.travel(distance);

		isNavigating = false;

		currentX = x;
		currentY = y;
		currentAngle = angle;
		oldDistance = distance;
	}

	private Vector currentPosition = new Vector(0, 0);

	/**
	 * Travel to given vector
	 * @param vector
	 */
	public void travelTo(Vector vector) {
		Vector difference = Vector.subtract(vector, currentPosition);
		turnTo(difference.getAngle() - currentAngle);
		motorController.travel(difference.getLength());
		currentPosition = Vector.add(currentPosition, difference);
		
		currentAngle = difference.getAngle();
	}

	@Override
	public void turnTo(double theta) {
		motorController.rotate(theta);
	}

	@Override
	public boolean isNavigating() {
		return isNavigating;
	}

	@Override
	public void timedOut() {
		/*
		 * if (ultrasonicSensor.getDistance() < DISTANCE_THRESHOLD){
		 * motorController.stop();
		 * 
		 * storeLocation();
		 * 
		 * motorController.rotate(90.0); motorController.travel(BLOCK_LENGTH); }
		 */
	}

	private void storeLocation() {
		currentX = odometer.getX();
		currentY = odometer.getY();
	}
}