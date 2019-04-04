package reversi;

import gamehandler.GamePlayer;
import gamehandler.Move;

import java.awt.*;

public class ReversiAI extends GamePlayer {
	private boolean running;
	private boolean moveRequested;
	class AiThread implements Runnable {
		public void run() {
			while(running) {
				if(moveRequested) {
					try {
						Thread.sleep(500);
					} catch(InterruptedException e) {
						System.out.println("AI thread failed to sleep:");
						e.printStackTrace();
					}
					moveRequested = false;
					while(true) {
						int x = (int) (Math.random() * 8);
						int y = (int) (Math.random() * 8);
						Move tryMove = new Move(x, y);
						if(model.doMove(tryMove)) {
							break;
						}
					}
				} else {
					try {
						Thread.sleep(10);
					} catch(InterruptedException e) {
						System.out.println("AI thread failed to sleep:");
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public ReversiAI() {
		super(1);
		running = true;
		moveRequested = false;
		Thread aiThread = new Thread(new AiThread());
		aiThread.start();

	}
	
	@Override
	public void handleClick(Move move) {
		// clicks are ignored for AI's
	}

	@Override
	public void requestMove(Move move) {
		moveRequested = true;
	}

	@Override
	public void endGame() {
		// stop thread
		running = false;
	}

	@Override
	public boolean isUserPlayer() {
		return false;
	}

	@Override
	public String playerName() {
		return null;
	}

	@Override
	public Point play(int[][] board) {
		return null;
	}
}
