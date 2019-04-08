package gamehandler;

public abstract class GamePlayer {
	
	protected GameModel model;
	protected int playerNumber;
	
	public GamePlayer(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public void setModel(GameModel model) {
		this.model = model;
	}
	
	public abstract void handleClick(Move move);
	
	public abstract void tellMove(Move move);
	
	public abstract void requestMove();
	
	public abstract void endGame();
}
