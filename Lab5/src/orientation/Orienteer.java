package orientation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import utils.Vector;

import lejos.nxt.LCD;

import navigation.Navigator;
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

	private int leftDistance, rightDistance, frontDistance, backDistance;

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
		
		while (positions.size() > 1) {
			counter++;
			if (ultrasonicController.getFrontDistance() < Constants.TILE_LENGTH) {
				navigator.turnTo(Constants.LEFT);
			}
			else{
				navigator.travelDistance(Constants.TILE_LENGTH);
			}
		}
		
		LCD.drawString("Counter: " + counter, 0, 4);
	}

	/**
	 * Use a stochastic orientation approach
	 */
	private void stochasticOrientation() {
		Random random = new Random();
	}
}
