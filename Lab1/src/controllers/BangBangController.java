package controllers;

import constants.ControllerConstants;

public class BangBangController extends AbstractController {
	private int filter = 0;

	@Override
	public void processNewDistance() {
		int distance = ultrasonicSensor.getDistance();
		adjust(distance);
	}

	/**
	 * Chooses adjustmust based on distance.
	 * 
	 * @param distance
	 */
	private void adjust(int distance) {
		int error = ControllerConstants.BAND_CENTER - distance;
		int bandWidth = ControllerConstants.BAND_WIDTH;

		if (error < -bandWidth) {
			filter++;
			
			// Turn left if the robot doesn't see anything for 31 cycles
			if (filter > ControllerConstants.FILTER_OUT)
				motorController.turnLeft();
		} else if (error > bandWidth) {
			filter = 0;
			motorController.inplaceRight();
		} else {
			filter = 0;
			motorController.start();
		}
	}
}
