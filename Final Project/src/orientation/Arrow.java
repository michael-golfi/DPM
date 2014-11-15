package orientation;

public class Arrow {
	
	private boolean possibleStartingLocation;
	
	private Arrow forwardArrow, counterClockwiseArrow, clockwiseArrow;
	
	private Tile tile;
	
	public int arrowIndex, arrowDirection;
	
	public Arrow(Tile tile, int arrowIndex){
		 this.tile = tile;
		 this.possibleStartingLocation = tile.getBlock() == Block.OBSTRUCTED? false : true;
		 this.arrowIndex = arrowIndex;
		 this.arrowDirection = arrowIndex%4;
	}
	
	public void setForwardArrow(Arrow a){
		forwardArrow = a;
	}
	
	public void setCounterClockwiseArrow(Arrow a){
		counterClockwiseArrow = a;
	}
	
	public void setClockwiseArrow(Arrow a){
		clockwiseArrow = a;
	}
	
	public Arrow getForwardArrow(){
		return forwardArrow;
	}
	
	public Arrow getClockwiseArrow(){
		return clockwiseArrow;
	}
	
	public Arrow getCounterClockwiseArrow(){
		return counterClockwiseArrow;
	}
	
	public boolean isPossibleStartingLocation(){
		return possibleStartingLocation;
	}
	
	public void setImpossibleStartingLocation(){
		possibleStartingLocation = false;
	}
	
	public Tile getTile(){
		return tile;
	}
	
}
