import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OrienteerTests {
	
	@Test
	public void test() {
		Field f = new Field(new Tile[][]{
			{Tile.EMPTY,	Tile.EMPTY},
			{Tile.BLOCKED,	Tile.EMPTY}
		}); // (1, 0) is blocked, starting position is (0, 1) EAST
		
		DefaultOrienteer o = new DefaultOrienteer(f);
		
		assertTrue(o.isPositionAmbiguous());
		o.observeLineOfSight(1);
		o.turnRight();
		o.advance();
		assertFalse(o.isPositionAmbiguous());
		o.observeLineOfSight(0);
		assertFalse(o.isPositionAmbiguous());
		
		Position cp = o.getCurrentPosition();
		assertEquals(0, cp.xTile);
		assertEquals(0, cp.yTile);
		assertEquals(AbsoluteDirection.SOUTH, cp.direction);
		
		Position sp = o.getStartingPosition();
		assertEquals(0, sp.xTile);
		assertEquals(1, sp.yTile);
		assertEquals(AbsoluteDirection.EAST, sp.direction);
		
		//test start position caching
		Position sp2 = o.getStartingPosition();
		assertEquals(sp, sp2);
		
		//TODO test resilience against contradictory input
	}
	
	@Test
	public void test2() { //typical run using deterministic method
		Field f = new Field(new Tile[][]{
			{ Tile.EMPTY, 	Tile.EMPTY, Tile.EMPTY, 	Tile.BLOCKED 	},
			{ Tile.BLOCKED,	Tile.EMPTY, Tile.EMPTY, 	Tile.EMPTY 		},
			{ Tile.EMPTY, 	Tile.EMPTY, Tile.BLOCKED, 	Tile.EMPTY 		},
			{ Tile.EMPTY, 	Tile.EMPTY, Tile.BLOCKED, 	Tile.EMPTY 		}
		}); //Starting point is (0, 0, NORTH)
		
		DefaultOrienteer o = new DefaultOrienteer(f);
		
		o.observeLineOfSight(2);
		o.advance(); 	//(0, 1, NORTH);
		o.observeLineOfSight(1);
		o.advance(); 	//(0, 2, NORTH);
		o.observeLineOfSight(0);
		o.turnRight();	//(0, 2, EAST);
		o.observeLineOfSight(1);
		o.advance();	//(1, 2, EAST);
		o.observeLineOfSight(0);
		o.turnRight();	//(1, 2, SOUTH);
		o.observeLineOfSight(1);
		o.advance();	//(1, 1, SOUTH);
		o.observeLineOfSight(0);
		o.turnRight();  //(1, 1, WEST);
		o.observeLineOfSight(1);
		o.advance();	//(0, 1, WEST);
		o.observeLineOfSight(0);
		System.out.println();
	}

	@Test
	public void test3() { //typical run using deterministic method
		Field f = new Field(new Tile[][]{
			{ Tile.EMPTY, 	Tile.EMPTY, Tile.EMPTY, 	Tile.BLOCKED 	},
			{ Tile.BLOCKED,	Tile.EMPTY, Tile.EMPTY, 	Tile.EMPTY 		},
			{ Tile.EMPTY, 	Tile.EMPTY, Tile.BLOCKED, 	Tile.EMPTY 		},
			{ Tile.EMPTY, 	Tile.EMPTY, Tile.BLOCKED, 	Tile.EMPTY 		}
		}); //Starting point is (0, 0, NORTH)
		
		DefaultOrienteer o = new DefaultOrienteer(f);
		
		//Starting at 1,1 N
		o.observeLineOfSight(2);
		o.advance(); //1,2 N
		o.advance(); // 1,3 N
		o.turnRight(); // 1,3 E
		o.advance(); // 2,3 E
		o.advance(); // 3,3 E
		
		System.out.println();
	}
}
