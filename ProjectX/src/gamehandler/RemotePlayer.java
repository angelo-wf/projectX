package gamehandler;

public class RemotePlayer extends GamePlayer {
	
	private boolean moveExpected;
	private boolean running;
	
	class RemoteThread implements Runnable {
		public void run() {
			while(running) {
				if(moveExpected) {
					// TODO: peek move from server
					// if it is a 'yourmove' instead, something went wrong
					model.doMove(new Move(0, 0));
					moveExpected = false;
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
	
	public RemotePlayer() {
		moveExpected = false;
		running = true;
		Thread remoteThread = new Thread(new RemoteThread());
		remoteThread.start();
	}
	
	@Override
	public void handleClick(Move move) {
		// Clicks are ignored for remote players
	}

	@Override
	public void requestMove(Move move) {
		// TODO: oeek 'yourmove' from server
		// TODO: send move to server
		moveExpected = true;
	}

	@Override
	public void endGame() {
		running = false;
	}

}
