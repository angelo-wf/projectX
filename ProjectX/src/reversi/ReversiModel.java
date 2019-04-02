package reversi;

import java.util.ArrayList;

import gamehandler.EndReason;
import gamehandler.GameModel;
import gamehandler.Move;
import gamehandler.Turn;

public class ReversiModel extends GameModel {
	
	private int[] board;
	private Turn turn;
	private EndReason reason;
	
	public ReversiModel() {
		board = new int[64];
		turn = Turn.PLAYER1;
		board[8 * 3 + 3] = Turn.PLAYER2.getPieceNum();
		board[8 * 3 + 4] = Turn.PLAYER1.getPieceNum();
		board[8 * 4 + 3] = Turn.PLAYER1.getPieceNum();
		board[8 * 4 + 4] = Turn.PLAYER2.getPieceNum();
	}
	
	@Override
	public void initGame() {
		// TODO: get starting player from server
		view.update();
		player1.requestMove(null);
	}

	@Override
	public boolean doMove(Move move) {
		ArrayList<Move> moves = handleTurn(move, turn);
		if(moves == null) {
			return false;
		}
		System.out.println("Move done: " + move.getAsInt(8));
		for(Move m : moves) {
			board[m.getAsInt(8)] = turn.getPieceNum();
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
	
	private ArrayList<Move> handleTurn(Move move, Turn turn) {
		if(board[move.getAsInt(8)] != 0) {
			return null;
		}
		boolean allow = false;
		ArrayList<Move> changes = new ArrayList<>();
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(move.getX() + i < 0 || move.getX() + i >= 8) {
					continue;
				}
				if(move.getY() + j < 0 || move.getY() + j >= 8) {
					continue;
				}
				Move cell = new Move(move.getX() + i, move.getY() + j);
				if((board[cell.getAsInt(8)] == turn.getPieceNum()) || board[cell.getAsInt(8)] == 0) {
					continue;
				}
				boolean canDo = false;
				int t = 2;
				while(inRange(move.getX() + (i * t)) && inRange(move.getY() + (j * t))) {
					Move tCell = new Move(move.getX() + (i * t), move.getY() + (j * t));
					if(board[tCell.getAsInt(8)] == 0) {
						break;
					}
					if(board[tCell.getAsInt(8)] == turn.getPieceNum()) {
						canDo = true;
						break;
					}
					t++;
				}
				if(canDo) {
					int u = 1;
					while(inRange(move.getX() + (i * u)) && inRange(move.getY() + (j * u))) {
						Move tCell = new Move(move.getX() + (i * u), move.getY() + (j * u));
						if(board[tCell.getAsInt(8)] == turn.getPieceNum()) {
							break;
						}
						changes.add(tCell);
						u++;
					}
					allow = true;
				}
			}
		}
		if(allow) {
			changes.add(move);
			return changes;
		}
		return null;
	}
	
	private boolean inRange(int val) {
		if(val < 0 || val >= 8) {
			return false;
		}
		return true;
	}

}
