package orientation;

public class Position implements Cloneable {
	public int xTile, yTile;
	public Direction direction;
	
	public Position(int xTile, int yTile, Direction direction) {
		this.xTile = xTile;
		this.yTile = yTile;
		this.direction = direction;
	}
	
	public void advance() {
		switch(this.direction) {
		case SOUTH: this.yTile--; break;
		case NORTH: this.yTile++; break;
		case WEST : this.xTile--; break;
		case EAST : this.xTile++; break;
		}
	}
	
	public void turnRight() {
		this.direction = this.direction.clockwise();
	}
	
	public void turnLeft() {
		this.direction = this.direction.counterclockwise();
	}
	
	@Override
	public String toString() {
		return "(" + this.xTile + ", " + this.yTile + ", " + this.direction + ")";
	}
	
	@Override
	public Position clone() {
		return new Position(this.xTile, this.yTile, this.direction);
	}
}
