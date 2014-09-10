package main;

import Interfaces.UltrasonicControl;
import lejos.nxt.UltrasonicSensor;

public class UltrasonicController implements UltrasonicControl {
	UltrasonicSensor ultrasonicSensor;

	public UltrasonicController(UltrasonicSensor sensor) {
		ultrasonicSensor = sensor;
	}

	public int getDistance() {
		return ultrasonicSensor.getDistance();
	}
}
