package orientation;

import java.util.ArrayList;

import blockdetection.Scan;
import odometry.Odometer;
import odometry.OdometerCorrection;
import constants.Constants;
import controller.UltrasonicController;
import navigation.DistanceNavigator;
import navigation.Navigator;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;

public class Orienteering extends Thread{
	
	private Field field;
	
	private ArrayList<Action> actionList;
	
	private Block[] observationList = {Block.OBSTRUCTED, Block.OBSTRUCTED, Block.OBSTRUCTED, Block.UNOBSTRUCTED, Block.OBSTRUCTED, Block.UNOBSTRUCTED, Block.UNOBSTRUCTED, Block.OBSTRUCTED, Block.OBSTRUCTED};
	
	private Navigator navigator;
	
	private DistanceNavigator navigator2;
	
	private Odometer odometer;
	
	private UltrasonicController ultrasonicController;
	
	private Arrow trackingArrow;
	
	private Scan scanner;
	
	private long accTime;
	
	private int observationCounter;
	
	private int turnCounter = 0;
	private int previousTurnCounter = 0;
	
	private int blockedObservationCounter = 0;
	
	private boolean getOutOfHere = false;
	
	private boolean[] orientationTracker = new boolean[4];
	
	public Orienteering(Field field, Navigator navigator, DistanceNavigator navigator2, UltrasonicController ultrasonicController, UltrasonicSensor ultrasonicSensor, Odometer odometer){
		this.field = field;
		this.navigator = navigator;
		this.navigator2 = navigator2;
		this.ultrasonicController = ultrasonicController;
		this.scanner = new Scan(odometer, 0, ultrasonicSensor);
		scanner.start();
		this.odometer = odometer;
		this.actionList = new ArrayList<Action>();
		
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		while(true){
			accTime = System.currentTimeMillis() - startTime;
		}
	}
	
	//handles orienting algorithm
	public void orient(){
		
		
		observationCounter = 0;
		
		this.start();
		
		while(field.foundStartingLocation() == false){
			
			Block observation = observe(observationCounter);
			observationCounter++;
		
			RConsole.println("possible starting locations " + field.getNumberOfPossibleStartingLocations());
			
			updatePossibleStartingLocations(observation);
								
			performAction2(observation);
							
		
		}
		Sound.beep();
		
		this.interrupt();
		
		RConsole.println("Time: " + accTime);
		
		RConsole.println("observations: " + observationCounter);
		
		RConsole.println("Oriented: Current Theta" + getCurrentTheta() + "");
		
		odometer.setPosition(determineCurrentTile().getCoordinate().getX()+15,  determineCurrentTile().getCoordinate().getY()+15, Math.toRadians(getCurrentTheta()));
		//odometer.setPosition(determineCurrentTile().getCoordinate().getX()+15,  determineCurrentTile().getCoordinate().getY()+15, getCurrentTheta());
		
		odometer.orientation = determineCurrentArrow().getOrientation();
		
		synchronized (odometer) {
			RConsole.println("odometer: (" + odometer.getX() + ", " + odometer.getY() + ") -- " + Math.toDegrees(odometer.getTheta()));
		}
		
		System.out.println("start: " + determineStartingTile().tileIndex);
		
		RConsole.println("Starting Tile: " + determineStartingTile().tileIndex);
		RConsole.println("Current Tile: " + determineCurrentTile().tileIndex + " direction: " + determineCurrentArrow().getOrientation());
		
		 
	}
	
	private Block observe(int observationCounter){
		int distance = 0;
		while(distance == 0){
			distance = scanner.distance;
			Delay.msDelay(100);
		}
		RConsole.println("US sensor: " + distance);
		
		return distance < 30? Block.OBSTRUCTED : Block.UNOBSTRUCTED;
		
		//return ultrasonicController.isBlocked()? Block.OBSTRUCTED : Block.UNOBSTRUCTED; 
		//return observationList[observationCounter];
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
	
	private void performAction2(Block observation){
		
		/*if(getOutOfHere){
			if(observation == Block.UNOBSTRUCTED){
				moveForward();
				return;
			}else{
				getOutOfHere = false;
			}
		}*/
		
		if(observation == Block.OBSTRUCTED){
			blockedObservationCounter++;
			Sound.buzz();
		}
		
		//System.out.println(observationCounter + " -- " + blockedObservationCounter);
		
		if(observationCounter >= 16 && blockedObservationCounter < 6 && getOutOfHere == false){
			Sound.twoBeeps();
			getOutOfHere = true;
		}
		
		if(observation == Block.UNOBSTRUCTED && getOutOfHere == true){
			moveForward();
			getOutOfHere = false;
			return;
		}
		
		if(turnCounter < 3){
			if(observation == Block.UNOBSTRUCTED){
				orientationTracker[turnCounter] = true;
			}
			turnCounter++;
			rotateCounterclockwise();
		}else if(turnCounter == 3){
			if(observation == Block.UNOBSTRUCTED){
				orientationTracker[turnCounter] = true;
			}
			while(orientationTracker[turnCounter] == false){
								
				if(orientationTracker[1]){
					rotateClockwise();
					rotateClockwise();
					turnCounter -= 2;
				}else if(orientationTracker[0]){
					rotateCounterclockwise();
					//rotateClockwise();
					//rotateClockwise();
					//rotateClockwise();
					turnCounter -= 3;
				}else{				
					rotateClockwise();
					turnCounter--;
				}
			}
			previousTurnCounter = turnCounter;
			turnCounter = 0;
			resetOrientationTrackers();
			moveForward();
			//getOutOfHere = true;
		}
	}
	
	private void resetOrientationTrackers(){
		for(int i=0;i<orientationTracker.length;i++)
			orientationTracker[i] = false;
	}
	
	private int invertOrientation(int turnCounter){
		switch(turnCounter){
		case 0 : return 2;
		case 1: return 3;
		case 2 : return 0;
		case 3 : return 1;
		default : return -1;
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
		trackingArrow = arrow;
		
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
	
	private Arrow determineStartingArrow(){
		for(Tile[] row : field.getTileMap()){
			for(Tile tile : row){
				for(Arrow a : tile.getArrows()){
					if(a.isPossibleStartingLocation())
						return a;
				}
			}
		}
		
		return null;
	}
	
	public Tile getCurrentTile(){
		return determineCurrentTile();
	}
	
	public double getCurrentTheta(){
		switch(determineCurrentArrow().getOrientation()){
		case NORTH : return 90.0;
		case EAST : return 0;
		case SOUTH : return 270.0;
		case WEST : return 180.0;
		default : return -1.0;
		}
	}
	
	
	private Tile determineCurrentTile(){
		Arrow tracker = determineStartingArrow();
		
		for(Action action : actionList){
			tracker = retrieveArrow(tracker, action);
		}
		
		return tracker.getTile();		
	}
	
	private Arrow determineCurrentArrow(){
		Arrow tracker = determineStartingArrow();
		
		for(Action action : actionList){
			tracker = retrieveArrow(tracker, action);
		}
		
		return tracker;	
	}
	
	private void moveForward(){
		actionList.add(Action.MOVE_FORWARD);
		//navigator.travelDistance(30);
		//navigator.travelBackwards(Constants.TILE_LENGTH);
		navigator2.travelDistance(Constants.TILE_LENGTH);
	}
	
	private void rotateClockwise(){
		actionList.add(Action.ROTATE_CLOCKWISE);
		//navigator.turnTo(-90);
		navigator2.turnTo(-90);
	}
	
	private void rotateCounterclockwise(){
		actionList.add(Action.ROTATE_COUNTERCLOCKWISE);
		//navigator.turnTo(90);
		navigator2.turnTo(90);
		
	}
	
	
}
