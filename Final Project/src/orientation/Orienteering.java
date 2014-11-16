package orientation;

import java.util.ArrayList;

public class Orienteering {
	
	private Field field;
	
	private ArrayList<Action> actionList;
	
	private Block[] observationList = {Block.UNOBSTRUCTED, Block.OBSTRUCTED, Block.UNOBSTRUCTED, Block.UNOBSTRUCTED, Block.OBSTRUCTED, Block.UNOBSTRUCTED, Block.OBSTRUCTED, Block.OBSTRUCTED, Block.UNOBSTRUCTED};
	
	public Orienteering(Field field){
		this.field = field;
		this.actionList = new ArrayList<Action>();
	}
	
	//handles orienting algorithm
	public void orient(){
		
		int observationCounter = 0;
		
		while(field.foundStartingLocation() == false){
			
			Block observation = observe(observationCounter);
			observationCounter++;
			
			updatePossibleStartingLocations(observation);
								
			performAction(observation);
							
		
		}
		 
	}
	
	private Block observe(int observationCounter){
		//TODO: call to ultrasonicSensor controller
		return observationList[observationCounter];
	}
	
	//decide which action to take based on observation
	private void performAction(Block observation){
		
		if(observation == Block.UNOBSTRUCTED){
			moveForward();
		}else if(observation == Block.OBSTRUCTED){
			//TODO: possible implement algorithm to minimize number of possible starting locations per movement
			
			//if((int)Math.random()*2 == 0){
				//rotateClockwise();
			//}else{
				rotateCounterclockwise();
			//}
		}
		
	}
	
	private void updatePossibleStartingLocations(Block observation){
		//iterate through entire tileMap and check the remaining viable arrows if they are still viable starting locations
		for(Tile[] row : field.getTileMap()){
			for(Tile tile : row){
				Arrow[] arrow = tile.getArrows();
				for(Arrow a : arrow){
					if(a.isPossibleStartingLocation() && isPossibleStartingLocation(a, observation) == false)
						a.setImpossibleStartingLocation();
				}
			}
		}
	}
	
	int counter = 0;
	//returns whether an arrow is a viable starting location
	private boolean isPossibleStartingLocation(Arrow arrow, Block observation){
		Arrow trackingArrow = arrow;
		
		//iterate through action list and apply action to tracking arrow
		for(Action action : actionList){
			trackingArrow = retrieveArrow(trackingArrow, action);
			
			counter++;
			
			//if(getArrowObservation(trackingArrow) == Block.OBSTRUCTED)
				//return false;
				
		}
		
		if(getArrowObservation(retrieveArrow(trackingArrow, Action.MOVE_FORWARD)) != observation)
			return false;
		
		//System.out.println(counter + ") " + getArrowObservation(trackingArrow) + " -- " + arrow.getTile().tileIndex + ": " + arrow.arrowDirection);
		
		return true;		
	}
	
	private Block getArrowObservation(Arrow arrow){
		try{
			return arrow.getTile().getBlock();
		}catch(Exception e){
			return Block.OBSTRUCTED;
		}
	}
	
	private Arrow retrieveArrow(Arrow arrow, Action action){
		try{
			switch (action){
			case MOVE_FORWARD : 
				return arrow.getForwardArrow();
			case ROTATE_CLOCKWISE :
				return arrow.getClockwiseArrow();
			case ROTATE_COUNTERCLOCKWISE : 
				return arrow.getCounterClockwiseArrow();
			default :
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}
	
	private Tile determineStartingTile(){
		for(Tile[] row : field.getTileMap()){
			for(Tile tile : row){
				for(Arrow a : tile.getArrows()){
					if(a.isPossibleStartingLocation())
						return a.getTile();
				}
			}
		}
		
		return null;
	}
	
	private void moveForward(){
		actionList.add(Action.MOVE_FORWARD);
		//TODO: call to navigation
	}
	
	private void rotateClockwise(){
		actionList.add(Action.ROTATE_CLOCKWISE);
		//TODO: call to navigation
	}
	
	private void rotateCounterclockwise(){
		actionList.add(Action.ROTATE_COUNTERCLOCKWISE);
		//TODO: call to navigation
		
	}
	
	
}
