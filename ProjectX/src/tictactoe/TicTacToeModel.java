package tictactoe;

import gamehandler.EndReason;
import gamehandler.GameModel;
import gamehandler.Move;
import gamehandler.Turn;

public class TicTacToeModel extends GameModel {
	
	private int[] board;
	private Turn turn;
	private EndReason reason;
	
	public TicTacToeModel() {
		board = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		turn = Turn.PLAYER1;
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
		board[move.getAsInt(3)] = turn.getPieceNum();
		if(checkWin()) {
			if(turn == Turn.PLAYER2) {
				endGame(EndReason.WIN2);
			} else {
				endGame(EndReason.WIN1);
			}
			return true;
		} else if(checkFull()) {
			endGame(EndReason.DRAW);
			return true;
		}
		if(turn == Turn.PLAYER2) {
			turn = Turn.PLAYER1;
			player1.requestMove(move);
		} else {
			turn = Turn.PLAYER2;
			player2.requestMove(move);
		}
		view.update();
		return true;
	}

	@Override
	public void endGame(EndReason reason) {
		player1.endGame();
		player2.endGame();
		turn = Turn.ENDED;
		this.reason = reason;
		view.update();
	}
	
	public int[] getBoard() {
		return board;
	}
	
	public String getStateString() {
		if(turn == Turn.ENDED) {
			return turn.getNiceString() + ", " + reason.getNiceString();
		}
		return turn.getNiceString();
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
