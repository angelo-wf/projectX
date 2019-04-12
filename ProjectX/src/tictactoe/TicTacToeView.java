package tictactoe;

import application.Gui;
import gamehandler.GameView;
import gamehandler.Move;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TicTacToeView extends GameView {
	
	private ImageView[] cells;
	private Label stats;
	private Image empty;
	private Image xImg;
	private Image oImg;
	
	public TicTacToeView(Gui gui) {
		super(gui);
		empty = new Image("assets/tictactoe_empty.png");
		xImg = new Image("assets/tictactoe_x.png");
		oImg = new Image("assets/tictactoe_o.png");
		
		boardView = new GridPane();
		boardView.setPrefSize(384, 384);
		cells = new ImageView[9];
		for(int i = 0; i < cells.length; i++) {
			final int x = i % 3;
			final int y = i / 3;
			cells[i] = new ImageView(empty);
			cells[i].setOnMouseClicked(e -> {
				handleClick(new Move(x, y));
			});
			((GridPane) boardView).add(cells[i], x, y);
		}
		stats = new Label("Stats");
		statsPane = new Pane();
		statsPane.getChildren().add(stats);
	}
	
	@Override
	public void update() {
		Platform.runLater(() -> {
			updateBoard();
		});
		int[] values = ((TicTacToeModel) model).getStats();
		String reason = ((TicTacToeModel) model).getEndReason();
		gui.updateStats(values[0], values[1], values[2], reason);
		
	}
	
	private void updateBoard() {
		int[] board = ((TicTacToeModel) model).getBoard();
		
		for(int i = 0; i < board.length; i++) {
			int value = board[i];
			if(value == 1) {
				cells[i].setImage(xImg);
			} else if(value == 2) {
				cells[i].setImage(oImg);
			} else {
				cells[i].setImage(empty);
			}
		}
	}

	@Override
	public String[] getPieceNames() {
		return new String[] {"X", "O"};
	}

}
