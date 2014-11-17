package constants;

import orientation.Tile;

/**
 * Placeholder for the map being used
 * 
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 * 
 */
public class Map {
	public enum Tile{EMPTY,BLOCKED};
	
	public static final Tile[][] map = new Tile[][] {
			{ Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.BLOCKED },
			{ Tile.BLOCKED, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.BLOCKED, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.BLOCKED, Tile.EMPTY } };

	public static final Tile[][] mapTranspose = new Tile[][] {
			{ Tile.EMPTY, Tile.BLOCKED, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.BLOCKED, Tile.BLOCKED },
			{ Tile.BLOCKED, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY } };
}
