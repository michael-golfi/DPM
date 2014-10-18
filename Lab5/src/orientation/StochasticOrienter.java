package orientation;

import java.util.ArrayList;
import java.util.Random;

import odometry.Odometer;
import utils.Vector;
import lejos.nxt.LCD;
import navigation.Navigator;
import constants.Constants;
import constants.Lab5Map;
import controller.UltrasonicController;

/**
 * 
 * An orienter that finds its location using the stochastic method
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 *
 */
public class StochasticOrienter extends Thread {
	private DefaultOrienteer defaultOrienteer;
	private Field field;
	private UltrasonicController ultrasonicController;
	private Navigator navigator;
	private Odometer odometer;

	public StochasticOrienter(UltrasonicController ultrasonicController,
			Navigator navigator, Odometer odometer) {
		field = new Field(Lab5Map.map);
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
		int counter = 0;
		Random random = new Random();
		int tilesAhead = ultrasonicController.getFilteredData() / 30;
		defaultOrienteer.observeLineOfSight(tilesAhead);

		while (defaultOrienteer.isPositionAmbiguous()) {
			counter++;

			if (ultrasonicController.isBlocked() || random.nextBoolean())
				turnLeft();
			else
				goForward();
		}

		LCD.drawString("Counter: " + counter, 0, 4);

		Position startingPosition = defaultOrienteer.getStartingPosition();
		Position currentPosition = defaultOrienteer.getCurrentPosition();
		drawPositionToScreen(startingPosition, currentPosition);
		
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
		ArrayList<Vector> path = PathFinder.getPath(Lab5Map.mapTranspose,
				currentPosition.xTile, currentPosition.yTile);

		navigator.setLocation(path.remove(0));

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

	/**
	 * Draws the current and starting position to screen
	 * 
	 * @param startingPosition
	 * @param currentPosition
	 */
	public void drawPositionToScreen(Position startingPosition, Position currentPosition) {
		LCD.drawString("Starting Position" + startingPosition, 0, 1);
		LCD.drawString("Current Position" + currentPosition, 0, 2);
	}
}
