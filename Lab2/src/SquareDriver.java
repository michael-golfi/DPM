import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;
import main.UnitConverter;
import constants.MotorConstants;

public class SquareDriver {

	public static void drive(NXTRegulatedMotor leftMotor,
			NXTRegulatedMotor rightMotor, double leftRadius,
			double rightRadius, double width) {		
		accelerate(new NXTRegulatedMotor[] { leftMotor, rightMotor });
		Delay.msDelay(2000);

		for (int i = 0; i < 4; i++) {
			leftMotor.setSpeed(MotorConstants.FORWARD_SPEED);
			rightMotor.setSpeed(MotorConstants.FORWARD_SPEED);

			rotate(leftMotor, rightMotor, leftRadius, rightRadius);

			leftMotor.setSpeed(MotorConstants.ROTATE_SPEED);
			rightMotor.setSpeed(MotorConstants.ROTATE_SPEED);

			leftMotor.rotate(UnitConverter.convertAngle(leftRadius, width, 90.0), true);
			rightMotor.rotate(-UnitConverter.convertAngle(rightRadius, width, 90.0),false);
		}
	}

	private static void accelerate(NXTRegulatedMotor[] motors) {
		for (NXTRegulatedMotor motor : motors) {
			motor.stop();
			motor.setAcceleration(1000);
		}
	}

	private static void rotate(NXTRegulatedMotor leftMotor,
			NXTRegulatedMotor rightMotor, double leftRadius, double rightRadius) {
		int converted = UnitConverter.convertDistance(leftRadius, 60.96);
		leftMotor.rotate(converted, true);
		converted = UnitConverter.convertDistance(rightRadius, 60.96);
		rightMotor.rotate(converted, false);
	}
}