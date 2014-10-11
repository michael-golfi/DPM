package orientation;

import java.util.ArrayList;

import utils.Vector;

public class Tree {
	public ArrayList<Tree> children;
	private Vector coordinateVector;

	public Tree(Vector coordinateVector) {
		this.coordinateVector = coordinateVector;
		children = new ArrayList<>();
	}

	public Tree(int x, int y) {
		this(new Vector(x, y));
	}

	/**
	 * Add a child to the tree
	 * 
	 * @param child
	 */
	public void addChild(Tree child) {
		if (child != null)
			children.add(child);
	}

	/**
	 * Get the child at the current index
	 * 
	 * @param index
	 * @return
	 */
	public Tree getChild(int index) {
		return children.get(index);
	}

	/**
	 * Get the coordinates of the current Tree node
	 * 
	 * @return
	 */
	public Vector getCoordinates() {
		return coordinateVector;
	}

	/**
	 * Flatten the branch of a Tree with given index
	 * 
	 * @param tree
	 * @param path
	 * @param index
	 */
	public static void flattenBranch(Tree tree, ArrayList<Vector> path,
			int index) {
		if (tree == null)
			return;

		path.add(tree.getCoordinates());

		if (tree.children.size() > 0) {
			Tree child = tree.children.get(index);
			flattenBranch(child, path, index);
		}
	}
}
