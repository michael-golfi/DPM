package localizers;

import navigation.Navigator;
import odometry.Odometer;

public class FallingEdge {
	public static double findAlpha(
			UltrasonicLocalizer ultrasonicLocalizer, Navigator navigator,
			Odometer odometer) {
		ultrasonicLocalizer.turnFromWall(360);
		navigator.turnTo(25);
		ultrasonicLocalizer.turnToWall(-360);
		return odometer.getThetaDegrees();
	}

	public static double findBeta(
			UltrasonicLocalizer ultrasonicLocalizer, Navigator navigator,
			Odometer odometer) {
		navigator.turnTo(90);
		ultrasonicLocalizer.turnToWall(360);
		return odometer.getThetaDegrees();
	}
	
	public static double getAngle(double alpha, double beta) {
		return ((alpha > beta) ? 45 : 225) - (alpha + beta) / 2;
	}
}
