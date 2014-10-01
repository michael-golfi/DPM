package main;

import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import utils.Vector;
import controller.MotorController;

public class ObstacleNavigator extends Thread {// implements TimerListener {
	private Navigator navigator;
	private MotorController motorController;
	private UltrasonicSensor ultrasonicSensor;
	private int counter = 0;

	public ObstacleNavigator(Navigator navigator,
			UltrasonicSensor ultrasonicSensor, MotorController motorController) {
		this.navigator = navigator;
		this.motorController = motorController;
		this.ultrasonicSensor = ultrasonicSensor;
		motorController.setSpeed(200);
	}

	@Override
	public void run() {
		while (true) {
			if (navigator.isNavigating())
				checkForBlock();
			else
				navigate();
		}
	}

	public void checkForBlock() {
		if (ultrasonicSensor.getDistance() <= 20) {
			
			motorController.stop();
			try{
			navigator.sleep(5000);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			
			motorController.rotate(-75);
			motorController.travel(43);
			
			checkForBlock();
		}
	}

	public void navigate() {
		if (counter == 0) {
			navigator.travelWithoutWait(new Vector(0, 60));
			counter++;
		} else if (counter == 1) {
			navigator.travelWithoutWait(new Vector(60, 0));
			counter++;
		}
	}

	public void stopNavigation() {
		Motor.A.setSpeed(0);
		Motor.B.setSpeed(0);
		Motor.B.stop(true);
		Motor.A.stop(true);
	}

	public void resumeNavigation() {

	}
}
