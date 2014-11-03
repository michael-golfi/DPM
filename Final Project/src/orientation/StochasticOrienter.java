package orientation;

import java.util.ArrayList;
import java.util.Random;

import navigation.Navigator;
import odometry.Odometer;
import utils.Vector;
import constants.Constants;
import constants.Map;
import controller.UltrasonicController;

/**
 * 
 * An orienter that finds its location using the stochastic method
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class StochasticOrienter extends Thread
{
	private DefaultOrienteer		defaultOrienteer;
	private Field					field;
	private UltrasonicController	ultrasonicController;
	private Navigator				navigator;
	private Odometer				odometer;

	public StochasticOrienter(UltrasonicController ultrasonicController,
			Navigator navigator, Odometer odometer)
	{
		field = new Field(Map.map);
		defaultOrienteer = new DefaultOrienteer(field);
		this.ultrasonicController = ultrasonicController;
		this.navigator = navigator;
		this.odometer = odometer;
	}

	public void run() {
		orient();
	}

	/**
	 * Start discovering starting and current position using deterministic
	 * method
	 */
	public void orient() {
		Random random = new Random();
		int tilesAhead = ultrasonicController.getFilteredData() / 30;
		defaultOrienteer.observeLineOfSight(tilesAhead);

		while (defaultOrienteer.isPositionAmbiguous())
		{
			if (ultrasonicController.isBlocked() || random.nextBoolean())
				turnLeft();
			else
				goForward();
		}

		Position currentPosition = defaultOrienteer.getCurrentPosition();
		setOdometer(currentPosition);
		navigate(currentPosition);
	}

	/**
	 * Set Odometer to current position
	 * 
	 * @param currentPosition
	 */
	private void setOdometer(Position currentPosition) {
		odometer.setX(currentPosition.xTile * 30 - 15);
		odometer.setY(currentPosition.yTile * 30 - 15);

		int direction = currentPosition.direction.getAngle();
		navigator.turnTo(-direction);
		odometer.setTheta(0);
	}

	/**
	 * Navigate to final location
	 * 
	 * @param currentPosition
	 */
	private void navigate(Position currentPosition) {
		ArrayList<Vector> path = PathFinder.getPath(Map.mapTranspose,
				currentPosition.xTile, currentPosition.yTile);

		for (Vector position : path)
			navigator.travelTo(position.getX() * Constants.TILE_LENGTH - 15,
					position.getY() * Constants.TILE_LENGTH - 15);
	}

	/**
	 * Turn left 90 degrees
	 */
	public void turnLeft() {
		defaultOrienteer.turnLeft();
		navigator.turnTo(Constants.LEFT);
	}

	/**
	 * Go forward one tile
	 */
	public void goForward() {
		navigator.travelDistance(Constants.TILE_LENGTH);
		defaultOrienteer.advance();
	}
}
