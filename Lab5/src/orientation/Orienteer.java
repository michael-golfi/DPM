package orientation;

import java.util.ArrayList;
import java.util.Random;

import lejos.nxt.LCD;
import navigation.Navigator;
import utils.Vector;
import constants.Constants;
import controller.UltrasonicController;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Orienteer extends Thread {
	private UltrasonicController ultrasonicController;
	private Navigator navigator;
	private OrientationType type;
	private Map map = new Map();
	private int movementX = 0, movementY = 0;

	public Orienteer(UltrasonicController ultrasonicController,
			Navigator navigator, OrientationType type) {
		this.ultrasonicController = ultrasonicController;
		this.navigator = navigator;
		this.type = type;
	}

	public void run() {
		chooseOrientationMethod(type);
	}

	/**
	 * Choose the type of orientation technique to use.
	 * 
	 * @param type
	 */
	private void chooseOrientationMethod(OrientationType type) {
		if (type == OrientationType.DETERMINISTIC)
			deterministicOrientation();
		else
			stochasticOrientation();
	}

	/**
	 * Use a deterministic orientation approach
	 */
	private void deterministicOrientation() {
		int counter = 0;

		ArrayList<Vector> positions = map.getPossibleStartingPositions();
		Orientation orientation = Orientation.NORTH;
		
		while (positions.size() > 1) {
			counter++;

			int tilesAhead = ultrasonicController.getFilteredData() / 30;
			
			// Do some filtering logic

			if (ultrasonicController.getDistance() < Constants.TILE_LENGTH) {
				navigator.turnTo(Constants.LEFT);
				orientation = orientation.getNextOrientation();
			} else {
				navigator.travelDistance(Constants.TILE_LENGTH);
				adjustMovement(orientation);
			}
		}

		// Knows its position
		LCD.drawString("Counter: " + counter, 0, 4);
	}

	/**
	 * Use a stochastic orientation approach
	 */
	private void stochasticOrientation() {
		Random random = new Random();
		int counter = 0;

		ArrayList<Vector> positions = map.getPossibleStartingPositions();
		Orientation orientation = Orientation.NORTH;
		while (positions.size() > 1) {
			counter++;

			if (ultrasonicController.getDistance() < Constants.TILE_LENGTH
					|| random.nextBoolean()) {
				navigator.turnTo(Constants.LEFT);
				orientation = orientation.getNextOrientation();
			} else {
				navigator.travelDistance(Constants.TILE_LENGTH);
				adjustMovement(orientation);
			}
		}

		LCD.drawString("Counter: " + counter, 0, 4);
	}

	/**
	 * Adjusts movement based on orientation and assumption of one tile movement
	 * 
	 * @param orientation
	 */
	private void adjustMovement(Orientation orientation) {
		switch (orientation) {
		case NORTH:
			movementY++;
			break;
		case EAST:
			movementX++;
			break;
		case SOUTH:
			movementY--;
			break;
		case WEST:
			movementX--;
			break;
		}
	}
}
