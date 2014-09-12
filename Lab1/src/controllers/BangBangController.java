package controllers;

import constants.ControllerConstants;

public class BangBangController extends AbstractController {

	@Override
	public void processNewDistance() {
		int distance = ultrasonicController.getDistance();
		chooseTurn(distance);
	}

	private int filter = 0;

	/**
	 * Chooses to turn based on distance.
	 * 
	 * @param distance
	 */
	private void chooseTurn(int distance) {
		int error = ControllerConstants.BAND_CENTER - distance;
		int bandWidth = ControllerConstants.BAND_WIDTH;

		if (error < -bandWidth) {
			filter++;
			if (filter > ControllerConstants.FILTER_OUT) {
				motorController.turnLeft();
			}
		} else if (error > bandWidth) {
			filter = 0;
			motorController.inplaceRight();
		} else {
			filter = 0;
			motorController.start();
		}
	}
}
