
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import odometer.Odometer;
import odometer.OdometryCorrection;
import odometer.OdometryDisplay;
import utils.LcdScreen;
import constants.MotorConstants;
import constants.OdometerConstants;

public class Lab2 {
	public static void main(String[] args) {
		LcdScreen.displayMainMenu();

		int buttonChoice = Button.waitForAnyPress();
		chooseFunction(buttonChoice);

		Button.waitForAnyPress();
		System.exit(0);
	}

	private static void chooseFunction(int buttonChoice) {
		Odometer odometer = new Odometer(new NXTRegulatedMotor[]{Motor.A, Motor.B});
		OdometryCorrection odometryCorrection = new OdometryCorrection(odometer);
		OdometryDisplay odometryDisplay = new OdometryDisplay(odometer);
		switch (buttonChoice) {
		case Button.ID_LEFT:

			for (NXTRegulatedMotor motor : new NXTRegulatedMotor[] { Motor.A,
					Motor.B, Motor.C }) {
				motor.forward();
				motor.flt();
			}
			odometer.start();
			odometryDisplay.start();

			break;
		case Button.ID_RIGHT:
			odometer.start();
			odometryDisplay.start();
			odometryCorrection.start();
			startDriver();

			break;
		default:
			break;
		}
	}

	private static void startDriver() {
		Thread thread = new Thread() {
			public void run() {
				SquareDriver.drive(Motor.A, Motor.B,
						MotorConstants.LEFT_RADIUS,
						MotorConstants.RIGHT_RADIUS, MotorConstants.WIDTH);
			}
		};
		thread.start();
	}
}