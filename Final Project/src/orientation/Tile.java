package orientation;

public enum Tile
{
	EMPTY, BLOCKED;
	
	/**
	 * Creates a copy of a map
	 * 
	 * @param map
	 * @return copy
	 */
	public static Tile[][] copy(Tile[][] map) {
		Tile[][] copy = new Tile[map.length][map.length];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++)
				copy[i][j] = map[i][j];

		return copy;
	}
}
