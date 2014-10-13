package localizers;

import navigation.Navigator;
import odometry.Odometer;

public class RisingEdge {
	public static double findAlpha(
			UltrasonicLocalizer ultrasonicLocalizer, Navigator navigator,
			Odometer odometer) {
		ultrasonicLocalizer.turnToWall(-360);
		ultrasonicLocalizer.turnFromWall(-360);
		return odometer.getThetaDegrees();
	}

	public static double findBeta(
			UltrasonicLocalizer ultrasonicLocalizer, Navigator navigator,
			Odometer odometer) {
		navigator.turnTo(25);
		ultrasonicLocalizer.turnToWall(360);
		return odometer.getThetaDegrees();
	}
	
	public static double getAngle(double alpha, double beta) {
		return ((alpha < beta) ? 135 : 315) - (alpha + beta) / 2;
	}
}
