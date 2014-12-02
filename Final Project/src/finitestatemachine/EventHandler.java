package finitestatemachine;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import blockdetection.BlockFinding;
import orientation.Field;
import orientation.NavigateToDropOff;
import orientation.Orienteering;
import orientation.PathFinder;
import orientation.Tile;
import navigation.DistanceNavigator;
import navigation.Navigator;
import odometry.Odometer;
import odometry.OdometerCorrection;
import constants.Constants;
import constants.Map;
import controller.MotorController;
import controller.UltrasonicController;


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
public class EventHandler{
	
	private UltrasonicController ultrasonicController;
	private UltrasonicSensor ultrasonicSensor;
	private Navigator navigator;
	private DistanceNavigator navigator2;
	private MotorController motorController;
	private Odometer odometer;
	private OdometerCorrection odometerCorrection;
	private Orienteering orienteering;
	private static Field field;
	private static PathFinder pathFinder;
	private static NavigateToDropOff navigateToDropOff;
	
	private Class ref;

	
	//CONSTRUCTOR
	public EventHandler(){
		motorController = new MotorController();
		ultrasonicController = new UltrasonicController(motorController);
		ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
		odometer = new Odometer(motorController);
		//odometerCorrection = new OdometerCorrection(odometer, motorController);
		navigator = new Navigator(motorController, odometer);
		navigator2 = new DistanceNavigator(odometer);
		field = new Field(Map.map1);

		orienteering = new Orienteering(field, navigator, navigator2, ultrasonicController, ultrasonicSensor, odometer);
		
		
		
		
	}
	
	public Odometer getOdometer(){
		return odometer;
	}
	
	public DistanceNavigator getNavigator(){
		return navigator2;
	}
	
	/**
	 * @return
	 */
	public boolean handleOrienteering() {
		//motorController.grabBlock();
		odometer.start();
		//odometerCorrection.start();
		orienteering.orient();

		handleNavigatingToBlocks(orienteering.getCurrentTile());
		
		orienteering = null;
		return true;
	}

	/**
	 * @return
	 */
	public boolean handleNavigatingToBlocks(Tile origin) {
		//navigator.turnTo(180);
		//odometer.orientation = odometer.invertOrientation(odometer.orientation);
		pathFinder = new PathFinder(field, navigator, navigator2, odometer);
		pathFinder.findPath(origin, field.getTileMap()[9][1]);
		pathFinder = null;
		System.gc();
		return true;
	}

	/**
	 * @return
	 */
	public boolean handleFindingBlocks() {
		BlockFinding blockFinding = new BlockFinding(odometer, navigator2, ultrasonicSensor, motorController);
		blockFinding = null;
		
		return false;
	}

	/**
	 * @return
	 */
	public boolean handlePickingUpBlock() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return
	 */
	public boolean handleNavigatingToDropOff() {
		//navigateToDropOff.navigateToDropOff(field2.getTileMap()[5][5]);
		return false;
	}

	/**
	 * @return
	 */
	public boolean handleDroppingOffBlock() {
		motorController.openClaw();
		Delay.msDelay(2000);
		navigator2.travelDistance(-10);
		motorController.grabBlock();
		
		navigator2.turnTo(180);
		navigator2.travelDistance(-10);
		
		return false;
	}

}
