package orientation;

import java.util.ArrayList;

import javax.xml.stream.events.XMLEvent;

import odometry.Odometer;
import lejos.nxt.Button;
import lejos.nxt.comm.RConsole;
import navigation.DistanceNavigator;
import navigation.Navigator;

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
	
	private Navigator navigator;
	
	private DistanceNavigator navigator2;
	
	private Odometer odometer;
	
	private Tile currentTile;
	
	public PathFinder(Field field, Navigator navigator, DistanceNavigator navigator2, Odometer odometer){
		this.field = field;
		this.navigator = navigator;
		this.navigator2 = navigator2;
		this.odometer = odometer;
	}
	
	public void findPath(Tile origin, Tile destination){
		
		TreeMap treeMap = buildTreeMap(origin, destination); 
		
		navigatePath(traverseTree(destination));
		//navigatePath2(traverseTree(destination), origin);
	
	}
	
	public void navigatePath(ArrayList<Tile> path){
		RConsole.println("path: " + path);
		for(Tile tile : path){
			synchronized (odometer) {
				RConsole.println("odometer: (" + odometer.getX() + ", " + odometer.getY() + ") -- " + Math.toDegrees(odometer.getTheta()));
				RConsole.println("Travel to: " + (tile.getCoordinate().getX()+15.0) + ", " + (tile.getCoordinate().getY()+15));
			}
			navigator2.travelTo((tile.getCoordinate().getX()+15.0), (tile.getCoordinate().getY()+15.0));
		}
		
		//navigator.turnTo(180);		
	}
	
	public void navigatePath2(ArrayList<Tile> path, Tile origin){
		currentTile = origin;
		for(Tile tile : path){
			
			RConsole.println("CURRENT ORIENTATION: " + odometer.orientation);
			RConsole.println("travel to: (" + tile.x + ", " + tile.y + ")");
			//Button.waitForAnyPress();
			
			
			switch(odometer.orientation){
			case NORTH :
				if(tile.x > currentTile.x){
					navigator.turnTo(-90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.EAST;
				}else if(tile.x < currentTile.x){
					navigator.turnTo(90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.WEST;
				}else if(tile.y > currentTile.y){
					navigator.travelDistance(30);
					odometer.orientation = Orientation.NORTH;
				}else if(tile.y < currentTile.y){
					navigator.turnTo(180);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.SOUTH;
				}
				break;
			case EAST :
				if(tile.x > currentTile.x){
					navigator.travelDistance(30);
					odometer.orientation = Orientation.EAST;
				}else if(tile.x < currentTile.x){
					navigator.turnTo(180);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.WEST;
				}else if(tile.y > currentTile.y){
					navigator.turnTo(90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.NORTH;
				}else if(tile.y < currentTile.y){
					navigator.turnTo(-90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.SOUTH;
				}
				break;
			case SOUTH :
				if(tile.x > currentTile.x){
					navigator.turnTo(90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.EAST;
				}else if(tile.x < currentTile.x){
					navigator.turnTo(-90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.WEST;
				}else if(tile.y > currentTile.y){
					navigator.turnTo(180);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.NORTH;
				}else if(tile.y < currentTile.y){
					navigator.travelDistance(30);
					odometer.orientation = Orientation.SOUTH;
				}
				break;
			case WEST :
				if(tile.x > currentTile.x){
					navigator.turnTo(180);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.EAST;
				}else if(tile.x < currentTile.x){;
					navigator.travelDistance(30);
					odometer.orientation = Orientation.WEST;
				}else if(tile.y > currentTile.y){
					navigator.turnTo(-90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.NORTH;
				}else if(tile.y < currentTile.y){
					navigator.turnTo(90);
					navigator.travelDistance(30);
					odometer.orientation = Orientation.SOUTH;
				}
				break;
			}
			currentTile = tile;
		}
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
