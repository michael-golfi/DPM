import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PositionTests {

	@Test
	public void test() {
		Position p = new Position(0, 0, AbsoluteDirection.NORTH);
		p.advance();
		assertEquals(0, p.xTile);
		assertEquals(1, p.yTile);
		assertEquals(AbsoluteDirection.NORTH, p.direction);
		
		p.turnLeft();
		assertEquals(0, p.xTile);
		assertEquals(1, p.yTile);
		assertEquals(AbsoluteDirection.WEST, p.direction);
		
		p.advance();
		assertEquals(-1, p.xTile);
		assertEquals(1, p.yTile);
		assertEquals(AbsoluteDirection.WEST, p.direction);
		
		p.turnRight();
		assertEquals(-1, p.xTile);
		assertEquals(1, p.yTile);
		assertEquals(AbsoluteDirection.NORTH, p.direction);
	}
	
	@Test
	public void test2() {
		Position p = new Position(0, 0, AbsoluteDirection.EAST);
		p.advance();
		assertEquals(1, p.xTile);
		assertEquals(0, p.yTile);
		assertEquals(AbsoluteDirection.EAST, p.direction);
	}
}
