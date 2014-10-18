package orientation;

public interface Orienteer {
	/**
	 * Add an advance to robot relative path
	 */
	void advance();

	/**
	 * Add a left turn to robot relative path
	 */
	void turnLeft();

	/**
	 * Add a right turn to robot relative path
	 */
	void turnRight();

	/**
	 * Add a distance line of sight condition to enhance orienteering
	 * 
	 * @param freeTiles
	 */
	void observeLineOfSight(int freeTiles);

	/**
	 * Check if the position is known
	 * 
	 * @return isPositionKnown
	 */
	boolean isPositionAmbiguous();

	/**
	 * Get the current position if it is known
	 * 
	 * @return current position
	 */
	Position getCurrentPosition();

	/**
	 * Get the start position if it is known
	 * 
	 * @return start position
	 */
	Position getStartingPosition();
}