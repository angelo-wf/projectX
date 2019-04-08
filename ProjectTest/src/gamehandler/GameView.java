package gamehandler;

import javafx.scene.layout.Pane;

public abstract class GameView {
	
	protected GamePlayer player;
	protected GameModel model;
	
	protected Pane statsPane;
	protected Pane boardView;
	
	public void setPlayer(GamePlayer player) {
		this.player = player;
	}
	
	public void setModel(GameModel model) {
		this.model = model;
	}
	
	public Pane getStatsPane() {
		return statsPane;
	}
	
	public Pane getBoardView() {
		return boardView;
	}
	
	public abstract void update();
	
	public void handleClick(Move move) {
		player.handleClick(move);
	}
}
