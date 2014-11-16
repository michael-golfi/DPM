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
public class PathFinder {
	
	private Field field;
	
	private TileNode originNode;
	
	public PathFinder(Field field){
		this.field = field;
	}
	
	public ArrayList<Tile> findPath(Tile origin, Tile destination){
		
		TreeMap treeMap = buildTreeMap(origin, destination); 
		
		return traverseTree(destination);
	
	}
	
	//returns the path list (of tiles) 
	private ArrayList<Tile> traverseTree(Tile destination){
		
		TileNode crt = originNode;
		ArrayList<Tile> tilePath = new ArrayList<Tile>();
		
		while(!crt.getTile().equals(destination)){
			
			crt = crt.getParent();
			
			tilePath.add(crt.getTile());
		}
		
		return tilePath;
		
	}
	
	//returns a breadth-first tree map with the destination as the root
	private TreeMap buildTreeMap(Tile origin, Tile destination){
		
		ArrayList<TileNode> traversalQueue = new ArrayList<TileNode>(); 
		
		TileNode root = new TileNode(destination, null);
		destination.setSeen();
		
		traversalQueue.add(root);
		
		TreeMap treeMap = new TreeMap(root);
		
		
		while(field.eachTileSeen() == false){
			TileNode crt = traversalQueue.get(0);
			
			for(Tile neighbourTile : crt.getTile().getUnobstructedNeighbours()){
				
				TileNode child = new TileNode(neighbourTile, crt);
				
				if(child.getTile().isSeen() == false){
					
					if(child.getTile() == origin)
						originNode = child;
					
					crt.addChild(child);
					child.getTile().setSeen();
					
					traversalQueue.add(child);	
				}		
			}
			traversalQueue.remove(0);
		
		}
		
		return treeMap;		
	}
	
}
