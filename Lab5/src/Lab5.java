import lejos.nxt.Button;
import lejos.nxt.LCD;
import controller.MotorController;
import controller.UltrasonicController;
import orientation.Orienteer;


public class Lab5 {

	public static void main(String[] args) {
		LCD.drawString("Press any button", 0, 0);
		Button.waitForAnyPress();
		
		MotorController motorController = new MotorController();
		UltrasonicController ultrasonicController = new UltrasonicController(motorController);
		Orienteer orienteer = new Orienteer(ultrasonicController, motorController);
		orienteer.start();
		
		Button.waitForAnyPress();
	}

}
