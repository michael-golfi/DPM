package orientation;

import java.util.ArrayList;

public class Tile {
	
	private Block block;
	private Arrow[] arrow;

	private Coordinate coordinate;	
	
	//used for building TreeMap
	private boolean seen;
	
	public int tileIndex;
		
	public Tile(Block block, int index){
		this.block = block;
		this.tileIndex = index;
		this.seen = false;
		
		initializeArrows();
	}
		
	private void initializeArrows(){
		arrow = new Arrow[4];	
		//for(int i=0;i<arrow.length;i++)
		arrow[0] = new Arrow(this, this.tileIndex*4+0, Orientation.NORTH);
		arrow[1] = new Arrow(this, this.tileIndex*4+1, Orientation.EAST);
		arrow[2] = new Arrow(this, this.tileIndex*4+2, Orientation.SOUTH);
		arrow[3] = new Arrow(this, this.tileIndex*4+3, Orientation.WEST);
	}
	
	public Arrow[] getArrows(){
		return arrow;
	}
	
	public Block getBlock(){
		return block;
	}
	
	public void setSeen(){
		this.seen = true;
	}
	
	public boolean isSeen(){
		return this.seen;
	}
	
	public void setCoordinate(Coordinate coord){
		this.coordinate = coord;
	}
	
	public Coordinate getCoordinate(){
		return this.coordinate;
	}
	
	public String toString(){
		return this.getCoordinate().toString();
	}
	
	//returns a list of all unobstructed neighbouring tiles
	public ArrayList<Tile> getUnobstructedNeighbours(){
		
		ArrayList<Tile> neighbours = new ArrayList<Tile>();
		
		for(Arrow a : arrow){
			try{
				if(a.getForwardArrow().getTile().getBlock() == Block.UNOBSTRUCTED)
					neighbours.add(a.getForwardArrow().getTile());
			}catch(Exception e){}
		}
		
		return neighbours;
	}
}
