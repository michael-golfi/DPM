package finitestatemachine;

import blockdetection.BlockFinding;
import orientation.Field;
import orientation.Orienteering;
import orientation.PathFinder;
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
public class EventHandler extends Thread{
	
	private UltrasonicController ultrasonicController;
	private Navigator navigator;
	private DistanceNavigator navigator2;
	private MotorController motorController;
	private Odometer odometer;
	private OdometerCorrection odometerCorrection;
	private Orienteering orienteering;
	private Field field;
	private PathFinder pathFinder;

	
	//CONSTRUCTOR
	public EventHandler(){
		motorController = new MotorController();
		ultrasonicController = new UltrasonicController(motorController);
		odometer = new Odometer(motorController);
		odometerCorrection = new OdometerCorrection(odometer, motorController);
		navigator = new Navigator(motorController, odometer);
		navigator2 = new DistanceNavigator(odometer);
		field = new Field(Map.map1);
		orienteering = new Orienteering(field, navigator, navigator2, ultrasonicController, odometer);
		pathFinder = new PathFinder(field, navigator, navigator2, odometer);
	}
	
	/**
	 * @return
	 */
	public boolean handleOrienteering() {
		//motorController.grabBlock();
		odometer.start();
		orienteering.orient();
		return true;
	}

	/**
	 * @return
	 */
	public boolean handleNavigatingToBlocks() {
		odometerCorrection.start();
		//navigator.turnTo(180);
		//odometer.orientation = odometer.invertOrientation(odometer.orientation);
		pathFinder.findPath(orienteering.getCurrentTile(), field.getTileMap()[5][1]);
		return true;
	}

	/**
	 * @return
	 */
	public boolean handleFindingBlocks() {
		BlockFinding blockFinding = new BlockFinding(odometer, navigator2, motorController);
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
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return
	 */
	public boolean handleDroppingOffBlock() {
		// TODO Auto-generated method stub
		return false;
	}

}
