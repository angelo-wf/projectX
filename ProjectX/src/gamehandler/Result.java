package gamehandler;

public class Result {
	private int score;
	private int spot;
	
	public Result(int score, int spot) {
		this.score = score;
		this.spot = spot;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getSpot() {
		return spot;
	}
}
