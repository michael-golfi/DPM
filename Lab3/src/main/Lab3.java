package main;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import odometry.Odometer;
import odometry.OdometryDisplay;
import controller.MotorController;

public class Lab3 {

	public static void main(String[] args) {
		MotorController motorController = new MotorController();

		LCD.drawString("Left: Obstacle avoid", 0, 0);
		int button = Button.waitForAnyPress();

		if (button == Button.ID_LEFT){
			startNavigation(motorController);
		} else {
			startNavigationWithoutAvoidance(motorController);
		}				
	}

	public static void startNavigation(MotorController motorController) {
		Odometer odometer = new Odometer(motorController);
		OdometryDisplay odometryDisplay = new OdometryDisplay(odometer);
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);

		odometer.start();
		odometryDisplay.start();

		Navigator navigator = new Navigator(motorController, ultrasonicSensor,
				odometer);
		Avoider obstacleNavigator = new Avoider(ultrasonicSensor, navigator);
		obstacleNavigator.start();

		navigator.travelTo(0,60);
		navigator.travelTo(40, 10);
	}
	
	public static void startNavigationWithoutAvoidance(MotorController motorController){
		Odometer odometer = new Odometer(motorController);
		OdometryDisplay odometryDisplay = new OdometryDisplay(odometer);
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);

		odometer.start();
		odometryDisplay.start();

		Navigator navigator = new Navigator(motorController, ultrasonicSensor,
				odometer);
		navigator.start();
	}
}
