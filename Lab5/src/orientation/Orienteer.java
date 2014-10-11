package orientation;

import lejos.nxt.LCD;
import lejos.util.Delay;
import controller.MotorController;
import controller.UltrasonicController;

public class Orienteer extends Thread {
	private UltrasonicController ultrasonicController;

	private int leftDistance, rightDistance, frontDistance, backDistance;

	public Orienteer(UltrasonicController ultrasonicController, MotorController motorController) {
		this.ultrasonicController = ultrasonicController;
	}

	public void run() {
		updateDistances();
		
		LCD.drawString("Distances", 0, 0);
		LCD.drawString("Tiles Front " + getNumberTiles(frontDistance), 0, 1);
		LCD.drawString("Tiles Left " + getNumberTiles(leftDistance), 0, 2);
		LCD.drawString("Tiles Right " + getNumberTiles(rightDistance), 0, 3);
		LCD.drawString("Tiles Back " + getNumberTiles(backDistance), 0, 4);
	}
	
	/**
	 * Get all the distances around the robot
	 */
	private void updateDistances(){
		frontDistance = ultrasonicController.getFrontDistance();
		Delay.msDelay(1000);
		leftDistance = ultrasonicController.getLeftDistance();
		Delay.msDelay(1000);
		rightDistance = ultrasonicController.getRightDistance();
		Delay.msDelay(1000);
		backDistance = ultrasonicController.getBackwardsDistance();
		Delay.msDelay(1000);
	}
	
	private int getNumberTiles(int distance){
		return distance % 30;
	}
}
