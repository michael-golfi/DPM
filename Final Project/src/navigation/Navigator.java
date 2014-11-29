package navigation;

import lejos.nxt.comm.RConsole;
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
	
	private final int ROTATE_SPEED = 150;
	private final int FORWARD_SPEED = 250;
	
	private final float THETA_TOLERANCE = 3.0f;
	private final float DISTANCE_TOLERANCE = 1.0f;

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

	/**
	 * Travel by given distance
	 * 
	 * @param distance
	 */
	public void travelDistance(double distance) {
		motorController.travel(distance);
	}
	
	/**
	 * Travel backwards by given distance
	 * @param distance
	 */
	public void travelBackwards(double distance){
		motorController.travel(-distance);
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
		RConsole.println("Current Destination: " + vector);
		RConsole.println("Current Position: " + currentPosition);
		
		Vector difference = VectorOperations.subtract(vector, currentPosition);
		
		RConsole.println("Difference Angle: " + difference.getAngle());
		RConsole.println("Old Angle: " + getOldTheta());
		
		turnTo(difference.getAngle() - getOldTheta());
		motorController.travel(difference.getLength());
		
		double x = odometer.getX();
		double y = odometer.getY();
		
		RConsole.println("Odometer x,y" + x +", " + y);
		currentPosition = new Vector(y, x);
	}
	
	public void travelTo2(double x, double y){
		// USE THE FUNCTIONS setForwardSpeed and setRotationalSpeed from TwoWheeledRobot!
		//loop until robot is within distance tolerance of the desired destination
				
		double minimalAngle;
		
		while(Math.abs(odometer.getX() - x) > DISTANCE_TOLERANCE || Math.abs(odometer.getY() - y) > DISTANCE_TOLERANCE){

			//calculate min angle
			minimalAngle = (Math.atan2(x - odometer.getX(), y - odometer.getY())) * (180.0 / Math.PI);

			//if(minimalAngle < -0.0000005)
			if(minimalAngle < 0)
				minimalAngle+=360;

			//continue to destination if we are not currently following an obstacle
			turnTo(minimalAngle);		
			//odometer.getTwoWheeledRobot().setForwardSpeed(FORWARD_SPEED);
			motorController.setSpeeds(FORWARD_SPEED, FORWARD_SPEED);			
		}


		motorController.setSpeeds(0, 0);
		//odometer.getTwoWheeledRobot().setForwardSpeed(0);
		//odometer.getTwoWheeledRobot().setRotationSpeed(0);

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
	
	public void turnTo2(double theta){
		//double offset = theta - (odometer.getTheta() < -1 ? odometer.getTheta() + 360 : odometer.getTheta());
		
		double offset = theta - odometer.getTheta();
		
		while(Math.abs(offset) > THETA_TOLERANCE){

			//update offset
			//offset = theta - (odometer.getTheta() < - 1? odometer.getTheta() + 360 : odometer.getTheta());
			offset = theta - odometer.getTheta();
			
			//odometer.getTwoWheeledRobot().setForwardSpeed(0);
			motorController.setSpeeds(0, 0);
			
			//turn motors based on offset
			if(offset < -180){
				//odometer.getTwoWheeledRobot().setRotationSpeed(ROTATE_SPEED);
				motorController.setSpeeds(ROTATE_SPEED, -ROTATE_SPEED);
			}else if(offset < 0){
				//odometer.getTwoWheeledRobot().setRotationSpeed(-ROTATE_SPEED);
				motorController.setSpeeds(-ROTATE_SPEED, ROTATE_SPEED);
			}else if(offset > 180){
				//odometer.getTwoWheeledRobot().setRotationSpeed(-ROTATE_SPEED);
				motorController.setSpeeds(-ROTATE_SPEED, ROTATE_SPEED);
			}else{
				//odometer.getTwoWheeledRobot().setRotationSpeed(ROTATE_SPEED);
				motorController.setSpeeds(ROTATE_SPEED, -ROTATE_SPEED);
			}
		}
		//odometer.getTwoWheeledRobot().setRotationSpeed(0);
		motorController.setSpeeds(0, 0);

	}
}