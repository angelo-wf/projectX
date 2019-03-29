package gamehandler;

public class Move {
	
	private int x;
	private int y;
	
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getAsInt(int width) {
		return y * width + x;
	}
}
