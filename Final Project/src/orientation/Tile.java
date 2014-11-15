package orientation;

public class Tile {
	
	private Block block;
	private Arrow[] arrow;
	
	public int tileIndex;
	
	public Tile(Block block, int index){
		this.block = block;
		this.tileIndex = index;
		
		initializeArrows();
	}
		
	private void initializeArrows(){
		arrow = new Arrow[4];	
		for(int i=0;i<arrow.length;i++)
			arrow[i] = new Arrow(this, this.tileIndex*4+i);
	}
	
	public Arrow[] getArrows(){
		return arrow;
	}
	
	public Block getBlock(){
		return block;
	}
	
}
