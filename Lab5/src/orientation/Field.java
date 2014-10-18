package orientation;

import java.util.ArrayList;

public class Field {
	public Tile[][] map;
	public int[][][] linesOfSight;
	private ArrayList<Position> potentialPositions;
	
	public Field(Tile[][] map) {
		this.map = map;
		this.linesOfSight = new int[map.length][map[0].length][4];
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				
				//north LOS
				int n = Direction.NORTH.ordinal();
				for(int a = 1; y + a <= map[0].length; a++) {
					if(y + a == map[0].length || map[x][y + a] == Tile.BLOCKED) {
						linesOfSight[x][y][n] = a - 1;
						break;
					}
				}
				
				//south LOS
				int s = Direction.SOUTH.ordinal();
				for(int a = 1; y - a >= -1; a++) {
					if(y - a == -1 || map[x][y - a] == Tile.BLOCKED) {
						linesOfSight[x][y][s] = a - 1;
						break;
					}
				}

				//east LOS
				int e = Direction.EAST.ordinal();
				for(int a = 1; x + a <= map.length; a++) {
					if(x + a == map.length || map[x + a][y] == Tile.BLOCKED) {
						linesOfSight[x][y][e] = a - 1;
						break;
					}
				}
				
				//west LOS
				int w = Direction.WEST.ordinal();
				for(int a = 1; x - a >= -1; a++) {
					if(x - a == -1 || map[x - a][y] == Tile.BLOCKED) {
						linesOfSight[x][y][w] = a - 1;
						break;
					}
				}
			}
		}
		
		this.potentialPositions = new ArrayList<>();
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				if(map[x][y] == Tile.EMPTY) {
					for(Direction d : Direction.values()) {
						this.potentialPositions.add(new Position(x, y, d));
					}
				}
			}
		}
	}
	
	public ArrayList<Position> getPotentialPositions() { 
		ArrayList<Position> result = new ArrayList<>();
		for(Position p : potentialPositions) {
			result.add(p.clone());
		}
		return result;
	}
}