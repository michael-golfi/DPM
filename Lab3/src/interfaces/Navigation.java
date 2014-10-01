package interfaces;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public interface Navigation {
	/**
	 * 
	 * @return true if the robot is moving
	 */
	boolean isNavigating();

	/**
	 * Travel to coordinates x,y in the plane
	 * 
	 * @param x
	 * @param y
	 */
	void travelTo(double x, double y);

	/**
	 * Rotate robot by theta degrees
	 * 
	 * @param theta
	 */
	void turnTo(double theta);
}
