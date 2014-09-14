package controllers;

import lejos.util.Delay;
import constants.ControllerConstants;

/**
 * 
 * @author michael
 * 
 *         P Controller adapted from this source:
 *         http://www.inpharmix.com/jps/PID_Controller_For_Lego_Mindstorms_Robots
 *         .html
 */
public class PController extends AbstractController {
	private int filterControl;
	@Override
	public void processNewDistance() {
		int distance = ultrasonicController.getDistance();
		//int turnRadians = doPID(distance);
		//int kp = 10;
		//int correction = kp * (ControllerConstants.BAND_CENTER - distance);
		
		int correction = doPID(distance);
		motorController.setLeftMotorSpeed(400 + correction);
		motorController.setRightMotorSpeed(400 - correction);
		
		/*if (error < -ControllerConstants.BAND_WIDTH){
			filterControl++;
			if (filterControl > ControllerConstants.FILTER_OUT){
				motorController.turnLeft(Math.abs(error));
			}
		}
		else if (error > ControllerConstants.BAND_WIDTH){
			filterControl = 0;
			motorController.turnRight(Math.abs(error));
		}
		else {
			filterControl = 0;
			motorController.start();
		}*/
		//Delay.msDelay(10);
	}

	private float Kp = 8.0f; // proportional value determines the reaction to
								// the current error
	private int highLimit = 150; // assuming control of motor speed and thereby
									// max would be 900 deg/sec
	private int lowLimit = -highLimit;
	private int deadband = 0;
	private long cycleTime = 0; // used to calc the time between each call (dt)
								// to doPID()
	private int setpoint = 40; // The setpoint to strive for
	private int error; // proportional term
	private float power = 0;
	private int rampThresold = 0;
	private double rampExtent = 1;

	/**
	 * Do the PID calc for a single iteration. Your implementation must provide
	 * the delay between calls to this method if you have not set one with
	 * <code>setDelay()</code> or in the constructor.
	 * 
	 * @param processVariable
	 *            The PV value from the process (sensor reading, etc.).
	 * @see #setDelay
	 * @return The Manipulated Variable <code>MV</code> to input into the
	 *         process (motor speed, etc.)
	 */
	public int doPID(int processVariable) {
		int outputMV;
		if (this.cycleTime == 0) {
			this.cycleTime = System.currentTimeMillis();
			return 0;
		}
		error = setpoint - processVariable;
		error = Math.abs(error) <= deadband ? 0 : error;
		outputMV = (int) (Kp * error);

		if (outputMV > highLimit)
			outputMV = highLimit;
		if (outputMV < lowLimit)
			outputMV = lowLimit;
		outputMV = rampOut(outputMV);
		this.cycleTime = System.currentTimeMillis();
		return outputMV;
	}

	private int rampOut(int ov) {
		if (power == 0 || rampThresold == 0)
			return ov;
		if (Math.abs(ov) > rampThresold)
			return ov;
		int workingOV;
		workingOV = (int) (Math.pow(Math.abs(ov), power) / rampExtent * rampThresold);
		return (ov < 0) ? -1 * workingOV : workingOV;
	}
}
