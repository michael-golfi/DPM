package Controllers;

import lejos.nxt.UltrasonicSensor;
import main.MotorController;
import main.UltrasonicController;
import constants.ControllerConstants;
import constants.SensorConstants;

/**
 * 
 * @author michael
 * 
 * P Controller adapted from this source: 
 * http://www.inpharmix.com/jps/PID_Controller_For_Lego_Mindstorms_Robots.html
 */
public class PController extends AbstractController {
	private UltrasonicController ultrasonicController;
	private MotorController motorController;
		
	public PController() {
		UltrasonicSensor sensor = new UltrasonicSensor(SensorConstants.ULTRASONIC_PORT);
		this.ultrasonicController = new UltrasonicController(sensor);
		this.motorController = new MotorController();
	}

	@Override
	public void processNewDistance() {
		int distance = ultrasonicController.getDistance();
		int error = distance - ControllerConstants.OFFSET;
		int turnRadians = ControllerConstants.DRIFT_SPEED * error;
		
		motorController.setLeftMotorSpeed(ControllerConstants.RETURN_SPEED + turnRadians);
		motorController.setRightMotorSpeed(ControllerConstants.RETURN_SPEED - turnRadians);
	}
}
