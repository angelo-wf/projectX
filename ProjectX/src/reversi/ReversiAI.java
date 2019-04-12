package reversi;

import java.util.ArrayList;

import gamehandler.GamePlayer;
import gamehandler.Move;
import gamehandler.Result;

public class ReversiAI extends GamePlayer {
	private boolean running;
	private boolean moveRequested;
	
	public static final int RECURSION_DEPTH = 7;
	// adapted from the weights at https://github.com/arminkz/Reversi/blob/master/src/player/ai/RealtimeEvaluator.java
	public static final int[] BOARD_WEIGHTS = {
			100 , -10 , 8  ,  6 ,  6 , 8  , -10 ,  100,
            -10 , -25 ,  -4, -4 , -4 , -4 , -25 , -10 ,
            8   ,  -4 ,   6,   4,   4,   6,  -4 ,  8  ,
            6   ,  -4 ,   4,   0,   0,   4,  -4 ,  6  ,
            6   ,  -4 ,   4,   0,   0,   4,  -4 ,  6  ,
            8   ,  -4 ,   6,   4,   4,   6,  -4 ,  8  ,
            -10 , -25 ,  -4, -4 , -4 , -4 , -25 , -10 ,
            100 , -10 , 8  ,  6 ,  6 , 8  , -10 ,  100};
	public static final int END_PIECE_WEIGHT = 25; 
	public static final int TIME_LIMIT = 8500;
	private boolean nearingTimeout = false;
	
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
					nearingTimeout = false;
					
					int[] board = ((ReversiModel) model).getBoard().clone();
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
	
	public ReversiAI(int playerNum) {
		super(playerNum);
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
		// AI doesn't do anything with other's moves
	}
	
	private Move getBestMove(int[] board) {
		Thread timeoutThread = new Thread(()-> {
			try {
				Thread.sleep(TIME_LIMIT);
				System.out.println("Nearing Timeout");
				nearingTimeout = true;
			} catch (InterruptedException e) {
//				e.printStackTrace();				
			}
		});	
		
		timeoutThread.start();
		int best = minimax(board, playerNumber, 0, Integer.MIN_VALUE, Integer.MAX_VALUE).getSpot();
		timeoutThread.interrupt();
		return Move.getFromInt(8, best);
	}
	
	private Result minimax(int[] board, int playernum, int depth, int alpha, int beta) {
		if(depth > RECURSION_DEPTH || nearingTimeout) {
			// calculate board state with cell weights and return it
			int total = 0;
			for(int i = 0; i < 64; i++) {
				if(board[i] == playerNumber) {
					total += BOARD_WEIGHTS[i];
				} else if(board[i] != 0) {
					total -= BOARD_WEIGHTS[i];
				}
			}
			return new Result(total, 0);
		}
		
		if(playernum == playerNumber) {
			int highestScore = Integer.MIN_VALUE;
			Result highestMove = null;
			int value = Integer.MIN_VALUE;
			// else, check for all possible moves what the score is
			ArrayList<ArrayList<Move>> possibleMoves = getPossibleMoves(board, playernum);
			for(ArrayList<Move> move : possibleMoves) {
				// for each possible move, apply it, and get the minimax for the other player
				int[] newBoard = board.clone();
				for(Move turn : move) {
					newBoard[turn.getAsInt(8)] = playernum;
				}
				Result res;
				// check if the other player can do a move
				if(getPossibleMoves(newBoard, playernum == 2 ? 1 : 2).size() > 0) {
					// other player can move, get minimax from him
					value = minimax(newBoard, playernum == 2 ? 1 : 2, depth + 1, alpha, beta).getScore();
					res = new Result(value , move.get(move.size() - 1).getAsInt(8));
				} else {
					// get minimax for ourselvses instead
					value = minimax(newBoard, playernum, depth + 1, alpha, beta).getScore();
					res = new Result(value, move.get(move.size() - 1).getAsInt(8));
				}
				if(res.getScore() > highestScore) {
					highestScore = res.getScore();
					highestMove = res;
				}
				alpha = Math.max(alpha, value);
				if(alpha > beta) {
					break;
				}
			}
			if(highestMove == null) {
				// this can only be reached if neither player could move, get weight from pieces on board
				int total = 0;
				for(int i = 0; i < 64; i++) {
					if(board[i] == playerNumber) {
						total += END_PIECE_WEIGHT;
					} else if(board[i] != 0) {
						total -= END_PIECE_WEIGHT;
					}
				}
				return new Result(total, 0);
			}
			return highestMove;
		} else {
			int lowestScore = Integer.MAX_VALUE;
			Result lowestMove = null;
			int value = Integer.MAX_VALUE;
			// else, check for all possible moves what the score is
			ArrayList<ArrayList<Move>> possibleMoves = getPossibleMoves(board, playernum);
			for(ArrayList<Move> move : possibleMoves) {
				// for each possible move, apply it, and get the minimax for the other player
				int[] newBoard = board.clone();
				for(Move turn : move) {
					newBoard[turn.getAsInt(8)] = playernum;
				}
				Result res;
				// check if the other player can do a move
				if(getPossibleMoves(newBoard, playernum == 2 ? 1 : 2).size() > 0) {
					// other player can move, get minimax from him
					value = minimax(newBoard, playernum == 2 ? 1 : 2, depth + 1, alpha, beta).getScore();
					res = new Result(value , move.get(move.size() - 1).getAsInt(8));
				} else {
					value = minimax(newBoard, playernum, depth + 1, alpha, beta).getScore();
					// get minimax for ourselvses instead
					res = new Result(value, move.get(move.size() - 1).getAsInt(8));
				}
				if(res.getScore() < lowestScore) {
					lowestScore = res.getScore();
					lowestMove = res;
				}
				beta = Math.min(beta, value);
				if(alpha > beta) {
					break;
				}
			}
			// check for highestMove == null, but it always is in this loop
			if(lowestMove == null) {
				// this can only be reached if neither player could move, get weight from pieces on board
				int total = 0;
				for(int i = 0; i < 64; i++) {
					if(board[i] == playerNumber) {
						total += END_PIECE_WEIGHT;
					} else if(board[i] != 0) {
						total -= END_PIECE_WEIGHT;
					}
				}
				return new Result(total, 0);
			}
			return lowestMove;
		}
		// else, check for all possible moves what the score is
//		ArrayList<ArrayList<Move>> possibleMoves = getPossibleMoves(board, playernum);
//		for(ArrayList<Move> move : possibleMoves) {
//			// for each possible move, apply it, and get the minimax for the other player
//			int[] newBoard = board.clone();
//			for(Move turn : move) {
//				newBoard[turn.getAsInt(8)] = playernum;
//			}
//			Result res;
//			// check if the other player can do a move
//			if(getPossibleMoves(newBoard, playernum == 2 ? 1 : 2).size() > 0) {
//				// other player can move, get minimax from him
//				res = new Result(minimax(newBoard, playernum == 2 ? 1 : 2, depth + 1).getScore(), move.get(move.size() - 1).getAsInt(8));
//			} else {
//				// get minimax for ourselvses instead
//				res = new Result(minimax(newBoard, playernum, depth + 1).getScore(), move.get(move.size() - 1).getAsInt(8));
//			}
//			if(res.getScore() > highestScore) {
//				highestScore = res.getScore();
//				highestMove = res;
//			}
//			if(res.getScore() < lowestScore) {
//				lowestScore = res.getScore();
//				lowestMove = res;
//			}
//		}
//		if(highestMove == null) {
//			// this can only be reached if neither player could move, get weight from pieces on board
//			int total = 0;
//			for(int i = 0; i < 64; i++) {
//				if(board[i] == playerNumber) {
//					total += END_PIECE_WEIGHT;
//				} else if(board[i] != 0) {
//					total -= END_PIECE_WEIGHT;
//				}
//			}
//			return new Result(total, 0);
//		}
//		if(playernum == playerNumber) {
//			return highestMove;
//		}
//		return lowestMove;
	}
	
	private ArrayList<ArrayList<Move>> getPossibleMoves(int[] board, int playernum) {
		ArrayList<ArrayList<Move>> results = new ArrayList<>();
		for(int i = 0; i < 64; i++) {
			Move move = Move.getFromInt(8, i);
			ArrayList<Move> list = handleTurn(board, move, playernum);
			if(list != null) {
				results.add(list);
			}
		}
		return results;
	}
	
	private ArrayList<Move> handleTurn(int[] board, Move move, int playernum) {
		// if piece is already full, not a valid move
		if(board[move.getAsInt(8)] != 0) {
			return null;
		}
		boolean allow = false;
		ArrayList<Move> changes = new ArrayList<>();
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				// for each of the 8 cells around this cell, if it is out of range, check next cell
				if(move.getX() + i < 0 || move.getX() + i >= 8) {
					continue;
				}
				if(move.getY() + j < 0 || move.getY() + j >= 8) {
					continue;
				}
				Move cell = new Move(move.getX() + i, move.getY() + j);
				// if it is our piece, or empty (also skips middle cell), check next cell
				if((board[cell.getAsInt(8)] == playernum) || board[cell.getAsInt(8)] == 0) {
					continue;
				}
				boolean canDo = false;
				int t = 2;
				// check in each direction from the other player's piece
				while(inRange(move.getX() + (i * t)) && inRange(move.getY() + (j * t))) {
					Move tCell = new Move(move.getX() + (i * t), move.getY() + (j * t));
					// if it is empty, break (move not possible)
					if(board[tCell.getAsInt(8)] == 0) {
						break;
					}
					// else, break but indicate that move is possible
					if(board[tCell.getAsInt(8)] == playernum) {
						canDo = true;
						break;
					}
					t++;
				}
				// if we can move
				if(canDo) {
					int u = 1;
					// add all other player's pieces to the change-list, these get turned
					while(inRange(move.getX() + (i * u)) && inRange(move.getY() + (j * u))) {
						Move tCell = new Move(move.getX() + (i * u), move.getY() + (j * u));
						// if we hit our piece, break
						if(board[tCell.getAsInt(8)] == playernum) {
							break;
						}
						changes.add(tCell);
						u++;
					}
					allow = true;
				}
			}
		}
		// if we could move, add the piece we placed to the change-list
		if(allow) {
			changes.add(move);
			return changes;
		}
		// else return null (couldn't move)
		return null;
	}
	
	private boolean inRange(int val) {
		if(val < 0 || val >= 8) {
			return false;
		}
		return true;
	}
}
