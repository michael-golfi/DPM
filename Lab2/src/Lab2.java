/*
 * Lab2.java
 */
import lejos.nxt.*;

public class Lab2 {
	public static void main(String[] args) {
		LcdScreen.displayMainMenu();
		
		int buttonChoice = Button.waitForAnyPress();
		chooseFunction(buttonChoice);		
		
		Button.waitForAnyPress();
		System.exit(0);
	}
	
	private static void chooseFunction(int buttonChoice){
		Odometer odometer = new Odometer();
		OdometryDisplay odometryDisplay = new OdometryDisplay();
		switch(buttonChoice){
		case Button.ID_LEFT: 
			
			for (NXTRegulatedMotor motor : new NXTRegulatedMotor[] { Motor.A, Motor.B, Motor.C }) {
				motor.forward();
				motor.flt();
			}
			odometer.start();
			odometryDisplay.start();
			
			break;
		case Button.ID_RIGHT: 
			
			odometer.start();
			odometryDisplay.start();
			// odometryCorrection.start();
			startMotor();
			
			break;
		default: break;
		}
	}
	
	private static void startMotor(){
		Thread thread = new Thread(){
			public void run(){
				SquareDriver.drive(Motor.A, Motor.B, 2.8, 2.8, 15.24);
			}
		};
		thread.start();
	}
}