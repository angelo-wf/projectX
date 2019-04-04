package gamehandler;

public class Move {
	
	private int x;
	private int y;
	
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getAsInt(int width) {
		return y * width + x;
	}
	
	public static Move getFromInt(int width, int num) {
		return new Move(num % width, num / width);
	}
}
