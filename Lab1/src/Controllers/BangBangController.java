package Controllers;

import constants.ControllerConstants;
import constants.SensorConstants;
import lejos.nxt.UltrasonicSensor;
import main.MotorController;
import main.UltrasonicController;

public class BangBangController extends AbstractController {
	private UltrasonicController ultrasonicController;
	private MotorController motorController;

	public BangBangController() {
		UltrasonicSensor sensor = new UltrasonicSensor(
				SensorConstants.ULTRASONIC_PORT);
		this.ultrasonicController = new UltrasonicController(sensor);
		this.motorController = new MotorController();
	}

	@Override
	public void processNewDistance() {
		int distance = ultrasonicController.getDistance();
		chooseTurn(distance);
	}

	private void chooseTurn(int distance) {
		int error = ControllerConstants.BAND_CENTER - distance;
		int bandWidth = ControllerConstants.BAND_WIDTH;

		if (error < bandWidth) {
			motorController.inplaceLeft();
		} else if (error > bandWidth) {
			motorController.inplaceRight();
		} else {
			motorController.start();
		}
	}
}
