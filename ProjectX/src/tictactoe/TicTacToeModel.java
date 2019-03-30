package tictactoe;

import gamehandler.EndReason;
import gamehandler.GameModel;
import gamehandler.Move;

public class TicTacToeModel extends GameModel {
	
	private int[] board;
	private int turn;
	private EndReason reason;
	
	public TicTacToeModel() {
		board = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		turn = 0;
	}
	
	@Override
	public void initGame() {
		view.update();
		player1.requestMove(null);
	}
	
	@Override
	public synchronized boolean doMove(Move move) {
		if(board[move.getAsInt(3)] != 0) {
			return false;
		}
		board[move.getAsInt(3)] = turn + 1;
		if(checkWin()) {
			if(turn == 1) {
				endGame(EndReason.WIN2);
			} else {
				endGame(EndReason.WIN1);
			}
			return true;
		} else if(checkFull()) {
			endGame(EndReason.DRAW);
			return true;
		}
		if(turn == 1) {
			turn = 0;
			player1.requestMove(move);
		} else {
			turn = 1;
			player2.requestMove(move);
		}
		view.update();
		return true;
	}

	@Override
	public void endGame(EndReason reason) {
		player1.endGame();
		player2.endGame();
		turn = 2;
		this.reason = reason;
		view.update();
	}
	
	public int[] getBoard() {
		return board;
	}
	
	public String getStateString() {
		switch(turn) {
		case 0:
			return "Player's 1 turn";
		case 1:
			return "Player's 2 turn";
		case 2:
			return "Game ended, " + reason.getNiceString();
		default:
			return "Unknown turn state: " + turn;
		}
	}
	
	private boolean checkWin() {
		if(board[0] == board[1] && board[1] == board[2] && board[0] != 0) {
			return true;
		}
		if(board[3] == board[4] && board[4] == board[5] && board[3] != 0) {
			return true;
		}
		if(board[6] == board[7] && board[7] == board[8] && board[6] != 0) {
			return true;
		}
		if(board[0] == board[3] && board[3] == board[6] && board[0] != 0) {
			return true;
		}
		if(board[1] == board[4] && board[4] == board[7] && board[1] != 0) {
			return true;
		}
		if(board[2] == board[5] && board[5] == board[8] && board[2] != 0) {
			return true;
		}
		if(board[0] == board[4] && board[4] == board[8] && board[0] != 0) {
			return true;
		}
		if(board[2] == board[4] && board[4] == board[6] && board[2] != 0) {
			return true;
		}
		return false;
	}
	
	private boolean checkFull() {
		boolean full = true;
		for(int i = 0; i < board.length; i++) {
			if(board[i] == 0) {
				full = false;
			}
		}
		return full;
	}

}
