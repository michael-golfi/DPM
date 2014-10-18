package utils;

/**
 * 
 * A simple utility to start multiple threads with one command
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class ThreadStart {

	/**
	 * Starts all thread objects
	 * 
	 * @param threaded
	 *            objects
	 */
	public static void startAll(Thread[] objects) {
		for (Thread thread : objects)
			thread.start();
	}

}
