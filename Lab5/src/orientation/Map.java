package orientation;

import java.util.ArrayList;

import constants.Lab5Map;
import utils.Array;
import utils.Vector;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Map {
	public enum TileStatus { EMPTY, BLOCKED, VISITED }

	TileStatus[][] map = Lab5Map.map;
	TileStatus[][] temp;

	ArrayList<Vector> positions = new ArrayList<Vector>();

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
		if (temp[y][x] != TileStatus.BLOCKED && temp[y][x] != TileStatus.VISITED) {
			Tree tree = new Tree(x, y);

			temp[y][x] = TileStatus.VISITED;

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
	 * Gets an ArrayList of all the possible starting positions and orientations
	 * 
	 * @return starting positions
	 */
	public ArrayList<Vector> getPossibleStartingPositions() {
		positions = new ArrayList<Vector>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == TileStatus.BLOCKED)
					continue;
				for (Orientation orientation : Orientation.values())
					positions.add(new Vector(j, i, orientation));
			}
		}

		return positions;
	}

	/**
	 * Filter possible starting positions based on free tiles around the robot
	 * 
	 * @param positions
	 * @param tilesAhead
	 */
	public ArrayList<Vector> filter(int tilesAhead) {
		ArrayList<Vector> difference = new ArrayList<>();
		for (Vector position : positions) {
			int x = position.getX();
			int y = position.getY();
			Orientation orientation = position.getOrientation();

			if (isValid(x, y, tilesAhead, orientation))
				difference.add(position);
		}
		positions.removeAll(difference);
		return positions;
	}

	public ArrayList<Vector> filter(Vector position, boolean blocked) {
		ArrayList<Vector> difference = new ArrayList<>();
		for (Vector possibleLocation : positions) {
			int x = position.getX();
			int y = position.getY();
			Orientation orientation = position.getOrientation();

			if (!isValid(possibleLocation, position, blocked))
				difference.add(position);
		}
		positions.removeAll(difference);
		return positions;
	}

	private boolean isValid(Vector possible, Vector position, boolean isBlocked) {
		Orientation realDir = possible.getOrientation();
		for (int i = 0; i < position.getOrientation().ordinal(); i++)
			realDir = realDir.getNextOrientation();

		int x = 0, y = 0;
		switch (possible.getOrientation()) {
		case NORTH:
			x = possible.getX() + position.getX();
		case SOUTH:
			x = possible.getX() - position.getX();
		case EAST:
			x = possible.getX() + position.getY();
		case WEST:
			x = possible.getX() - position.getY();
		}

		switch (possible.getOrientation()) {
		case WEST:
			y = possible.getY() + position.getX();
		case EAST:
			y = possible.getY() - position.getX();
		case NORTH:
			y = possible.getY() + position.getY();
		case SOUTH:
			y = possible.getY() - position.getY();
		}

		if (x < 0 || x > 3 || y < 0 || y > 3 || map[x][y] == TileStatus.BLOCKED)
			return false;

		switch (realDir) {
		case NORTH:
			if (isBlocked)
				return (y == 3 || map[x][y + 1] == TileStatus.BLOCKED);
			else
				return (y < 3 && !(map[x][y + 1] == TileStatus.BLOCKED));
		case SOUTH:
			if (isBlocked)
				return (y == 0 || map[x][y - 1] == TileStatus.BLOCKED);
			else
				return (y > 0 && !(map[x][y - 1] == TileStatus.BLOCKED));
		case WEST:
			if (isBlocked)
				return (x == 0 || map[x - 1][y] == TileStatus.BLOCKED);
			else
				return (x > 0 && !(map[x - 1][y] == TileStatus.BLOCKED));
		case EAST:
			if (isBlocked)
				return (x == 3 || map[x + 1][y] == TileStatus.BLOCKED);
			else
				return (x < 3 && !(map[x + 1][y] == TileStatus.BLOCKED));
		}
		return true;
	}
	
	public static ArrayList<Position> Orient(TileStatus[][] map, int[] tileDistances) {
		ArrayList<Position> potentialStartingPositions = new ArrayList<>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				// iterating over tiles of the map

				if (map[i][j] == TileStatus.EMPTY) {
					for (Orientation startingOrientation : Orientation.values()) {
						// iterating over potential starting orientations given
						// a specific tile
						Position potentialStartingPosition = new Position(
								startingOrientation, i, j);
						boolean valid = true;

						for (int d = 0; d < Orientation.values().length
								&& valid == true; d++) {
							// iterating over lines of sight given a starting
							// tile and orientation

							// Which line of sight we're looking at
							Orientation crt = startingOrientation;
							for (int it = 0; it < d; it++) {
								crt = crt.getNextOrientation();
							}

							// look in that direction, see if line of sight
							// works
							Position blockedLineOfSight = potentialStartingPosition
									.getTile(crt, tileDistances[d] + 1);

							// Check if line of sight ends within map
							if ((blockedLineOfSight.xTile > map.length)
									|| (blockedLineOfSight.yTile > map[0].length)
									|| (blockedLineOfSight.xTile < -1)
									|| (blockedLineOfSight.yTile < -1)) {
								valid = false;
								break;
							}

							// Check that the line of sight is properly blocked
							if (blockedLineOfSight.xTile != -1
									&& blockedLineOfSight.xTile != map.length
									&& blockedLineOfSight.yTile != -1
									&& blockedLineOfSight.yTile != map[0].length
									&& map[blockedLineOfSight.xTile][blockedLineOfSight.yTile] == TileStatus.EMPTY) {
								valid = false;
								break;
							}

							// Check if line of sight is clear until blocked
							// tile
							for (int td = 1; td <= tileDistances[d]; td++) {
								Position p = potentialStartingPosition.getTile(
										crt, td);
								if (map[p.xTile][p.yTile] != TileStatus.EMPTY) {
									valid = false;
									break;
								}
							}
						}

						if (valid) {
							potentialStartingPositions
									.add(potentialStartingPosition);
						}
					}
				}
			}
		}
		return potentialStartingPositions;
	}


	/**
	 * Check if a position is valid based on the number of tiles in front
	 * 
	 * @param x
	 * @param y
	 * @param tilesAhead
	 * @param orientation
	 * @return
	 */
	private boolean isValid(int x, int y, int tilesAhead,
			Orientation orientation) {
		switch (orientation) {
		case NORTH:
			if (y + tilesAhead >= map.length)
				return true;

			for (int i = y; i <= y + tilesAhead; i++)
				if (map[i][x] == TileStatus.BLOCKED)
					return true;
			return false;
		case EAST:
			if (x + tilesAhead >= map.length)
				return true;

			for (int i = x; i <= x + tilesAhead; i++)
				if (map[y][i] == TileStatus.BLOCKED)
					return true;
			return false;
		case SOUTH:
			if (y - tilesAhead < 0)
				return true;

			for (int i = y; i >= y - tilesAhead; i--)
				if (map[i][x] == TileStatus.BLOCKED)
					return true;
			return false;
		case WEST:
			if (x - tilesAhead < 0)
				return true;

			for (int i = x; i >= x - tilesAhead; i--)
				if (map[y][i] == TileStatus.BLOCKED)
					return true;
			return false;
		default:
			return false;
		}
	}

}
