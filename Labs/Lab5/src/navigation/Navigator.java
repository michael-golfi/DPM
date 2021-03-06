package navigation;

import odometry.Odometer;
import utils.Vector;
import utils.VectorOperations;
import controller.MotorController;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Navigator extends Thread implements Navigation {
	public Vector currentDestination;
	private Vector currentPosition = new Vector(0, 0);

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
	 * @return the last angle measured by the odometer
	 */
	private double getOldTheta() {
		return odometer.getTheta();
	}

	@Override
	public boolean isNavigating() {
		return motorController.isNavigating();
	}

	@Override
	public void run() {
		travelTo(new Vector(60, 30));
		travelTo(new Vector(30, 30));
		travelTo(new Vector(30, 60));
		travelTo(new Vector(60, 0));
	}

	public void setLocation(Vector v){
		currentDestination = v;
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

	/**
	 * Travel to a location without blocking the calling thread
	 * 
	 * @param vector
	 */
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
}