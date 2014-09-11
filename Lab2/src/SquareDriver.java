import java.util.stream;
import constants.MotorConstants;
import utils.UnitConverter;
import lejos.nxt.*;

public class SquareDriver {

	public static void drive(NXTRegulatedMotor leftMotor,
			NXTRegulatedMotor rightMotor, double leftRadius,
			double rightRadius, double width) {
		NXTRegulatedMotor[] motors = new NXTRegulatedMotor[]{leftMotor, rightMotor};
		
		accelerate(new NXTRegulatedMotor[] { leftMotor, rightMotor });

		sleep();
		
		for (int i = 0; i < 4; i++) {
			leftMotor.setSpeed(MotorConstants.FORWARD_SPEED);
			rightMotor.setSpeed(MotorConstants.FORWARD_SPEED);

			rotate(leftMotor, rightMotor, leftRadius, rightRadius);
			
			leftMotor.setSpeed(MotorConstants.ROTATE_SPEED);
			rightMotor.setSpeed(MotorConstants.ROTATE_SPEED);

			leftMotor.rotate(
					UnitConverter.convertAngle(leftRadius, width, 90.0), true);
			rightMotor.rotate(
					-UnitConverter.convertAngle(rightRadius, width, 90.0),
					false);
		}
	}

	private static void accelerate(NXTRegulatedMotor[] motors) {
		for (NXTRegulatedMotor motor : motors) {
			motor.stop();
			motor.setAcceleration(3000);
		}
	}

	private static void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void rotate(NXTRegulatedMotor leftMotor,
			NXTRegulatedMotor rightMotor, double leftRadius, double rightRadius) {
		int converted = UnitConverter.convertDistance(leftRadius, 60.96);
		leftMotor.rotate(converted, true);
		converted = UnitConverter.convertDistance(rightRadius, 60.96);
		rightMotor.rotate(converted, false);
	}
	
	private static void 
}