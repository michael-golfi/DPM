package orientation;

public enum Orientation {
	NORTH, EAST, SOUTH, WEST;

	/**
	 * Gets the clockwise orientation to the current one
	 * 
	 * @return clockwise orientation
	 */
	public Orientation getNextOrientation() {
		return values()[(ordinal() + 1) % 4];
	}

	/**
	 * Gets the counter-clockwise orientation to the current one
	 * 
	 * @return counter-clockwise orientation
	 */
	public Orientation getLastOrientation() {
		int place = (ordinal() == 0) ? 4 : ordinal();
		return values()[(place - 1) % 4];
	}
}
