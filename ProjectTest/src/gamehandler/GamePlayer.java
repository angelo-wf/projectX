package gamehandler;

import reversi.ReversiModel;

import java.awt.*;

public abstract class GamePlayer {

	protected GameModel model;

	public void setModel(GameModel model) {
		this.model = model;
	}
	
	public abstract void handleClick(Move move);
	
	public abstract void requestMove(Move move);
	
	public abstract void endGame();

	}


