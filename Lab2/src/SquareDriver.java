/*
 * SquareDriver.java
 */
import utils.UnitConverter;
import lejos.nxt.*;

public class SquareDriver {
	private static final int FORWARD_SPEED = 250;
	private static final int ROTATE_SPEED = 150;

	public static void drive(NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor,
			double leftRadius, double rightRadius, double width) {
		for (NXTRegulatedMotor motor : new NXTRegulatedMotor[] { leftMotor, rightMotor }) {
			motor.stop();
			motor.setAcceleration(3000);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 4; i++) {
			leftMotor.setSpeed(FORWARD_SPEED);
			rightMotor.setSpeed(FORWARD_SPEED);
			
			int converted = UnitConverter.convertDistance(leftRadius, 60.96);
			leftMotor.rotate(converted, true);
			converted = UnitConverter.convertDistance(rightRadius, 60.96);
			rightMotor.rotate(converted, false);

			// turn 90 degrees clockwise
			leftMotor.setSpeed(ROTATE_SPEED);
			rightMotor.setSpeed(ROTATE_SPEED);

			leftMotor.rotate(UnitConverter.convertAngle(leftRadius, width, 90.0), true);
			rightMotor.rotate(-UnitConverter.convertAngle(rightRadius, width, 90.0), false);
		}
	}


}