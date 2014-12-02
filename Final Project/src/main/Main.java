package main;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;
import navigation.DistanceNavigator;
import odometry.Correction;
import odometry.Odometer;
import utils.Vector;
import controller.MotorController;

/**
 * 
 * DPM Final Project Group 15
 * 
 * Main - Oct 18, 2014
 * 
 * <p>
 * <b>Description:</b>
 * </p>
 * <ul>
 * The aim of this project is to program a lego mindstorms NXT to navigate a
 * grid maze, find blocks and retrieve them
 * </ul>
 * 
 */
public class Main {
	public static void main(String[] args) {
		RConsole.open();
	
		testCorrection();
		/*FiniteStateMachine fsm = new FiniteStateMachine(); 
		fsm.start();
		*/
		Button.waitForAnyPress();
		RConsole.close();
	}

	public static void getMapAndDropOffLocation(){
		int map = 0, x = 0, y = 0, buttonPressed;
		
		while(true){			
			LCD.drawString("Choose a map", 0, 0);
			buttonPressed = Button.waitForAnyPress();			
			if (buttonPressed == Button.ID_RIGHT)
				map++;
			else if (buttonPressed == Button.ID_LEFT)
				map--;
			else if (buttonPressed == Button.ID_ENTER)
				break;
			LCD.drawString("Map Number: ", 0, 1);
		}
		
		while(true){
			LCD.drawString("Choose X", 0, 0);
			buttonPressed = Button.waitForAnyPress();			
			if (buttonPressed == Button.ID_RIGHT)
				x++;
			else if (buttonPressed == Button.ID_LEFT)
				x--;
			else if (buttonPressed == Button.ID_ENTER)
				break;
			LCD.drawString("X: " + x + " Y: " + 0, 0, 1);
		}
		
		while(true){
			LCD.drawString("Choose Y", 0, 0);
			buttonPressed = Button.waitForAnyPress();			
			if (buttonPressed == Button.ID_RIGHT)
				y++;
			else if (buttonPressed == Button.ID_LEFT)
				y--;
			else if (buttonPressed == Button.ID_ENTER)
				break;
			LCD.drawString("X: " + x + " Y: " + y, 0, 1);
		}
	}
	
	public static void testTurnAngles() {
		MotorController motorController = new MotorController();
		
		Delay.msDelay(2000);
		Odometer odometer = new Odometer(motorController);
		DistanceNavigator distanceNavigator = new DistanceNavigator(odometer,
				motorController);
		distanceNavigator.turnTo(90);
		double theta = Math.toDegrees(odometer.getTheta());
		RConsole.println("Theta: " + theta);
	}

	public static void testCorrection() {
		ColorSensor[] sensors = new ColorSensor[]{ new ColorSensor(SensorPort.S2, Color.RED), 
			new ColorSensor(SensorPort.S3, Color.RED)};
		
		MotorController motorController = new MotorController();
		Odometer odometer = new Odometer(motorController);
		odometer.start();
		Correction odometerCorrection = new Correction(
				odometer, motorController, sensors);
		odometerCorrection.start();

		synchronized (odometer) {
			odometer.setX(-15.0);
			odometer.setY(-15.0);
			odometer.setTheta(Math.toRadians(90.0));
		}

		DistanceNavigator distanceNavigator = new DistanceNavigator(odometer,
				motorController);
		distanceNavigator.travelTo(new Vector(-15, 15)); // up
		distanceNavigator.travelTo(new Vector(15, 15));  // right
		distanceNavigator.travelTo(new Vector(45, 15));
		distanceNavigator.travelTo(new Vector(75, 15));
		distanceNavigator.travelTo(new Vector(75, 45));  // up
		distanceNavigator.travelTo(new Vector(75, 75));
		distanceNavigator.travelTo(new Vector(75, 105));
		distanceNavigator.travelTo(new Vector(45, 105)); // left
		distanceNavigator.travelTo(new Vector(15, 105));
		distanceNavigator.travelTo(new Vector(-15, 105));
		
	}
}
