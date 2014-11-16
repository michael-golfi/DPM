package orientation;

public class OrientationTest {
	public static void main(String[] args){
		
		Field field = new Field("");
		
		Orienteering orienteering = new Orienteering(field);
		
		//System.out.println(field.getTileMap()[0][0].getArrows()[0].getClockwiseArrow().getForwardArrow().getClockwiseArrow().getForwardArrow().getCounterClockwiseArrow().getForwardArrow().getTile());
		System.out.println(field.getTileMap()[3][2]);
		orienteering.orient();
	}
}
