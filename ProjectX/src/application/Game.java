package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Game {

	
	
	
	private StackPane gamePane;
	private Gui gui;
	private ApplicationHandler app;
	private Label turnLabel;
	private Button ffBtn;
	private Button ffBackBtn;
	private Label scoreNumberLabel;
	private Label oppScoreNumberLabel;
	private HBox hBox = new HBox();
	private Pane currentGamePane;
	private Label yourScoreLabel = new Label("");
    private Label oppScoreLabel = new Label("");

	Game(StackPane mainPane, Gui gui, ApplicationHandler app){
		this.gamePane = mainPane;
		this.gui = gui;
		this.app = app;
		
		showBoth(new Pane());
		
	}
	
	public void setGameView(Pane gamePane, String player1name, String player2name, String[] pieceNames) {
		//hBox.getChildren().clear();
		if(currentGamePane != null) {
			hBox.getChildren().remove(currentGamePane);
		}
		yourScoreLabel.setText(player1name + " (" + pieceNames[0] + ") : ");
		oppScoreLabel.setText(player2name + " (" + pieceNames[1] + ") : ");
		currentGamePane = gamePane;
		hBox.getChildren().add(gamePane);
		ffBtn.setVisible(true);
		ffBackBtn.setVisible(false);
	}
	
	
public void updateStats(int yourPoints, int oppPoints, int beurt, String endreason) {
    	
    	Platform.runLater(() -> {
    		scoreNumberLabel.setText("" + yourPoints);
    		oppScoreNumberLabel.setText("" + oppPoints);
    		
    		if(beurt == 1) {
    			turnLabel.setText("It's your turn!");
    		}
    		
			if(beurt==2) {
				turnLabel.setText("Opponent's turn.");
			}
			if(beurt==0) {
				turnLabel.setText(endreason);
				ffBtn.setVisible(false);
				ffBackBtn.setVisible(true);
			}
			
    		
    	});
    }
	
	private void makeStats() {

		//grid1: 
        //hier zitten twee VBoxen in (vBoxLabels en vBoxScores)
        GridPane grid1 = new GridPane();

        grid1.setAlignment(Pos.CENTER);
        grid1.setPadding(new Insets(15));
        grid1.setMinWidth(400);
        grid1.setMaxWidth(400);  
        
        VBox vBoxLabels = new VBox();
        VBox vBoxScores = new VBox();
        
        //labels for stats screen
        Label statLabel = new Label("Stats");
        turnLabel = new Label();
        
        //Button
        ffBtn = new Button("Forfeit");
        ffBackBtn = new Button("Back");        
        //styles
        statLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        yourScoreLabel.setFont(Font.font("Verdana", 20));
        oppScoreLabel.setFont(Font.font("Verdana", 20));
        turnLabel.setFont(Font.font("Verdana", 20));
        turnLabel.setTextFill(Color.GREEN);
        turnLabel.setStyle("-fx-padding: 100 0 0 0;");

        ffBtn.setFont(Font.font("Verdana", 20));
        ffBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
        		"    -fx-background-radius: 30;\r\n" + 
        		"    -fx-background-insets: 0;\r\n" + 
        		"    -fx-text-fill: white;");
                
        //Button interactie en stijl
        ffBtn.setOnMouseEntered(e -> ffBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
        		"    -fx-background-radius: 30;\r\n" + 
        		"    -fx-background-insets: 0;\r\n" + 
        		"    -fx-text-fill: white; -fx-opacity: 0.5;"));
                
        ffBtn.setOnMouseExited(e -> ffBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
        		"    -fx-background-radius: 30;\r\n" + 
        		"    -fx-background-insets: 0;\r\n" + 
        		"    -fx-text-fill: white;"));
        
        ffBackBtn.setFont(Font.font("Verdana", 20));
        ffBackBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
        		"    -fx-background-radius: 30;\r\n" + 
        		"    -fx-background-insets: 0;\r\n" + 
        		"    -fx-text-fill: white;");
                
        //Button interactie en stijl
        ffBackBtn.setOnMouseEntered(e -> ffBackBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
        		"    -fx-background-radius: 30;\r\n" + 
        		"    -fx-background-insets: 0;\r\n" + 
        		"    -fx-text-fill: white; -fx-opacity: 0.5;"));
                
        ffBackBtn.setOnMouseExited(e -> ffBackBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
        		"    -fx-background-radius: 30;\r\n" + 
        		"    -fx-background-insets: 0;\r\n" + 
        		"    -fx-text-fill: white;"));

        ffBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                
            	if(gui.getOnline()) {
                	app.forfeit();
            	}
            	else {
            		app.localForfeit();
            	}
            }
        });
        
        ffBackBtn.setVisible(false);
        
        ffBackBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {            	
            	if(gui.getOnline()) {
                	gui.gameToLobby();
            	}
            	else {
                	gui.gameToLocalLobby();
            	}
            }
        });
        
        //labels for stats screen
        scoreNumberLabel = new Label();
        oppScoreNumberLabel = new Label();

        //styles
        scoreNumberLabel.setFont(Font.font("Verdana", 20));
        oppScoreNumberLabel.setFont(Font.font("Verdana", 20));
        scoreNumberLabel.setStyle("-fx-padding:18 0 0 0");

        //vboxes op de grid (zodat het in het midden zit)
        grid1.add(vBoxLabels, 0, 0);
        grid1.add(vBoxScores, 1, 0);
        
        //vul vboxes
        vBoxLabels.getChildren().addAll(statLabel, yourScoreLabel, oppScoreLabel, turnLabel, ffBtn, ffBackBtn);
        vBoxScores.getChildren().addAll(scoreNumberLabel, oppScoreNumberLabel);
        //hBox.getChildren().clear();
        hBox.getChildren().add(grid1);
	}
	
	private void makeGame(Pane board) {
        hBox.getChildren().add(board);
   }
	
	private void showBoth(Pane gameview) {
    	gamePane.getChildren().clear();
    	makeStats();
    	makeGame(gameview);
    	gamePane.getChildren().add(hBox);
    }
	
	public void setInvisible() {
		gamePane.setVisible(false);
	}
	
	public void setVisible() {
		gamePane.setVisible(true);		
	}
}
