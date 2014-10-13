

public interface MotorControl {
	/**
	 * Start the motor at default speed
	 * 
	 * @param motor
	 */
	void start();

	/**
	 * Start the motor at default speed in reverse
	 * 
	 * @param motor
	 */
	void reverse();

	/**
	 * Stop the motor immediately
	 * 
	 * @param motor
	 */
	void stop();

	/**
	 * In-place left turn
	 */
	void inplaceLeft();

	/**
	 * In-place right turn
	 */
	void inplaceRight();

	/**
	 * Set left motor speed
	 * 
	 * @param radians
	 */
	void setLeftMotorSpeed(int radians);

	/**
	 * Set right motor speed
	 * 
	 * @param radians
	 */
	void setRightMotorSpeed(int radians);
	
	/**
	 * Rotates the sensor to an angle
	 * @param angle
	 */
	void rotateSensor(int angle);
}
