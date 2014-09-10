package Controllers;

public abstract class AbstractController extends Thread {

	public void run() {
		while (true) {
			processNewDistance();
		}
	}
	
	/**
	 * Controls motors based on current ultrasonic distance value
	 */
	public abstract void processNewDistance();
}
