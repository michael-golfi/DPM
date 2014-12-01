package main;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;
import navigation.DistanceNavigator;
import navigation.Navigator;
import odometry.Odometer;
import odometry.OdometerCorrection;
import test.UltrasonicTest;
import utils.Vector;
import constants.Constants;
import controller.MotorController;
import finitestatemachine.FiniteStateMachine;

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
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		RConsole.openAny(1000);
		
		MotorController motorController = new MotorController();
		Motor.C.setAcceleration(200);
		Motor.B.setAcceleration(200);
		
		Odometer odometer = new Odometer(motorController);
		DistanceNavigator distanceNavigator = new DistanceNavigator(odometer);
		OdometerCorrection odometerCorrection = new OdometerCorrection(odometer, motorController);
		odometer.start();
		
		odometerCorrection.start();
		odometer.setX(-15);
		odometer.setY(-15);
		odometer.setTheta(Math.toRadians(90));
		
		Delay.msDelay(2000);
						
		ArrayList<Vector> navigate = new ArrayList<>();
		Vector[] points = new Vector[]{
				new Vector(-15, 15),
				new Vector(15, 15),
				new Vector(45, 15),
				new Vector(45, 45),
				new Vector(45, 75),
				new Vector(15, 75),
				/*new Vector(75, 15),
				new Vector(75, -15),
				new Vector(45, -15),
				
				
				/*new Vector(75, 15),
				new Vector(105, 15),
				new Vector(130, 15),
				new Vector(165, 15),
				new Vector(165, 45),
				new Vector(165, 75),
				new Vector(165, 105),
				new Vector(165, 135),
				new Vector(165, 165)*/
		};
		navigate.addAll(points);
				
		for(Vector vector : navigate)
			distanceNavigator.travelTo(vector);
		
		RConsole.close();
	}
	
	public static void testWidth(){
		MotorController motorController = new MotorController();
		NXTConnection nxtConnection = Bluetooth.waitForConnection();
		DataInputStream dataInputStream = nxtConnection.openDataInputStream();
		while(true){
			motorController.rotate(90);
			try {
				Constants.WIDTH = dataInputStream.readDouble();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
