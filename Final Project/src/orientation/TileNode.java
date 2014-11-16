package orientation;

import java.util.ArrayList;

/*
package orientation;

import java.util.ArrayList;

/**
 * 
 * DPM Final Project Group 15
 * 
 * Main - Oct 18, 2014
 * 
 * <p>
 * <b>Description:</b>
 * </p>
 * <ul>
 * </ul>
 * 
 */
public class TileNode {
	
	private Tile tile;
	
	private ArrayList<TileNode> children;
	
	private TileNode parent;
	
	public TileNode(Tile tile, TileNode parent){
		this.tile = tile;
		this.parent = parent;
		this.children = new ArrayList<TileNode>();
	}
	
	public void addChild(TileNode child){
		child.getTile().setSeen();
		children.add(child);
	}
	
	public Tile getTile(){
		return tile;
	}
	
	public TileNode getParent(){
		return parent;
	}
	
	public ArrayList<TileNode> getChildren(){
		return children;
	}
	
}
