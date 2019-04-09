package gamehandler;

import application.Gui;
import javafx.scene.layout.Pane;

public abstract class GameView {
	
	protected GamePlayer player;
	protected GameModel model;
	protected Gui gui;
	
	protected Pane statsPane;
	protected Pane boardView;
	
	public GameView(Gui gui) {
		this.gui = gui;
	}
	
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
