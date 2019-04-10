package tictactoe;

import java.util.ArrayList;

import gamehandler.GamePlayer;
import gamehandler.Move;

public class TicTacToeAI extends GamePlayer {
	
	private boolean running;
	private boolean moveRequested;
	
	class AiThread implements Runnable {
		public void run() {
			while(running) {
				if(moveRequested) {
					moveRequested = false;
					try {
						Thread.sleep(500);
					} catch(InterruptedException e) {
						System.out.println("AI thread failed to sleep:");
						e.printStackTrace();
					}
					int[] board = cloneBoard(((TicTacToeModel) model).getBoard());
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
		int best = minimax(board, playerNumber, "")[0];
		return Move.getFromInt(3, best);
	}
	
	private int[] minimax(int[] board, int playernum, String depth) {
		// adapted from https://github.com/ahmadabdolsaheb/minimaxarticle/blob/master/index.js
		if(checkWin(board, playerNumber)) {
			return new int[] {0, 1};
		} else if(checkWin(board, playerNumber == 2 ? 1 : 2)) {
			return new int[] {0, -1};
		} else if(checkFull(board)) {
			return new int[] {0, 0};
		}
		ArrayList<Integer> moves = new ArrayList<>();
		ArrayList<Integer> spots = new ArrayList<>(); 
		for(int i = 0; i < 9; i++) {
			if(board[i] == 0) {
				board[i] = playernum;
				moves.add(minimax(cloneBoard(board), playernum == 2 ? 1 : 2, depth + "->")[1]);
				spots.add(i);
				board[i] = 0;
			}
		}
		int bestResult;
		int spot = 0;
		if(playernum == playerNumber) {
			bestResult = -9;
			for(int i = 0; i < moves.size(); i++) {
				int move = moves.get(i);
				if(move > bestResult) {
					bestResult = move;
					spot = spots.get(i);
				}
			}
		} else {
			bestResult = 9;
			for(int i = 0; i < moves.size(); i++) {
				int move = moves.get(i);
				if(move < bestResult) {
					bestResult = move;
					spot = spots.get(i);
				}
			}
		}
		return new int[] {spot, bestResult};
	}
	
	private int[] cloneBoard(int[] board) {
		int[] newBoard = new int[board.length];
		for(int i = 0; i < board.length; i++) {
			newBoard[i] = board[i];
		}
		return newBoard;
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
