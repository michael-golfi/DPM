package main;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.RConsole;
import blockdetection.BlockDetector;
import blockdetection.BlockListener;
import constants.Constants;
import controller.MotorController;
import controller.UltrasonicController;
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
	public static void main(String[] args) {		
		RConsole.open();
		RConsole.println("Start");
		
		FiniteStateMachine fsm = new FiniteStateMachine();
		fsm.start();
		
		/*UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		while(true){
			RConsole.println("" + sensor.getDistance());
			try{
				Thread.sleep(500);
			}catch(Exception e){}
		}*/
		
		/*ColorSensor colorSensor = new ColorSensor(SensorPort.S1);
		RConsole.println("Started ColorSensor");
		BlockDetector blockDetector = new BlockDetector(colorSensor);
		MotorController motorController = new MotorController();
		
		blockDetector.setBlockListener(new BlockListener() {
			
			@Override
			public void onBlockDetected(int color) {
				motorController.stop();
				motorController.openClaw();
				motorController.travel(20);
				motorController.grabBlock();
				
				blockDetector.setListenForBlock(false);
			}
		});
		
		RConsole.println("Started BlockDetector");
		blockDetector.start();
		
		motorController.travel(70);
		*/
		Button.waitForAnyPress();
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
