package reversi;

import gamehandler.GamePlayer;
import gamehandler.Move;

import java.util.ArrayList;

public abstract class ReversiAIAB extends GamePlayer {
	private boolean running;
	private boolean moveRequested;

	public static final int RECURSION_DEPTH = 5;
	// adapted from the weights at https://github.com/arminkz/Reversi/blob/master/src/player/ai/RealtimeEvaluator.java
	public static final int[] BOARD_WEIGHTS = {
			100, 3, 20, 12, 12, 20, 3, 100,
			3,   1, 6,  6,  6,  6,  1, 3,
			20,  6, 12, 10, 10, 12, 6, 20,
			12,  6, 10, 8,  8,  10, 6, 12,
			12,  6, 10, 8,  8,  10, 6, 12,
			20,  6, 12, 10, 10, 12, 6, 20,
			3,   1, 6,  6,  6,  6,  1, 3,
			100, 3, 20, 12, 12, 20, 3, 100
	};

	class AiThread implements Runnable {
		public void run() {
			while (running) {
				if (moveRequested) {
					moveRequested = false;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						System.out.println("AI thread failed to sleep:");
						e.printStackTrace();
					}
					int[] board = ((ReversiModel) model).getBoard().clone();
					Move move = getBestMove(board);
					model.doMove(move);
				} else {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						System.out.println("AI thread failed to sleep:");
						e.printStackTrace();
					}
				}
			}
		}
	}

	public ReversiAIAB(int playerNum) {
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
	public boolean gameOver() {
		return false;
	}

	@Override
	public int getBoardValue() {
		return 0;
	}

	@Override
	public void tellMove(Move move) {
		// AI doesn't do anything with other's moves
	}

	private Move getBestMove(int[] board) {
		int best = new minimax(board,playerNumber);
		return Move.getFromInt(8, best);
	}

	private class Result {

		private int score;
		private int spot;

		public Result(int score, int spot) {
			this.score = score;
			this.spot = spot;
		}

		public int getScore() {
			return score;
		}

		public int getSpot() {
			return spot;
		}
	}
		private int minValue(int[] board, int playernum, int depth, int alpha, int beta) {
			int legalMoveCounter = 0;
			if (gameOver() || RECURSION_DEPTH == 0) {
				return getBoardValue();
			} else {
				int total = 0;
				// calculate board state with cell weights and return it
				for (int i = 0; i < 64; i++) {
					if (board[i] == playerNumber) {
						total += BOARD_WEIGHTS[i];
						legalMoveCounter++;
					} else if (board[i] != 0) {
						total -= BOARD_WEIGHTS[i];
						legalMoveCounter++;
						int g = maxValue(board, playernum == 2 ? 1 : 2, depth - 1, alpha, beta);
						if (g <= alpha) {
							return g;
						}
						if (g < beta) {
							beta = g;
						}
					}
				}
				if (legalMoveCounter == 0) {
					alpha = maxValue(board, playernum == 2 ? 1 : 2, depth - 1, alpha, beta);
				}
				return Integer.max(alpha,beta);
			}
		}

		private int maxValue(int[] board, int playernum, int depth, int alpha, int beta) {
			int legalMoveCounter = 0;
			if (gameOver() || RECURSION_DEPTH == 0) {
				return getBoardValue();
			} else {
				int total = 0;
				// calculate board state with cell weights and return it
				for (int i = 0; i < 64; i++) {
					if (board[i] == playerNumber) {
						total += BOARD_WEIGHTS[i];
						legalMoveCounter++;
					} else if (board[i] != 0) {
						total -= BOARD_WEIGHTS[i];
						legalMoveCounter++;
						int g = minValue(board, playernum == 2 ? 1 : 2, depth - 1, alpha, beta);
						if (g >= beta) {
							return g;
						}
						if (g > alpha) {
							alpha = g;
						}
					}
				}
				if (legalMoveCounter == 0) {
					alpha = minValue(board, playernum == 2 ? 1 : 2, depth - 1, alpha, beta);
				}
				return Integer.min(alpha,beta);
			}

			private void makeBestMoveForMin(int[] board, int playernum, int depth, int alpha, int beta){
				int bestRow = -1;
				int bestCol = -1;
				for (int r = 0; r < 8 && r >= 0; r++) {
					for (int c = 0; c < 8 && c >= 0; c++) {
						if (makeMove(r, c)){
							int v = maxValue(board, playernum == 2 ? 1 : 2, depth - 1, alpha, beta);
							undoMove();
							if (v < beta) {
								bestRow = r;
								bestCol = c;
							}
						}
					}
				}
				if (!(bestRow == -1 || bestCol == -1)) {
					new Move(bestRow, bestCol);
				}
			} // end makeBestMoveForMin()

			private void makeBestMoveForMax(int[] board, int playernum, int depth, int alpha, int beta){
				int bestRow = -1;
				int bestCol = -1;
				for (int r = 0; r < 8 && r >= 0; r++) {
					for (int c = 0; c < 8 && c >= 0; c++) {
						if (makeMove(r, c)) {
							double v = minValue(board, playernum == 2 ? 1 : 2, depth - 1, alpha, beta);
							undoMove();
							if (v > alpha) {
								bestRow = r;
								bestCol = c;
							}
						}
					}
				}
				if (!(bestRow == -1 || bestCol == -1)) {
					new Move(bestRow, bestCol);
				}
			} // end makeBestMoveForMax()

			int highestScore = Integer.max(alpha,beta);
			Result highestMove = null;
			int lowestScore = Integer.min(alpha,beta);
			Result lowestMove = null;
			// else, check for all possible moves what the score is
			ArrayList<ArrayList<Move>> possibleMoves = getPossibleMoves(board, playernum);
			for (ArrayList<Move> move : possibleMoves) {
				// for each possible move, apply it, and get the minimax for the other player
				int[] newBoard = board.clone();
				for (Move turn : move) {
					newBoard[turn.getAsInt(8)] = playernum;
				}
				Result res = new Result(minValue(newBoard, playernum == 2 ? 1 : 2, depth + 1, alpha, beta).getScore(), move.get(move.size() - 1).getAsInt(8));
				if (res.getScore() > highestScore) {
					highestScore = res.getScore();
					highestMove = res;
				}
				if (res.getScore() < lowestScore) {
					lowestScore = res.getScore();
					lowestMove = res;
				}
			}
			if (highestMove == null) {
				// the player couldn't do a move
				// assume as being bad
				// TODO: handle properly
				return new Result(0, 0);
			}
			if (playernum == playerNumber) {
				return highestMove;
			}
			return lowestMove;
		}

		private ArrayList<ArrayList<Move>> getPossibleMoves(int[] board, int playernum) {
			ArrayList<ArrayList<Move>> results = new ArrayList<>();
			for (int i = 0; i < 64; i++) {
				Move move = Move.getFromInt(8, i);
				ArrayList<Move> list = handleTurn(board, move, playernum);
				if (list != null) {
					results.add(list);
				}
			}
			return results;
		}

		private ArrayList<Move> handleTurn(int[] board, Move move, int playernum) {
			// if piece is already full, not a valid move
			if (board[move.getAsInt(8)] != 0) {
				return null;
			}
			boolean allow = false;
			ArrayList<Move> changes = new ArrayList<>();
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					// for each of the 8 cells around this cell, if it is out of range, check next cell
					if (move.getX() + i < 0 || move.getX() + i >= 8) {
						continue;
					}
					if (move.getY() + j < 0 || move.getY() + j >= 8) {
						continue;
					}
					Move cell = new Move(move.getX() + i, move.getY() + j);
					// if it is our piece, or empty (also skips middle cell), check next cell
					if ((board[cell.getAsInt(8)] == playernum) || board[cell.getAsInt(8)] == 0) {
						continue;
					}
					boolean canDo = false;
					int t = 2;
					// check in each direction from the other player's piece
					while (inRange(move.getX() + (i * t)) && inRange(move.getY() + (j * t))) {
						Move tCell = new Move(move.getX() + (i * t), move.getY() + (j * t));
						// if it is empty, break (move not possible)
						if (board[tCell.getAsInt(8)] == 0) {
							break;
						}
						// else, break but indicate that move is possible
						if (board[tCell.getAsInt(8)] == playernum) {
							canDo = true;
							break;
						}
						t++;
					}
					// if we can move
					if (canDo) {
						int u = 1;
						// add all other player's pieces to the change-list, these get turned
						while (inRange(move.getX() + (i * u)) && inRange(move.getY() + (j * u))) {
							Move tCell = new Move(move.getX() + (i * u), move.getY() + (j * u));
							// if we hit our piece, break
							if (board[tCell.getAsInt(8)] == playernum) {
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
			if (allow) {
				changes.add(move);
				return changes;
			}
			// else return null (couldn't move)
			return null;
		}

		private boolean inRange(int val) {
			if (val < 0 || val >= 8) {
				return false;
			}
			return true;
		}

		private boolean makeMove(int row, int col) {
			return false;
		}

		public void undoMove() {

		}

}
