package localizers;

import lejos.nxt.UltrasonicSensor;
import navigation.Navigator;
import odometry.Odometer;

public class UltrasonicLocalizer {
	private static final int DISTANCE = 30;
	private double distance = 0.0;

	private Odometer odometer;
	private Navigator navigator;
	private UltrasonicSensor ultrasonicSensor;

	public UltrasonicLocalizer(Odometer odometer,
			UltrasonicSensor ultrasonicSensor, Navigator navigator) {
		this.odometer = odometer;
		this.ultrasonicSensor = ultrasonicSensor;
		this.navigator = navigator;
	}

	public void doLocalization(LocalizationType type) {
		if (type == LocalizationType.FALLING_EDGE)
			fallingEdgeLocalization();
		else
			risingEdgeLocalization();
	}

	private double angleA = 0.0, angleB = 0.0;

	public void fallingEdgeLocalization() {
		angleA = FallingEdge.findAlpha(this, navigator, odometer);
		angleB = FallingEdge.findBeta(this, navigator, odometer);
		double theta = FallingEdge.getAngle(angleA, angleB);
		goToZero(theta + (odometer.getThetaDegrees() % 360));
	}

	protected void turnToWall(double theta) {
		navigator.turnWithoutWait(theta);
		do {
			distance = getFilteredData();
		} while (distance > (DISTANCE));
		navigator.stop();
	}

	protected void turnFromWall(double theta) {
		navigator.turnWithoutWait(theta);
		do {
			distance = getFilteredData();
		} while (distance < (DISTANCE));
		navigator.stop();
	}

	public void risingEdgeLocalization() {
		angleA = RisingEdge.findAlpha(this, navigator, odometer);
		angleB = RisingEdge.findBeta(this, navigator, odometer);
		double theta = RisingEdge.getAngle(angleA, angleB);
		goToZero(theta + (odometer.getThetaDegrees() % 360));
	}

	private void goToZero(double theta) {
		navigator.turnTo(theta);
		navigator.travelDistance(12);
		odometer.setTheta(0);
	}

	public double getFilteredData() {
		ultrasonicSensor.ping();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
		distance = ultrasonicSensor.getDistance();
		return distance > 50 ? 50 : distance;
	}
}
