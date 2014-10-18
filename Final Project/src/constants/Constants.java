package constants;

/**
 * Placeholder for global constants
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class Constants
{
	/**
	 * LCD Refresh Period: {@value}
	 */
	public static final int REFRESH_PERIOD = 100;

	/**
	 * Left turn degree: {@value}
	 */
	public static final int LEFT = -90;

	/**
	 * Right turn degree: {@value}
	 */
	public static final int RIGHT = 90;

	/**
	 * Backwards degree: {@value}
	 */
	public static final int BACKWARD = 180;

	/**
	 * Length of one tile: {@value} cm
	 */
	public static final int TILE_LENGTH = 30;

	/**
	 * Wheel radius: {@value} cm
	 */
	public static final double WHEEL_RADIUS = 2.10;

	/**
	 * Robot tire-to-tire width: {@value} cm
	 */
	public static final double WIDTH = 15.19;

	/**
	 * The period of Correction for the Odometer: {@value}
	 */
	public static final long CORRECTION_PERIOD = 10;

	/**
	 * The period of Display for the Odometer: {@value}
	 */
	public static final long DISPLAY_PERIOD = 250;

	/**
	 * The period of the Odometer: {@value}
	 */
	public static final long ODOMETER_PERIOD = 25;
}
