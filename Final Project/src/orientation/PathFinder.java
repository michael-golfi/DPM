package orientation;

import java.util.ArrayList;

import utils.Vector;

public class PathFinder {
	private static Tile[][] temp;

	/**
	 * Build a tree of possible paths from the array, avoiding obstacles
	 * 
	 * @param starting
	 *            x
	 * @param starting
	 *            y
	 * @return a tree of moves
	 */
	private static Tree buildTree(int x, int y) {
		if (temp[y][x] != Tile.BLOCKED && temp[y][x] != Tile.BLOCKED) {
			Tree tree = new Tree(x, y);

			temp[y][x] = Tile.BLOCKED;

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
	 * @param x
	 * @param y
	 * @return a set of movement vectors
	 */
	public static ArrayList<Vector> getPath(Tile[][] map, int x, int y) {
		temp = Tile.copy(map);

		ArrayList<Vector> path = new ArrayList<>();

		Tree tree = buildTree(x, y);
		Tree.flattenBranch(tree, path, 0);

		temp = null;

		return path;
	}

}
