package main;

import navigation.Navigator;
import controller.MotorController;
import odometry.Odometer;
import lejos.nxt.Button;
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
		Odometer odometer = new Odometer(motorController);
		Navigator navigator = new Navigator(motorController, odometer);
		odometer.start();
		
		navigator.travelTo(60,30);
		navigator.travelTo(30,30);
		
		Button.waitForAnyPress();
		
		RConsole.close();
		
	}

}
