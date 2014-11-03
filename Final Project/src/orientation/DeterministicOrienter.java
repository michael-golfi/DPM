package orientation;

import java.util.ArrayList;

import lejos.nxt.LCD;
import navigation.Navigator;
import odometry.Odometer;
import utils.Vector;
import constants.Constants;
import constants.Map;
import controller.UltrasonicController;

/**
 * 
 * An orienter that finds its location using the deterministic method
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 *
 */
public class DeterministicOrienter extends Thread {
	private DefaultOrienteer defaultOrienteer;
	private Field field;
	private UltrasonicController ultrasonicController;
	private Odometer odometer;
	private Navigator navigator;

	public DeterministicOrienter(UltrasonicController ultrasonicController,
			Navigator navigator, Odometer odometer) {
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
		int counter = 0;
		int tilesAhead = ultrasonicController.getTilesAhead();
		defaultOrienteer.observeLineOfSight(tilesAhead);

		while (defaultOrienteer.isPositionAmbiguous()) {
			counter++;

			tilesAhead = ultrasonicController.getTilesAhead();
			defaultOrienteer.observeLineOfSight(tilesAhead);

			LCD.drawString("Counter: " + counter, 0, 0);
			LCD.drawString("Tiles " + tilesAhead, 0, 1);

			if (ultrasonicController.isBlocked())
				turnLeft();
			else
				goForward();
		}

		Position startingPosition = defaultOrienteer.getStartingPosition();
		Position currentPosition = defaultOrienteer.getCurrentPosition();
		drawPositionToScreen(startingPosition, currentPosition);

		setOdometer(currentPosition);

		// Should navigate here, but it doesn't navigate due to an error with
		// the navigator
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
		navigator.turnTo(90);
	}

	/**
	 * Turn left 90 degrees
	 */
	public void turnRight() {
		defaultOrienteer.turnRight();
		navigator.turnTo(-90);
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
	public void drawPositionToScreen(Position startingPosition,
			Position currentPosition) {
		LCD.drawString("Star " + startingPosition, 0, 1);
		LCD.drawString("Curr " + currentPosition, 0, 2);
	}

}
