package main;

import navigation.Navigator;
import controller.ColourSensorController;
import controller.MotorController;
import odometry.Odometer;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;

/**
 * 
 * DPM Final Project Group 15
 * 
 * Main - Oct 18, 2014
 * 
 * <p><b>Description:</b></p> <ul> The aim of this project is to program a lego
 * mindstorms NXT to navigate a grid maze, find blocks and retrieve them </ul>
 * 
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		RConsole.open();
		
		RConsole.println("Bluetooth working!");
		
		
		MotorController motorController = new MotorController();
		
		System.out.println(motorController.getPassword());
		
		
		Odometer odometer = new Odometer(motorController);
		Navigator navigator = new Navigator(motorController, odometer);
		odometer.start();
		
		
		ColourSensorController colourSensorController = new ColourSensorController(SensorPort.S1);
		colourSensorController.start();
		
		Motor.A.rotate(100);
		
		Motor.A.setAcceleration(500);
		
		while(colourSensorController.getLightValue() < 500){
			RConsole.println("" + colourSensorController.getLightValue());
			navigator.travelDistance(1);
		}	
		
		Motor.A.rotate(-250);
		
		navigator.turnTo(180);
		
		//navigator.travelTo(45, 30);
		
		Motor.A.rotate(250);
		
		navigator.travelDistance(-20);
		
		
		navigator.turnTo(360);
		
		
		
		//navigator.travelTo(60,30);
		//navigator.travelTo(30,30);
		
		Button.waitForAnyPress();
		
		RConsole.close();
		
	}

}
