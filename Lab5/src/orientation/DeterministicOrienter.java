package orientation;

import lejos.nxt.LCD;
import navigation.Navigator;
import constants.Constants;
import constants.Lab5Map;
import controller.UltrasonicController;

public class DeterministicOrienter extends Thread {
	private static DefaultOrienteer defaultOrienteer;
	private static Field field;
	private static UltrasonicController ultrasonicController;
	private static Navigator navigator;

	public DeterministicOrienter(UltrasonicController ultrasonicController,
			Navigator navigator) {
		field = new Field(Lab5Map.map);
		defaultOrienteer = new DefaultOrienteer(field);
	}

	public void run() {
		orient();
	}

	public void orient() {
		int counter = 0;
		int tilesAhead = ultrasonicController.getFilteredData() / 30;
		defaultOrienteer.observeLineOfSight(tilesAhead);

		while (defaultOrienteer.isPositionAmbiguous()) {
			counter++;

			if (ultrasonicController.isBlocked())
				turnLeft();
			else
				goForward();
		}

		LCD.drawString("Counter: " + counter, 0, 4);
		
		Position startingPosition = defaultOrienteer.getStartingPosition();
		Position currentPosition = defaultOrienteer.getCurrentPosition();
		drawPosition(startingPosition, currentPosition);
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
	public void drawPosition(Position startingPosition, Position currentPosition) {
		LCD.drawString("Starting Position" + startingPosition, 0, 1);
		LCD.drawString("Current Position" + currentPosition, 0, 2);
	}

}
