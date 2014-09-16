package constants;

public class ControllerConstants {
	/**
	 * Ultrasonic Band Center: {@value}
	 */
	public static final int BAND_CENTER = 30;
	/**
	 * Ultrasonic Band Width: {@value}
	 */
	public static final int BAND_WIDTH = 3;
	/**
	 * Filter Threshold: {@value}
	 */
	public static final int FILTER_OUT = 31;
	/**
	 * The average between black and white light sensor readings: {@value}
	 */
	public static final int OFFSET = 45;
	/**
	 * The speed at which the controller drifts from the line {@value}
	 */
	public static final int KP = 10;
	public static final int SCALING = 10;
	/**
	 * The speed at which the controller returns to the line: {@value}
	 */
	public static final int TP = 50;
}
