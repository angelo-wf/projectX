package gamehandler;

import ai.AI;
import javafx.scene.layout.Pane;

public abstract class GameView {
	
	protected AI player;
	protected GameModel model;
	
	protected Pane statsPane;
	protected Pane boardView;
	
	public void setPlayer(AI player) {
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
		//player.handleClick(move);
	}
}
