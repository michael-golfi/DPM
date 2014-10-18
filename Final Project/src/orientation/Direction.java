package orientation;

/**
 * An enum to represent the possible different directions the robot can be
 * facing.
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 *
 */
public enum Direction {
	NORTH, WEST, SOUTH, EAST;

	/**
	 * Get the counter-clockwise direction to the current one.
	 * 
	 * @return the next CCW direction
	 */
	public Direction counterclockwise() {
		return Direction.values()[(this.ordinal() + 1) % 4];
	}

	/**
	 * Get the clockwise direction to the current one
	 * 
	 * @return the next clockwise direction
	 */
	public Direction clockwise() {
		int v = this.ordinal() - 1;
		return Direction.values()[v >= 0 ? v : v + 4];
	}

	/**
	 * Get the angle represented by the direction
	 * @return angle in degrees
	 */
	public int getAngle() {
		switch (values()[ordinal()]) {
		case NORTH:
			return 90;
		case WEST:
			return 180;
		case SOUTH:
			return 270;
		case EAST:
			return 0;
		}
		return 0;
	}
}
