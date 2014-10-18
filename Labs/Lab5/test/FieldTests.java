import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FieldTests {

	@Test
	public void test() {
		Field f = new Field(new Tile[][]{
			{Tile.EMPTY,	Tile.EMPTY},
			{Tile.BLOCKED,	Tile.EMPTY}
		}); // (1, 0) is blocked
		
		Set<Position> ps = f.getPotentialPositions();
		assertEquals(12, ps.size());
		for(Position p : ps) {
			assertTrue(p.xTile != 1 || p.yTile != 0);
			assertTrue(p.xTile <= 1);
			assertTrue(p.yTile <= 1);
			assertTrue(p.xTile >= 0);
			assertTrue(p.yTile >= 0);
		}
		
		assertEquals(0, f.linesOfSight[0][0][AbsoluteDirection.EAST.ordinal() ]);
		assertEquals(1, f.linesOfSight[1][1][AbsoluteDirection.WEST.ordinal() ]);
		assertEquals(0, f.linesOfSight[1][1][AbsoluteDirection.NORTH.ordinal()]);
	}
}
