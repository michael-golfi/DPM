package utils;

public class Array {

	/**
	 * Creates a copy of a 2D array
	 * 
	 * @param array
	 * @return new array
	 */
	public static int[][] copy(int[][] array) {
		int[][] copy = new int[array.length][array.length];
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				copy[i][j] = array[i][j];

		return copy;
	}
}
