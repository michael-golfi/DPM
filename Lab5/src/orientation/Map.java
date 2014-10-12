package orientation;

import java.util.ArrayList;

import utils.Array;
import utils.Vector;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Map {
	public static final int EMPTY = 0, BLOCKED = -1, VISITED = 2;
	
	/**
	 * Angle
	 */
	private static int up = 0, left = 90, down = 180, right = 270;
	int[][] map = new int[][] { { EMPTY, BLOCKED, EMPTY, EMPTY },
			{ EMPTY, EMPTY, EMPTY, EMPTY }, { EMPTY, EMPTY, BLOCKED, BLOCKED },
			{ BLOCKED, EMPTY, EMPTY, EMPTY } };

	int[][] temp;

	ArrayList<Vector> north = new ArrayList<>();
	ArrayList<Vector> east = new ArrayList<>();
	ArrayList<Vector> south = new ArrayList<>();
	ArrayList<Vector> west = new ArrayList<>();

	/**
	 * Build a tree of possible paths from the array, avoiding obstacles
	 * 
	 * @param starting
	 *            x
	 * @param starting
	 *            y
	 * @return a tree of moves
	 */
	private Tree buildTree(int x, int y) {
		if (temp[y][x] != BLOCKED && temp[y][x] != VISITED) {
			Tree tree = new Tree(x, y);

			temp[y][x] = VISITED;

			if (y < temp.length - 1) {
				Tree upTile = buildTree(x, y + 1);
				tree.addChild(upTile);
			}
			if (x > 0) {
				Tree leftTile = buildTree(x - 1, y);
				tree.addChild(leftTile);
			}
			if (y > 0) {
				Tree downTile = buildTree(x, y - 1);
				tree.addChild(downTile);
			}
			if (x < temp.length - 1) {
				Tree rightTile = buildTree(x + 1, y);
				tree.addChild(rightTile);
			}

			return tree;
		}

		return null;
	}

	/**
	 * Find the path from start to finish in the tree
	 * 
	 * @param starting
	 *            x
	 * @param starting
	 *            y
	 * @return a set of movement vectors
	 */
	public ArrayList<Vector> getPath(int x, int y) {
		temp = Array.copy(map);

		ArrayList<Vector> path = new ArrayList<>();

		Tree tree = buildTree(x, y);
		Tree.flattenBranch(tree, path, 0);

		temp = null;

		return path;
	}

	/**
	 * Gets an ArrayList of all the possible starting positions
	 * 
	 * @return starting positions
	 */
	public ArrayList<Vector> getPossibleStartingPositions() {
		ArrayList<Vector> positions = new ArrayList<Vector>();

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == BLOCKED)
					continue;

				positions.add(new Vector(j, i));
			}
		}

		north = new ArrayList<>(positions);
		east = new ArrayList<>(positions);
		south = new ArrayList<>(positions);
		west = new ArrayList<>(positions);

		return positions;
	}

	/**
	 * Filter possible starting positions based on free tiles around the robot
	 * 
	 * @param positions
	 * @param tilesAhead
	 */
	public void filterValidStartingPositions(int tilesAhead) {
		filter(north, tilesAhead, Orientation.NORTH);
		filter(east, tilesAhead, Orientation.EAST);
		filter(south, tilesAhead, Orientation.SOUTH);
		filter(west, tilesAhead, Orientation.WEST);
		System.out.println();
	}
	
	public void filter(ArrayList<Vector> positions, int tilesAhead, Orientation orientation) {
		ArrayList<Vector> difference = new ArrayList<>();
		for (Vector position : positions) {
			int x = position.getX();
			int y = position.getY();

			if (isBlocked(x, y, tilesAhead, orientation))
				difference.add(position);
		}
		positions.removeAll(difference);
	}

	public boolean isBlocked(int x, int y, int tilesAhead,
			Orientation orientation) {
		switch (orientation) {
		case NORTH:
			if (y + tilesAhead >= map.length)
				return true;
			
			for (int i = y; i <= y + tilesAhead; i++)
				if (map[i][x] == BLOCKED)
					return true;
			return false;
		case EAST:
			if (x + tilesAhead >= map.length)
				return true;

			for (int i = x; i <= x + tilesAhead; i++)
				if (map[y][i] == BLOCKED)
					return true;
			return false;
		case SOUTH:
			if (y - tilesAhead < 0)
				return true;

			for (int i = y; i >= y - tilesAhead; i--)
				if (map[i][x] == BLOCKED)
					return true;
			return false;
		case WEST:
			if (x - tilesAhead < 0)
				return true;

			for (int i = x; i >= x - tilesAhead; i--)
				if (map[y][i] == BLOCKED)
					return true;
			return false;
		default:
			return false;
		}
	}
	
	public void changeOrientations(int theta){
		
	}
}
