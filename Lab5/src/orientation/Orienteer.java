package orientation;

public interface Orienteer {
	void advance();
	void turnLeft();
	void turnRight();
	void observeLineOfSight(int freeTiles);
	boolean isPositionAmbiguous();
	Position getCurrentPosition();
	Position getStartingPosition();
}