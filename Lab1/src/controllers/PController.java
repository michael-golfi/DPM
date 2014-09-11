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
			
	@Override
	public void processNewDistance() {
		int distance = ultrasonicController.getDistance();
		int error = distance - ControllerConstants.OFFSET;
		int turnRadians = (ControllerConstants.KP * error) / 100;
				
		motorController.setLeftMotorSpeed(ControllerConstants.TP + turnRadians);
		motorController.setRightMotorSpeed(ControllerConstants.TP - turnRadians);
	}
	
	
}
