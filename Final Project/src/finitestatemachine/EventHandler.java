package finitestatemachine;

import blockdetection.BlockFinding;
import orientation.Field;
import orientation.Orienteering;
import orientation.PathFinder;
import navigation.Navigator;
import odometry.Odometer;
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
	private MotorController motorController;
	private Odometer odometer;
	private Orienteering orienteering;
	private Field field;
	private PathFinder pathFinder;

	
	//CONSTRUCTOR
	public EventHandler(){
		motorController = new MotorController();
		ultrasonicController = new UltrasonicController(motorController);
		odometer = new Odometer(motorController);
		navigator = new Navigator(motorController, odometer);
		field = new Field(Map.map1);
		orienteering = new Orienteering(field, navigator, ultrasonicController, odometer);
		pathFinder = new PathFinder(field, navigator, odometer);
	}
	
	/**
	 * @return
	 */
	public boolean handleOrienteering() {
		motorController.grabBlock();
		orienteering.orient();
		return true;
	}

	/**
	 * @return
	 */
	public boolean handleNavigatingToBlocks() {
		navigator.turnTo(180);
		//odometer.orientation = odometer.invertOrientation(odometer.orientation);
		pathFinder.findPath(orienteering.getCurrentTile(), field.getTileMap()[5][1]);
		return true;
	}

	/**
	 * @return
	 */
	public boolean handleFindingBlocks() {
		BlockFinding blockFinding = new BlockFinding(odometer, navigator, motorController);
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
