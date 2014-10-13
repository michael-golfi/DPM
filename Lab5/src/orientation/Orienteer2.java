package orientation;

import java.util.ArrayList;

import navigation.Navigator;
import lejos.nxt.LCD;
import orientation.Map.TileStatus;
import controller.UltrasonicController;

public class Orienteer2 {

	public Orienteer2(UltrasonicController ultrasonicController,
			Navigator navigator) {
		this.ultrasonicController = ultrasonicController;
		this.navigator = navigator;
	}

	private UltrasonicController ultrasonicController;
	private Navigator navigator;

	public void deterministic() {
		int[] distances = ultrasonicController.getAllDistances();
		Map.TileStatus[][] map = new Map.TileStatus[][] {
				{ TileStatus.EMPTY,   TileStatus.EMPTY, TileStatus.EMPTY,	TileStatus.BLOCKED },
				{ TileStatus.BLOCKED, TileStatus.EMPTY, TileStatus.EMPTY,   TileStatus.EMPTY },
				{ TileStatus.EMPTY,   TileStatus.EMPTY, TileStatus.BLOCKED, TileStatus.EMPTY },
				{ TileStatus.EMPTY,   TileStatus.EMPTY, TileStatus.BLOCKED, TileStatus.EMPTY } };

		for (int i = 0; i < distances.length; i++) {
			if (distances[i] < 25)
				distances[i] = 0;
			else if (distances[i] >= 25 && distances[i] <= 55)
				distances[i] = 1;
			else if (distances[i] >= 55 && distances[i] <= 80)
				distances[i] = 2;
			else if (distances[i] > 80)
				distances[i] = 3;
		}

		try {
			ArrayList<Position> positions = Map.Orient(map, distances);
			Position potential = positions.get(0);

			LCD.drawString("x: " + potential.xTile, 0, 0);
			LCD.drawString("y: " + potential.yTile, 0, 1);
			LCD.drawString("o: " + potential.orientation, 0, 2);
		} catch (Exception ex) {
			LCD.drawString("" + distances[0], 0, 0);
			LCD.drawString("" + distances[1], 0, 1);
			LCD.drawString("" + distances[2], 0, 2);
			LCD.drawString("" + distances[3], 0, 3);
		}
	}
}
