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
	public static int DROPOFF_X, DROPOFF_Y, MAP;
	
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
	public static final double TILE_LENGTH = 30.48;

	/**
	 * Wheel radius: {@value} cm
	 */
	public static final double WHEEL_RADIUS = 1.7;

	/**
	 * Robot tire-to-tire width: {@value} cm
	 */
	public static double WIDTH = 19.64;

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
	
	/**
	 * The y-distance from the center of the robot to the color sensors: {@value} cm.
	 */
	public static final double DISTANCE_TO_CENTER = 12;
	/**
	 * The distance from each color sensor: {@value} cm
	 */
	public static final double DISTANCE_FROM_COLOR_SENSORS = 6.378;
	
	/**
	 * A timeout to wait for color sensor line detection failure: {@value} milliseconds
	 */
	public static final int CORRECTION_TIMEOUT = 2000;
}
