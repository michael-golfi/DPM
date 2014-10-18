package utils;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class ThreadStart {

	/**
	 * Starts all thread objects
	 * 
	 * @param objects
	 */
	public static void startAll(Thread[] objects) {
		for (Thread thread : objects)
			thread.start();
	}

}
