package main;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import odometry.Odometer;
import controller.MotorController;

public class Lab3 {

	public static void main(String[] args) {
		MotorController motorController = new MotorController();
		
		LCD.drawString("Press any button to start", 0, 0);
		Button.waitForAnyPress();
		
		startNavigation(motorController);
	}
	
	public static void startNavigation(MotorController motorController){
		Odometer odometer = new Odometer(motorController);
		UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
		Navigator navigator = new Navigator(motorController, ultrasonicSensor, odometer);				
		//new Timer(100, navigator).start();
		navigator.start();
	}
}
