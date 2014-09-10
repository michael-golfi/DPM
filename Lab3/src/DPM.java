import lejos.nxt.Motor;

// Main Class
public class DPM {
	public static void main(String [] args) {
		Odometer odo = new Odometer(Motor.A, Motor.B, 30, true);
		Navigation nav = new Navigation(odo);
		
		// move in a square
		nav.travelTo(0, 30);
		nav.travelTo(30, 30);
		nav.travelTo(30, 0);
		nav.travelTo(0, 0);
	}
}
