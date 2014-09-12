package controllers;

import constants.ControllerConstants;

/**
 * 
 * @author michael
 * 
 * P Controller adapted from this source: 
 * http://www.inpharmix.com/jps/PID_Controller_For_Lego_Mindstorms_Robots.html
 */
public class PController extends AbstractController {
			
	private int filter = 0;
	@Override
	public void processNewDistance() {
		int distance = ultrasonicController.getDistance();
		int bandWidth = ControllerConstants.BAND_WIDTH;
		//int error = distance - ControllerConstants.OFFSET;
		int error = ControllerConstants.BAND_CENTER - distance;
		//int turnRadians = (ControllerConstants.KP * error);
		
		if (error < -bandWidth) {
			filter++;
			if (filter > ControllerConstants.FILTER_OUT)
				motorController.turnLeft(error);
		} else if (error > bandWidth) {
			filter = 0;
			motorController.turnRight(error);
		} else {
			filter = 0;
			motorController.start();
		}
	}	
}
