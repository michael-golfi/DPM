package orientation;

public enum Orientation {
	WEST, SOUTH, EAST, NORTH;

	/**
	 * Gets the clockwise orientation to the current one
	 * 
	 * @return clockwise orientation
	 */
	public Orientation getNextOrientation() {
		return values()[(ordinal() + 1) % 4];
	}
}
