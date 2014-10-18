package orientation;

import navigation.Navigator;
import lejos.nxt.LCD;
import constants.Constants;

public abstract class Orienter {
	DefaultOrienteer defaultOrienteer;
	Navigator navigator;
	
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
