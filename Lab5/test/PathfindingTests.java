import constants.Lab5Map;
import orientation.PathFinder;

public class PathfindingTests {
	@Test
	public void testPathFinding(){
		PathFinder pathFinder = new PathFinder();
		pathFinder.getPath(Lab5Map.map, 1, 1);
		
		System.out.println();
	}
}
