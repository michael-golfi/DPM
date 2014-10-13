package utils;

import orientation.Map.TileStatus;

public class Array {

	/**
	 * Creates a copy of a 2D array
	 * 
	 * @param map
	 * @return new array
	 */
	public static TileStatus[][] copy(TileStatus[][] map) {
		TileStatus[][] copy = new TileStatus[map.length][map.length];
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++)
				copy[i][j] = map[i][j];

		return copy;
	}
}
