package main;

import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;
import odometry.Odometer;

public class Avoider extends Thread {

	private final int MIN_DISTANCE = 20;
	private static double destX;
	private static double destY;

	private UltrasonicSensor us;
	private Navigator driver;

	public Avoider(UltrasonicSensor us, Navigator driver) {
		this.us = us;
		this.driver = driver;
	}

	/**
	 * Called when Avoid thread is started When it detects an object, the
	 * avoidBlock scenario is used
	 */
	@Override
	public void run() {
		while (true) {
			LCD.drawString("x: " + destX + " y: " + destY, 0, 5);
			destX = driver.currentDestination.getX();
			destY = driver.currentDestination.getY();
			if (us.getDistance() < MIN_DISTANCE) {
				avoidBlock();
				driver.travelTo(destX, destY);
			}
			sleep();
		}
	}

	/**
	 * Has the robot avoid blocks
	 * 
	 * Turns 90 deg Goes straight a specified distance Turns back to earlier
	 * position
	 * 
	 */
	public void avoidBlock() {
		driver.turnTo(90);
		driver.travel(30);
		driver.turnTo(-90);
		driver.travel(30);
		driver.turnTo(-45);
		
		if (us.getDistance() < MIN_DISTANCE)
			driver.travelTo(destX, destY);
	}

	public void sleep() {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}