package tictactoe;

import gamehandler.GamePlayer;
import gamehandler.Move;
import gamehandler.Result;

public class TicTacToeAI extends GamePlayer {
	
	private boolean running;
	private boolean moveRequested;
	
	class AiThread implements Runnable {
		public void run() {
			while(running) {
				if(moveRequested) {
					moveRequested = false;
					try {
						Thread.sleep(300);
					} catch(InterruptedException e) {
						System.out.println("AI thread failed to sleep:");
						e.printStackTrace();
					}
					int[] board = ((TicTacToeModel) model).getBoard().clone();
					Move move = getBestMove(board);
					model.doMove(move);
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
	
	public TicTacToeAI(int importedPlayerNum) {
		super(importedPlayerNum);
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
	public void requestMove() {
		moveRequested = true;
	}

	@Override
	public void endGame() {
		// stop thread
		running = false;
	}

	@Override
	public void tellMove(Move move) {
		// AI doesn't do anything with the other's move
	}
	
	private Move getBestMove(int[] board) {
		int best = minimax(board, playerNumber, Integer.MIN_VALUE, Integer.MAX_VALUE).getSpot();
		return Move.getFromInt(3, best);
	}
	
	private Result minimax(int[] board, int playernum, int alpha, int beta) {
		if(checkWin(board, playerNumber)) {
			return new Result(1, 0);
		} else if(checkWin(board, playerNumber == 2 ? 1 : 2)) {
			return new Result(-1, 0);
		} else if(checkFull(board)) {
			return new Result(0, 0);
		}
		int highestScore = -2;
		Result highestMove = null;
		int lowestScore = 2;
		Result lowestMove = null;
		
		if(playernum == playerNumber) {
			// maximizing
			int value = Integer.MIN_VALUE;
			for(int i=0; i <9; i++) {
				if(board[i] == 0) {
					board[i] = playernum;
					value = minimax(board, playernum == 2 ? 1 : 2, alpha, beta).getScore();
					Result move = new Result(value, i);
					board[i] = 0;
					if(move.getScore() > highestScore) {
						highestScore = move.getScore();
						highestMove = move;
					}
					alpha = Math.max(alpha, value);
					if(alpha > beta) {
						break;
					}
						
				}
			}
			return highestMove;
			
		}else {
			// minimizing
			int value = Integer.MAX_VALUE;
			for(int i=0; i <9; i++) {
				if(board[i] == 0) {
					board[i] = playernum;
					value = minimax(board, playernum == 2 ? 1 : 2, alpha, beta).getScore();
					Result move = new Result(value, i);
					board[i] = 0;
					if(move.getScore() < lowestScore) {
						lowestScore = move.getScore();
						lowestMove = move;
					}
					beta = Math.min(beta, value);
					if(alpha > beta) {
						break;
					}	
				}
			}
			return lowestMove;
			
		}
	}
	
	private boolean checkWin(int[] board, int playerNum) {
		if(board[0] == board[1] && board[1] == board[2] && board[0] == playerNum) {
			return true;
		}
		if(board[3] == board[4] && board[4] == board[5] && board[3] == playerNum) {
			return true;
		}
		if(board[6] == board[7] && board[7] == board[8] && board[6] == playerNum) {
			return true;
		}
		if(board[0] == board[3] && board[3] == board[6] && board[0] == playerNum) {
			return true;
		}
		if(board[1] == board[4] && board[4] == board[7] && board[1] == playerNum) {
			return true;
		}
		if(board[2] == board[5] && board[5] == board[8] && board[2] == playerNum) {
			return true;
		}
		if(board[0] == board[4] && board[4] == board[8] && board[0] == playerNum) {
			return true;
		}
		if(board[2] == board[4] && board[4] == board[6] && board[2] == playerNum) {
			return true;
		}
		return false;
	}
	
	private boolean checkFull(int[] board) {
		for(int i = 0; i < board.length; i++) {
			if(board[i] == 0) {
				return false;
			}
		}
		return true;
	}

}
