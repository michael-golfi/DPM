package controllers;

import constants.ControllerConstants;

public class BangBangController extends AbstractController {

	@Override
	public void processNewDistance() {
		int distance = ultrasonicController.getDistance();
		chooseTurn(distance);						
	}
	
	private void chooseTurn(int distance) {
		int error = ControllerConstants.BAND_CENTER - distance;
		int bandWidth = ControllerConstants.BAND_WIDTH;

		if (error < -bandWidth) {
			motorController.inplaceLeft();
		} else if (error > bandWidth) {
			motorController.inplaceRight();
		} else {
			motorController.start();
		}
	}
}
