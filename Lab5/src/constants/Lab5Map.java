package constants;

import orientation.Map;
import orientation.Map.TileStatus;

public class Lab5Map {
	public static final TileStatus[][] map = new Map.TileStatus[][] { 
			{ TileStatus.EMPTY,		TileStatus.BLOCKED, TileStatus.EMPTY, 	TileStatus.EMPTY   },
			{ TileStatus.EMPTY, 	TileStatus.EMPTY, 	TileStatus.EMPTY, 	TileStatus.EMPTY   }, 
			{ TileStatus.EMPTY, 	TileStatus.EMPTY,	TileStatus.BLOCKED,	TileStatus.BLOCKED },
			{ TileStatus.BLOCKED,	TileStatus.EMPTY, 	TileStatus.EMPTY, 	TileStatus.EMPTY   } 
	};
}
