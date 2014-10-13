package orientation;

public class Position {
	public Orientation orientation;
	public int xTile;
	public int yTile;
	
	public Position(Orientation o, int xTile, int yTile) {
		this.orientation = o;
		this.xTile = xTile;
		this.yTile = yTile;
	}
	
	public Position getTile(Orientation o, int n) {
		switch(o) {
		case NORTH: return new Position(this.orientation, this.xTile,     this.yTile + n);
		case EAST:  return new Position(this.orientation, this.xTile + n, this.yTile    );
		case SOUTH: return new Position(this.orientation, this.xTile,     this.yTile - n);
		case WEST:  return new Position(this.orientation, this.xTile - n, this.yTile    );
		default: throw new IllegalArgumentException("Non-standard orientation");
		}
	}
}