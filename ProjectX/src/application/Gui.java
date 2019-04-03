package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Gui extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    
    HBox hBox = new HBox();
    String nameUser;
    String nameServer;
    
    public void makeStats() {

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
        Label yourScoreLabel = new Label("Your score: ");
        Label oppScoreLabel = new Label("Opponent's score: ");
        Label turnLabel = new Label("It's your turn!");
        Label timeLabel = new Label("8.32 sec left");
        
        
        //Button
        Button ffBtn = new Button("Forfeit");
        
        
        //styles
        statLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        yourScoreLabel.setFont(Font.font("Verdana", 20));
        oppScoreLabel.setFont(Font.font("Verdana", 20));
        turnLabel.setFont(Font.font("Verdana", 20));
        turnLabel.setTextFill(Color.GREEN);
        turnLabel.setStyle("-fx-padding: 100 0 0 0;");
        timeLabel.setFont(Font.font("Verdana", 20));
        timeLabel.setStyle("-fx-padding: 0 0 100 0;");
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

        ffBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("btn werkt");
            }
        });
        
        
        //labels for stats screen
        Label scoreNumberLabel = new Label("30");
        Label oppScoreNumberLabel = new Label("27");

        
        //styles
        scoreNumberLabel.setFont(Font.font("Verdana", 20));
        oppScoreNumberLabel.setFont(Font.font("Verdana", 20));
        scoreNumberLabel.setStyle("-fx-padding:18 0 0 0");

        //vboxes op de grid (zodat het in het midden zit)
        grid1.add(vBoxLabels, 0, 0);
        grid1.add(vBoxScores, 1, 0);
        
        //vul vboxes
        vBoxLabels.getChildren().addAll(statLabel, yourScoreLabel, oppScoreLabel, turnLabel, timeLabel, ffBtn);
        vBoxScores.getChildren().addAll(scoreNumberLabel, oppScoreNumberLabel);
        
        hBox.getChildren().add(grid1);
    }
    
    
    public void makeGame() {
    	 GridPane grid2 = new GridPane();
         grid2.setAlignment(Pos.CENTER);
         grid2.setPadding(new Insets(15));
         grid2.setMinWidth(400);
         grid2.setMaxWidth(400);
         
         
         grid2.setStyle("-fx-background-color: #C0C0C0;");
         hBox.getChildren().add(grid2);
    }
    
    public void showBoth(StackPane root) {
    	makeStats();
    	makeGame();
    	root.getChildren().add(hBox);
    }
    
    public void makeLogin(StackPane root) {
    	
    	
    	
    	
    	
    	ObservableList<String> options = 
    		    FXCollections.observableArrayList(
    		        "Online",
    		        "Local"
    		    );
    	
    	GridPane loginGrid = new GridPane();
    	loginGrid.setAlignment(Pos.CENTER);
    	loginGrid.setPadding(new Insets(15));
        
    	
        Label loginName = new Label("Username: ");
        Label serverName = new Label("Server: ");
        Label nameError = new Label("name required");
        Label serverError = new Label("server required");
        nameError.setId("inputError");
        serverError.setId("inputError");
        
        TextField loginInput = new TextField();
        TextField serverInput = new TextField();
        
        
        if(nameUser != null) {
        	loginInput.setText(nameUser);
        }
        if(nameServer != null) {
        	serverInput.setText(nameServer);
        }
    	
        
        ComboBox<String> selectMode = new ComboBox(options);
        selectMode.getSelectionModel().selectFirst();

        selectMode.setOnAction(e -> {
            String selectedMode = selectMode.getSelectionModel().getSelectedItem();
            
            if(selectedMode == "Local") {
                serverName.setVisible(false);
                serverInput.setVisible(false);
                serverError.setVisible(false);
            }
            
            if(selectedMode == "Online") {
            	serverName.setVisible(true);
                serverInput.setVisible(true);
                serverError.setVisible(true);
            }
        });
        
        
        
        
        
    	Button enterButton = new Button("Enter lobby");
        
    	loginGrid.add(loginName, 0, 1);
    	loginGrid.add(loginInput, 0, 2);
    	loginGrid.add(serverName, 0, 3);
    	loginGrid.add(serverInput, 0, 4);
    	
    	loginGrid.add(selectMode, 0, 0);
    	loginGrid.add(enterButton, 0, 5);
    	
    	
        enterButton.setOnAction(e -> {
        	nameUser = loginInput.getText();
        	nameServer = serverInput.getText();
        	Boolean canLogin = false;
        	
        	
        	String selectedMode1 = selectMode.getSelectionModel().getSelectedItem();
  	
        	if(selectedMode1.equals("Online")) {
        		if(nameUser.equals("") | nameUser == null) {
            		loginGrid.add(nameError, 1, 2);
            	}
            	if(nameServer.equals("") | nameServer == null) {
            		loginGrid.add(serverError, 1, 4);
            	}
            	else {
            		canLogin = true;
            	}
        	}
        	if(selectedMode1.equals("Local"))  {
        		if(nameUser.equals("") | nameUser == null) {
            		loginGrid.add(nameError, 1, 2);
            	}
            	else {
            		canLogin = true;
            	}
        	}
        	
        	
        	
	        	if(canLogin == true) {
	        		makeLobby(root);
	            	root.getChildren().remove(loginGrid);
	        	}
        	
        });
    	
    	
    	
//    	enterButton.setStyle("-fx-background-color: linear-gradient(#15b700, #75ff63);\r\n" + 
//    			"    -fx-background-radius: 30;\r\n" + 
//    			"    -fx-background-insets: 0;\r\n" + 
//    			"    -fx-text-fill: white;");
//    	
    	
    	enterButton.setId("record-sales");
    	
    	//selectMode.setStyle()
    	
    	loginGrid.setHgap(10);
    	loginGrid.setVgap(10);
    	loginGrid.setPadding(new Insets(10, 10, 10, 10));
    	
    	root.getChildren().add(loginGrid);



    }
    
    
public void makeLobby(StackPane root) {
		ToolBar toolbar = new ToolBar();
    	GridPane lobbyGrid = new GridPane();
    	
    	lobbyGrid.setAlignment(Pos.CENTER);
    	lobbyGrid.setPadding(new Insets(15));

    	Label playerLobbyLabel = new Label("Player");
    	Label gameLobbyLabel = new Label("Status");
    	Label lobbyUsernameLabel = new Label("Logged in as " + nameUser + "  ");
    	Label lobbyServerLabel = new Label("  server: " + nameServer);
    	lobbyUsernameLabel.setId("toolbar-content");
    	lobbyServerLabel.setId("toolbar-content");

    	Button backToLoginBtn = new Button("Back");
    	backToLoginBtn.setId("back-button");
    	
    	Button refreshBtn = new Button("refresh");
    	refreshBtn.setId("refresh-button");
    	
    	Separator separator1 = new Separator();
    	separator1.setOrientation(Orientation.VERTICAL);
    	
    	Separator separator2 = new Separator();
    	separator2.setOrientation(Orientation.VERTICAL);
    	
    	lobbyGrid.add(refreshBtn, 3, 1);
    	toolbar.getItems().add(backToLoginBtn);
    	toolbar.getItems().add(separator2);
    	toolbar.getItems().add(lobbyUsernameLabel);
    	toolbar.getItems().add(separator1);
    	toolbar.getItems().add(lobbyServerLabel);
    	lobbyGrid.add(playerLobbyLabel, 0, 1);
    	lobbyGrid.add(gameLobbyLabel, 1, 1);
    	VBox toolbarbox = new VBox();
    	toolbarbox.getChildren().add(toolbar);
    	//root.getChildren().add(lobbyGrid);

    	toolbarbox.getChildren().add(lobbyGrid);
    	
    	
    	root.getChildren().add(toolbarbox);

    	
    	
        playerLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        gameLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        playerLobbyLabel.setId("label-big");
        gameLobbyLabel.setId("label-big");
    	
    	ArrayList<Player> playerArray = new ArrayList<Player>();
    	
    	//dummy speler:
    	
    
    	
    	Player player1 = new Player("player123", "none", false);
    	Player player2 = new Player("speler123132", "none", false);
    	Player player3 = new Player("hallo23", "Invited you!", true);
    	
    	playerArray.add(player1);
    	playerArray.add(player2);
    	playerArray.add(player3);
    	
    	int aantalSpelers = playerArray.size();
    	
    	for (int r = 0; r < aantalSpelers; r++) {
            //for (int c = 0; c < 3; c++) {
            	Player currentPlayer = playerArray.get(r);
            	lobbyGrid.add(new Label(currentPlayer.getName()), 0, r+2);
            	
            	if(currentPlayer.invited() == true) {
                	//lobbyGrid.add(new Label(currentPlayer.getStatus()), 1, r+1);
                	lobbyGrid.add(new Label("You got invited!"), 1, r+2);
                	lobbyGrid.add(new Button("Accept"), 2, r+2);
                	lobbyGrid.add(new Button("Decline"), 3, r+2);
                	
                	
            	}
            //}
}
    	backToLoginBtn.setOnAction(e -> {
        	makeLogin(root);
        	root.getChildren().remove(lobbyGrid);
        	root.getChildren().remove(toolbarbox);
        });
    	
    	lobbyGrid.setHgap(10);
    	lobbyGrid.setVgap(10);
    	lobbyGrid.setPadding(new Insets(10, 10, 10, 10));

    }
    
    @Override
    public void start(Stage primaryStage) {
        
    	
    
    	
    	primaryStage.setTitle("Game");
        
       
        
       
        
        
    	StackPane root = new StackPane();
    	//showBoth(root);
        makeLogin(root);
        //makeLobby(root);
        
        
        Scene scene = new Scene(root, 800, 400);

        //scene.getStylesheets().add(css);
        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}