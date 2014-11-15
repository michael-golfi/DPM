package orientation;

public class Field {
	
	private Tile[][] tileMap = 
			   {{new Tile(Block.OBSTRUCTED, 0),  new Tile(Block.UNOBSTRUCTED, 1), new Tile(Block.UNOBSTRUCTED, 2), new Tile(Block.UNOBSTRUCTED, 3)},
			   {new Tile(Block.UNOBSTRUCTED, 4), new Tile(Block.UNOBSTRUCTED, 5), new Tile(Block.OBSTRUCTED, 6), new Tile(Block.OBSTRUCTED, 7)},
			   {new Tile(Block.UNOBSTRUCTED, 8), new Tile(Block.UNOBSTRUCTED, 9), new Tile(Block.UNOBSTRUCTED, 10), new Tile(Block.UNOBSTRUCTED, 11)},
			   {new Tile(Block.UNOBSTRUCTED, 12), new Tile(Block.OBSTRUCTED, 13), new Tile(Block.UNOBSTRUCTED, 14), new Tile(Block.UNOBSTRUCTED, 15)}};
	
	public Field(String fileName){		
		createTileMap();
			
	}
	
	//returns true if the starting location is found, false otherwise
	public boolean foundStartingLocation(){
		return getNumberOfPossibleStartingLocations() == 1;
	}
	
	//TODO: change to private after debugging is complete
	public int getNumberOfPossibleStartingLocations(){
		int possibleStartingLocations = 0;
		
		for(Tile[] row : tileMap){
			for(Tile tile : row){
				Arrow[] arrow = tile.getArrows();
				for(Arrow a : arrow){
					if(a.isPossibleStartingLocation()){
						possibleStartingLocations++;
						//System.out.println(a.getTile().tileIndex + ": " + a.arrowDirection);
					}
				}
				
			}
		}
		
		return possibleStartingLocations;
	}
	
	private void createTileMap(){
		assignNeighbouringArrows();
	}
	
	//assign edges between all arrows on the field
	private void assignNeighbouringArrows(){
		
	
		for(int i=0;i<tileMap.length;i++){
			for(int j=0;j<tileMap[0].length;j++){
				
				Arrow[] arrow = tileMap[i][j].getArrows();
				
				arrow[0].setForwardArrow(retrieveArrow(i-1, j, 0));
				arrow[0].setClockwiseArrow(retrieveArrow(i, j, 1));
				arrow[0].setCounterClockwiseArrow(retrieveArrow(i, j, 3));
				
				arrow[1].setForwardArrow(retrieveArrow(i, j+1, 1));
				arrow[1].setClockwiseArrow(retrieveArrow(i, j, 2));
				arrow[1].setCounterClockwiseArrow(retrieveArrow(i, j, 0));
				
				arrow[2].setForwardArrow(retrieveArrow(i+1, j, 2));
				arrow[2].setClockwiseArrow(retrieveArrow(i, j, 3));
				arrow[2].setCounterClockwiseArrow(retrieveArrow(i, j, 1));
				
				arrow[3].setForwardArrow(retrieveArrow(i, j-1, 3));
				arrow[3].setClockwiseArrow(retrieveArrow(i, j, 0));
				arrow[3].setCounterClockwiseArrow(retrieveArrow(i, j, 2));
				
			}
		}
		
	}
	
	//returns the arrow at the specified tile if it exsist, otherwise return null
	private Arrow retrieveArrow(int i, int j, int a){
		try{
			return tileMap[i][j].getArrows()[a];
		}catch(Exception e){
			return null;
		}
	}
	
	public Tile[][] getTileMap(){
		return tileMap;
	}

}
