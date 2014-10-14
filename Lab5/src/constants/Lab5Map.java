package constants;

import orientation.Tile;

/**
 * Placeholder for the map being used
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class Lab5Map {
	public static final Tile[][] map = new Tile[][] {
			{ Tile.EMPTY, Tile.BLOCKED, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.BLOCKED, Tile.BLOCKED },
			{ Tile.BLOCKED, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY } };
}
