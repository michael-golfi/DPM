package controllers;

import constants.ControllerConstants;
import constants.MotorConstants;

public class PController extends AbstractController {
	private int error, highLimit = MotorConstants.PTYPE_MOTOR_HIGH,
			lowLimit = -highLimit;
	private int filter;

	@Override
	public void processNewDistance() {
		int distance = ultrasonicSensor.getDistance();		
		int correction = calculateCorrection(distance);
		if (error < -ControllerConstants.BAND_WIDTH) {
			filter++;
			
			// Turn left if the robot doesn't see anything for 31 cycles
			if (filter > ControllerConstants.FILTER_OUT){				
				motorController.setLeftMotorSpeed(300 + correction);
				motorController.setRightMotorSpeed(300 - correction);
			}
		} else if (error > ControllerConstants.BAND_WIDTH) {
			filter = 0;
			motorController.setLeftMotorSpeed(300 + correction);
			motorController.setRightMotorSpeed(300 - correction);
		} else {
			filter = 0;
			motorController.start();
		}
	}

	
	/**
	 * Do the PID calc for a single iteration.
	 * 
	 * @param distance
	 *            The distance value from the ultrasonic sensor
	 * @return The Correction Variable to input into the motors
	 */
	public int calculateCorrection(int distance) {
		error = ControllerConstants.OFFSET - distance;		

		int correction = (ControllerConstants.SCALING * error);

		// Ensure correction doesn't exceed set motor limits
		correction = (correction > highLimit) ? highLimit : correction;
		correction = (correction < lowLimit) ? lowLimit : correction;

		return correction;
	}
}