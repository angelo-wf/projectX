package gamehandler;

public abstract class GameModel {
	
	protected GameView view;
	protected GamePlayer player1;
	protected GamePlayer player2;
	
	public void setView(GameView view) {
		this.view = view;
	}
	
	public void notifyView() {
		view.update();
	}
	
	public abstract void initGame();
	
	public abstract boolean doMove(Move move);
	
	public abstract void endGame(EndReason reason);
	
	public void setPlayer1(GamePlayer player) {
		this.player1 = player;
	}
	
	public void setPlayer2(GamePlayer player) {
		this.player2 = player;
	}
}
