package constants;

import orientation.Block;
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
	
	public final Tile[][] map1 = {
		   {new Tile(Block.UNOBSTRUCTED, 0),  new Tile(Block.OBSTRUCTED, 1), new Tile(Block.OBSTRUCTED, 2), new Tile(Block.UNOBSTRUCTED, 3), new Tile(Block.UNOBSTRUCTED, 4),  new Tile(Block.UNOBSTRUCTED, 5), new Tile(Block.UNOBSTRUCTED, 6), new Tile(Block.UNOBSTRUCTED, 7)},
		   {new Tile(Block.UNOBSTRUCTED, 8),  new Tile(Block.UNOBSTRUCTED, 9), new Tile(Block.OBSTRUCTED, 10), new Tile(Block.UNOBSTRUCTED, 11), new Tile(Block.UNOBSTRUCTED, 12),  new Tile(Block.UNOBSTRUCTED, 13), new Tile(Block.UNOBSTRUCTED, 14), new Tile(Block.OBSTRUCTED, 15)},
		   {new Tile(Block.OBSTRUCTED, 16),  new Tile(Block.UNOBSTRUCTED, 17), new Tile(Block.UNOBSTRUCTED, 18), new Tile(Block.OBSTRUCTED, 19), new Tile(Block.UNOBSTRUCTED, 20),  new Tile(Block.UNOBSTRUCTED, 21), new Tile(Block.OBSTRUCTED, 22), new Tile(Block.UNOBSTRUCTED, 23)},
		   {new Tile(Block.UNOBSTRUCTED, 24),  new Tile(Block.UNOBSTRUCTED, 25), new Tile(Block.UNOBSTRUCTED, 2), new Tile(Block.UNOBSTRUCTED, 3), new Tile(Block.OBSTRUCTED, 4),  new Tile(Block.UNOBSTRUCTED, 5), new Tile(Block.UNOBSTRUCTED, 6), new Tile(Block.UNOBSTRUCTED, 7)},
		   {new Tile(Block.OBSTRUCTED, 0),  new Tile(Block.UNOBSTRUCTED, 1), new Tile(Block.UNOBSTRUCTED, 2), new Tile(Block.UNOBSTRUCTED, 3), new Tile(Block.OBSTRUCTED, 4),  new Tile(Block.UNOBSTRUCTED, 5), new Tile(Block.UNOBSTRUCTED, 6), new Tile(Block.UNOBSTRUCTED, 7)},
		   {new Tile(Block.OBSTRUCTED, 0),  new Tile(Block.UNOBSTRUCTED, 1), new Tile(Block.UNOBSTRUCTED, 2), new Tile(Block.UNOBSTRUCTED, 3), new Tile(Block.OBSTRUCTED, 4),  new Tile(Block.UNOBSTRUCTED, 5), new Tile(Block.UNOBSTRUCTED, 6), new Tile(Block.UNOBSTRUCTED, 7)},
		   {new Tile(Block.OBSTRUCTED, 0),  new Tile(Block.UNOBSTRUCTED, 1), new Tile(Block.UNOBSTRUCTED, 2), new Tile(Block.UNOBSTRUCTED, 3), new Tile(Block.OBSTRUCTED, 4),  new Tile(Block.UNOBSTRUCTED, 5), new Tile(Block.UNOBSTRUCTED, 6), new Tile(Block.UNOBSTRUCTED, 7)},
		   {new Tile(Block.OBSTRUCTED, 0),  new Tile(Block.UNOBSTRUCTED, 1), new Tile(Block.UNOBSTRUCTED, 2), new Tile(Block.UNOBSTRUCTED, 3), new Tile(Block.OBSTRUCTED, 4),  new Tile(Block.UNOBSTRUCTED, 5), new Tile(Block.UNOBSTRUCTED, 6), new Tile(Block.UNOBSTRUCTED, 7)},
		   }; 
	
}
