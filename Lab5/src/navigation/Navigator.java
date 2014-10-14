package navigation;

import odometry.Odometer;
import utils.Vector;
import utils.VectorOperations;
import controller.MotorController;

/**
 * 
 * A navigator that travels to specific points in the grid according to its
 * starting location.
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class Navigator extends Thread implements Navigation {
	public Vector currentDestination;
	public Vector currentPosition = new Vector(0, 0);

	private MotorController motorController;
	private Odometer odometer;

	/**
	 * A navigator that goes to specific points in the x,y plane
	 * 
	 * @param motorController
	 * @param ultrasonicSensor
	 * @param odometer
	 */
	public Navigator(MotorController motorController, Odometer odometer) {
		this.motorController = motorController;
		this.odometer = odometer;
	}

	/**
	 * Gets the last odometer angle reading.
	 * 
	 * @return the last angle measured by the odometer
	 */
	private double getOldTheta() {
		return odometer.getThetaDegrees();
	}

	@Override
	public boolean isNavigating() {
		return motorController.isNavigating();
	}

	/**
	 * Travel by given distance
	 * 
	 * @param distance
	 */
	public void travelDistance(double distance) {
		motorController.travel(distance);
	}

	@Override
	public void travelTo(double x, double y) {
		currentDestination = new Vector(x, y);
		currentPosition = new Vector(odometer.getY(), odometer.getX());
		travelTo(currentDestination);
	}

	/**
	 * Travel to a location and block the calling thread
	 * 
	 * @param vector
	 */
	private void travelTo(Vector vector) {
		Vector difference = VectorOperations.subtract(vector, currentPosition);
		turnTo(difference.getAngle() - getOldTheta());
		motorController.travel(difference.getLength());
		currentPosition = new Vector(odometer.getY(), odometer.getX());
	}

	@Override
	public void turnTo(double theta) {
		motorController.rotate(theta);
	}

	/**
	 * Stops the robot
	 */
	public void stop() {
		motorController.stop();
	}
}