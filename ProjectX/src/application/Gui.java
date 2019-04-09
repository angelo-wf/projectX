package application;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

import gamehandler.GameView;

public class Gui {
        
    HBox hBox = new HBox();
    String nameUser;
    String nameServer;
    ApplicationHandler app;
	private GridPane lobbyGrid;
	//private ArrayList<String> playerArray;
	private ArrayList<String> playerArrayList;
	private VBox toolbarbox;
	private GridPane inviteGrid;
	private ArrayList<Player> inviteArrayList = new ArrayList<>();
	private String currentGame = "Reversi";
	private ArrayList<String> challengedPlayers = null;
   	StackPane root = new StackPane();
	HBox drieBox = new HBox();
	private Label scoreNumberLabel;
	private Label oppScoreNumberLabel;
	private Label timeLabel;
	private Label turnLabel;

    
    public Gui(Stage primaryStage, ApplicationHandler app) {
    	this.app = app;
    	primaryStage.setTitle("Game");
    	//showBoth(root);
        makeLogin(root);
        //makeLobby(root);
        
        Scene scene = new Scene(root, 800, 400);

        //scene.getStylesheets().add(css);
        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
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
        turnLabel = new Label();
        timeLabel = new Label("8.32 sec left");
        
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
        vBoxLabels.getChildren().addAll(statLabel, yourScoreLabel, oppScoreLabel, turnLabel, timeLabel, ffBtn);
        vBoxScores.getChildren().addAll(scoreNumberLabel, oppScoreNumberLabel);
        
        hBox.getChildren().add(grid1);
    }
    
    public void updateStats(int yourPoints, int oppPoints, int beurt) {
    	
    	Platform.runLater(() -> {
    		scoreNumberLabel.setText("" + yourPoints);
    		oppScoreNumberLabel.setText("" + oppPoints);
    		
    		if(beurt == 1) {
    			turnLabel.setText("> It's your turn! <");
    		}
    		else {
    			turnLabel.setText("opponent's turn");
    		}
    		//timeLabel.setText(secondenOver + "seconds left!");
    	});
    }
    
    
    
    public void makeGame(Pane board) {
    	 //GridPane grid2 = new GridPane();
         //grid2.setAlignment(Pos.CENTER);
         //grid2.setPadding(new Insets(15));
         //grid2.setMinWidth(400);
         //grid2.setMaxWidth(400);
         
         //grid2.add(board);
         //grid2.setStyle("-fx-background-color: #C0C0C0;");
         hBox.getChildren().add(board);
    }
    
    public void showBoth(StackPane root, GameView gameview) {
    	makeStats();
    	makeGame(gameview.getBoardView());
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
        Label nameErrorInvisible = new Label("name required");
        Label serverErrorInvisible = new Label("server required");
        
        nameErrorInvisible.setVisible(false);
        serverErrorInvisible.setVisible(false);

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
                serverError.setVisible(false);
                nameError.setVisible(false);
                
            }
        });
        
    	Button enterButton = new Button("Enter lobby");
        
    	loginGrid.add(loginName, 1, 1);
    	loginGrid.add(loginInput, 1, 2);
    	loginGrid.add(serverName, 1, 3);
    	loginGrid.add(serverInput, 1, 4);
    	
    	loginGrid.add(selectMode, 1, 0);
    	loginGrid.add(enterButton, 1, 5);
    	
    	loginGrid.add(nameErrorInvisible, 0, 2);
    	loginGrid.add(serverErrorInvisible, 0, 2);
    	
		loginGrid.add(nameError, 2, 2);
		loginGrid.add(serverError, 2, 4);
		serverError.setVisible(false);
		nameError.setVisible(false);
	
        enterButton.setOnAction(e -> {
        	nameUser = loginInput.getText();
        	nameServer = serverInput.getText();
        	Boolean userGoed = false;
        	Boolean serverGoed = false;
        	Boolean canLogin = false;
        	
        	String selectedMode1 = selectMode.getSelectionModel().getSelectedItem();
  	
        	if(selectedMode1.equals("Online")) {
        		if(nameUser.equals("") | nameUser == null) {
        			nameError.setVisible(true);
            	}
        		else {
        			nameError.setVisible(false);
        			userGoed=true;
        		}
            	if(nameServer.equals("") | nameServer == null) {
        			serverError.setVisible(true);
            	}
            	else {
            		serverError.setVisible(false);
            		serverGoed = true;
            	}
            	if(userGoed == true && serverGoed == true) {
            		canLogin = true;
            	}
        	}
//        	else {
//        		canLogin = true;
//        	}
        	if(selectedMode1.equals("Local")) {
        		serverError.setVisible(false);
        		nameServer = "";
        		if(nameUser.equals("") | nameUser == null) {
        			nameError.setVisible(true);
            	}
            	else {
            		nameError.setVisible(false);
            		canLogin = true;
            	}
        		
        	}
        	
        	
        	
	        	if(canLogin == true && selectedMode1 == "Online") {
	        		app.setServer(nameUser, nameServer);
	            	root.getChildren().remove(loginGrid);

	        		makeLobby(root);

	        		app.requestPlayerList();
	        	}
	        	if(canLogin == true && selectedMode1 == "Local") {
	        		makeLocalLobby(root);
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
    
    public void setPlayerList(ArrayList<String> playerArray) {
    	this.playerArrayList = playerArray;
    	
    	//lobbyGrid = new GridPane();
    	
    	//dummy speler:
//    	Player player1 = new Player("player123", "none", false);
//    	Player player2 = new Player("speler123132", "none", false);
//    	Player player3 = new Player("hallo23", "Invited you!", true);
//    	
//    	playerArray.add(player1);
//    	playerArray.add(player2);
//    	playerArray.add(player3);
    	
    	//int aantalSpelers = playerArrayList.size();
    	
//    	for (int r = 0; r < aantalSpelers; r++) {
//            //for (int c = 0; c < 3; c++) {
//            	//Player currentPlayer = playerArrayList.get(r);
//            	//lobbyGrid.add(new Label(currentPlayer.getName()), 0, r+2);
//            	
//            	if(currentPlayer.invited() == true) {
//                	//lobbyGrid.add(new Label(currentPlayer.getStatus()), 1, r+1);
//                	lobbyGrid.add(new Label("You got invited!"), 1, r+2);
//                	lobbyGrid.add(new Button("Accept"), 2, r+2);
//                	lobbyGrid.add(new Button("Decline"), 3, r+2);        	
//            	}
//            //}
//}

    	
    

    	
    	
    	
    	
    	Platform.runLater(() -> {
    		lobbyGrid.getChildren().clear();
    		
    		
    		Label playerLobbyLabel = new Label("Player");
        	Label gameLobbyLabel = new Label("Status");
        	
        	lobbyGrid.add(playerLobbyLabel, 0, 1);
        	lobbyGrid.add(gameLobbyLabel, 1, 1);
        	
        	
        	playerLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            gameLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            playerLobbyLabel.setId("label-big");
            gameLobbyLabel.setId("label-big");
        	
        	
            Button refreshBtn = new Button("refresh");
        	refreshBtn.setId("refresh-button");
        	
        	lobbyGrid.add(refreshBtn, 2, 1);
        	
        	refreshBtn.setOnAction(e -> {
        		app.requestPlayerList();
        	});

    		

    		
    		int playerCount = playerArrayList.size();
    		//for (String username : playerArrayList) { 		      
    			for (int r = 0; r < playerCount; r++) {
                  //for (int c = 0; c < 3; c++) {
    				;
    				if(!(playerArrayList.get(r).equals(nameUser))) {
    					lobbyGrid.add(new Label(playerArrayList.get(r)), 0, r+2);
    					//lobbyGrid.add(new Button ("invite", null, css.style.Button_Plain), 1, r+2);
    					
    					
    					String currentPlayer = playerArrayList.get(r);
    					
    					Button tempBtn = new Button("Invite");
    					//Button tempBtn2 = new Button("Cancel Invite");

    					
    					
    					//tempBtn2.setOnAction(e -> {
    					//	challengedPlayers = null;
    					//});
    					
    					tempBtn.setId("small-button");
    					//tempBtn2.setId("small-button");
    					
    					
//    					if(challengedPlayers == null){
        					lobbyGrid.add(tempBtn, 1, r+2);    						
//    					}
//    					else {
//	    					if(challengedPlayers.contains(currentPlayer)) {
//	    						lobbyGrid.add(tempBtn2, 1, r+2);  
//	    					}
//    					}
        					tempBtn.setOnAction(e -> {
        						app.challengePlayer(currentPlayer, currentGame);
        						//challengedPlayers.add(currentPlayer);
        					});
        					
        				if(currentPlayer.length() > 18) {
        					r=r+1;
        				}
        				
    					//Button.setId("button-small");
    				}
    				
//                  	if(currentPlayer.invited() == true) {
//                      	//lobbyGrid.add(new Label(currentPlayer.getStatus()), 1, r+1);
//                      	lobbyGrid.add(new Label("You got invited!"), 1, r+2);
//                      	lobbyGrid.add(new Button("Accept"), 2, r+2);
//                      	lobbyGrid.add(new Button("Decline"), 3, r+2);        	
//                  	}
                  //}
      }
          // }
    		
    	});
    	
    }
    
    public void setChallenge(String name, String gameType, int chalNumber) {
    	
    	
    	Player tempPlayer = new Player(name, gameType, chalNumber);
    	inviteArrayList.add(tempPlayer);
    	
    	
    	
    	//lobbyGrid = new GridPane();
    	
    	//dummy speler:
//    	Player player1 = new Player("player123", "none", false);
//    	Player player2 = new Player("speler123132", "none", false);
//    	Player player3 = new Player("hallo23", "Invited you!", true);
//    	
//    	playerArray.add(player1);
//    	playerArray.add(player2);
//    	playerArray.add(player3);
    	
    	//int aantalSpelers = playerArrayList.size();
    	
//    	for (int r = 0; r < aantalSpelers; r++) {
//            //for (int c = 0; c < 3; c++) {
//            	//Player currentPlayer = playerArrayList.get(r);
//            	//lobbyGrid.add(new Label(currentPlayer.getName()), 0, r+2);
//            	
//            	if(currentPlayer.invited() == true) {
//                	//lobbyGrid.add(new Label(currentPlayer.getStatus()), 1, r+1);
//                	lobbyGrid.add(new Label("You got invited!"), 1, r+2);
//                	lobbyGrid.add(new Button("Accept"), 2, r+2);
//                	lobbyGrid.add(new Button("Decline"), 3, r+2);        	
//            	}
//            //}
//}

    	
    

    	
    	
    	
    	
    	Platform.runLater(() -> {
    		inviteGrid.getChildren().clear();
    		
    		
    		
        	//inviteGrid.add(inviteTitle, 0, 0);
        	
        	
        	
        	
            //Button refreshBtn = new Button("refresh");
        	//refreshBtn.setId("refresh-button");
        	
        	//inviteGrid.add(refreshBtn, 3, 1);
        	
        	//refreshBtn.setOnAction(e -> {
        	//	app.requestPlayerList();
        	//});

    		Label playerLobbyLabel = new Label("Invite");
        	Label gameLobbyLabel = new Label("Game");
        	//Label inviteTitle = new Label("Invites");
        	
        	inviteGrid.add(playerLobbyLabel, 0, 1);
        	inviteGrid.add(gameLobbyLabel, 1, 1);
	    	
        	playerLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            gameLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            playerLobbyLabel.setId("label-big");
            gameLobbyLabel.setId("label-big");

    		
    		int playerCount = inviteArrayList.size();
    		//for (String username : playerArrayList) { 		      
    			for (int r = 0; r < playerCount; r++) {
                  //for (int c = 0; c < 3; c++) {
    				
    				Player currentPlayer = inviteArrayList.get(r);
    				//if(!(inviteArrayList.get(r).equals(nameUser))) {
    				Label tempLabel = new Label(currentPlayer.getName());	
    				Label tempGameLabel = new Label(currentPlayer.getGame());
    				inviteGrid.add(tempLabel, 0, r+2);
    				inviteGrid.add(tempGameLabel, 1, r+2);
    				int currentIndex = r;
    				//}
                      	//lobbyGrid.add(new Label(currentPlayer.getStatus()), 1, r+1);
                      	//lobbyGrid.add(new Label("You got invited!"), 1, r+2);
    					Button tempAccept = new Button("Accept");
    					tempAccept.setOnAction(e -> {
    						app.acceptChallenge(currentPlayer.getChalNumber());
    						int acceptIndex = currentIndex;
    						inviteArrayList.remove(acceptIndex);
    					});
    					
                      	inviteGrid.add(tempAccept, 2, r+2);
                      	tempAccept.setId("small-button");
                      	//lobbyGrid.add(new Button("Decline"), 3, r+2);        	
                  	
                  }
    			
    		
     // }
          // }
    		
    	});
    	
    }
    
public void setGameScreen(GameView gameview) {
    	
    	
    	
    	
    	
    	Platform.runLater(() -> {
    		inviteGrid.getChildren().clear();
    		
    		root.getChildren().remove(drieBox);
        	root.getChildren().remove(toolbarbox);
    		showBoth(root, gameview);
    	});
    	
    }
    
    
	public void makeLobby(StackPane root) {
			
		
			
			lobbyGrid = new GridPane();
			
	    	lobbyGrid.setAlignment(Pos.CENTER);
	    	lobbyGrid.setPadding(new Insets(15));
		
	    	VBox lobbyVbox = new VBox();
	    	
	    	inviteGrid = new GridPane();
			
	    	inviteGrid.setAlignment(Pos.CENTER);
	    	inviteGrid.setPadding(new Insets(15));
	    	
	    	Label playerLobbyLabel = new Label("Invite");
        	Label gameLobbyLabel = new Label("Game");
        	//Label inviteTitle = new Label("Invites");
        	
        	inviteGrid.add(playerLobbyLabel, 0, 1);
        	inviteGrid.add(gameLobbyLabel, 1, 1);
	    	
        	playerLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            gameLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            playerLobbyLabel.setId("label-big");
            gameLobbyLabel.setId("label-big");
	    	
	    	VBox inviteVbox = new VBox();
	    	
	    	
	    	GridPane optionsGrid = new GridPane();
	    	
	    	VBox optionsVbox = new VBox();
	    	
	    	Label optionsGameLabel = new Label("Game:");
	    	Label optionsModeLabel = new Label("mode:");
	    	
	    	
	    	
	    	
	    	ObservableList<String> lobbyOptionsGame = 
	    		    FXCollections.observableArrayList(
	    		        "Reversi",
	    		        "Tic-tac-toe"
	    		    );
	    	
	    	
	    	ComboBox<String> lobbySelectGame = new ComboBox(lobbyOptionsGame);
	        lobbySelectGame.getSelectionModel().selectFirst();
	        
	        lobbySelectGame.setOnAction(e -> {
	            String selectedGame = lobbySelectGame.getSelectionModel().getSelectedItem();
	            
	            currentGame = selectedGame;
	        });
	        
	        
	        
	        ObservableList<String> lobbyOptionsMode = 
	    		    FXCollections.observableArrayList(
	    		        "AI",
	    		        "You"
	    		    );
	    	
	    	
	    	ComboBox<String> lobbySelectMode = new ComboBox(lobbyOptionsMode);
	        lobbySelectMode.getSelectionModel().selectFirst();
	    	
	    	optionsGrid.add(optionsGameLabel, 0, 1);
	    	optionsGrid.add(optionsModeLabel, 0, 2);
	    	
	    	optionsGrid.add(lobbySelectGame, 1, 1);
	    	optionsGrid.add(lobbySelectMode, 1, 2);
	    	
	    	
	    	lobbySelectMode.setId("small-dropdown");
	    	lobbySelectGame.setId("small-dropdown");
	    	
	    	
		
			ToolBar toolbar = new ToolBar();
	    	
	    	Label lobbyUsernameLabel = new Label("Logged in as " + nameUser + "  ");
	    	Label lobbyServerLabel = new Label("  server: " + nameServer);
	    	lobbyUsernameLabel.setId("toolbar-content");
	    	lobbyServerLabel.setId("toolbar-content");
	
	    	Button backToLoginBtn = new Button("Back");
	    	backToLoginBtn.setId("back-button");
	    	
	    	
	    	ArrayList<String> dummyInvites = new ArrayList<>();
	        dummyInvites.add("a");
	        dummyInvites.add("a");
	        dummyInvites.add("a");
	        dummyInvites.add("a");
	        dummyInvites.add("a");
	        
	        //setInviteList(dummyInvites);
	    	
	    	
	    	Separator separator1 = new Separator();
	    	separator1.setOrientation(Orientation.VERTICAL);
	    	
	    	Separator separator2 = new Separator();
	    	separator2.setOrientation(Orientation.VERTICAL);
	    	
	    	
	    	toolbar.getItems().add(backToLoginBtn);
	    	toolbar.getItems().add(separator2);
	    	toolbar.getItems().add(lobbyUsernameLabel);
	    	toolbar.getItems().add(separator1);
	    	toolbar.getItems().add(lobbyServerLabel);
	    	
	    	toolbarbox = new VBox();
	    	toolbarbox.getChildren().add(toolbar);
	    	//root.getChildren().add(lobbyGrid);
	
	    	inviteGrid.setId("invites-table");
	    	
	    	
	    	
	    	
	    	lobbyVbox.getChildren().add(lobbyGrid);
	    	inviteVbox.getChildren().add(inviteGrid);
	    	optionsVbox.getChildren().add(optionsGrid);
	    	
	    	drieBox.getChildren().add(lobbyVbox);
	    	drieBox.getChildren().add(inviteVbox);
	    	drieBox.getChildren().add(optionsVbox);

	    	
	    	ScrollPane scrollpane = new ScrollPane();
	    	scrollpane.setContent(drieBox);
	    	
	    	scrollpane.setId("scroll-pane");
	    	drieBox.minHeight(400);
	    	drieBox.prefHeight(400);
	    	scrollpane.minHeight(400);
	    	scrollpane.prefHeight(400);
	    	root.getChildren().add(toolbarbox);
	    	toolbarbox.getChildren().add(scrollpane);
	        
	    	
	    	
	    	
	    	backToLoginBtn.setOnAction(e -> {
        		app.disconnect();
	        	makeLogin(root);
	        	root.getChildren().remove(drieBox);
	        	root.getChildren().remove(toolbarbox);
	        });
	    	
	    	lobbyGrid.setHgap(10);
	    	lobbyGrid.setVgap(10);
	    	lobbyGrid.setPadding(new Insets(10, 10, 10, 10));
	    	
	    	inviteGrid.setHgap(10);
	    	inviteGrid.setVgap(10);
	    	inviteGrid.setPadding(new Insets(10, 10, 10, 10));
	    	
	    	inviteGrid.minHeight(400);
	    	inviteGrid.minWidth(100);
	    	
	    	inviteGrid.prefHeight(400);
	    	inviteGrid.prefWidth(100);
	    	
	    	optionsGrid.setHgap(10);
	    	optionsGrid.setVgap(10);
	    	optionsGrid.setPadding(new Insets(10, 10, 10, 10));
	    	
	    	
	    }
	    
	
	public void makeLocalLobby(StackPane root) {
		ToolBar toolbarLocal = new ToolBar();
		GridPane localLobbyGrid = new GridPane();
		
		localLobbyGrid.setAlignment(Pos.CENTER);
		localLobbyGrid.setPadding(new Insets(15));
		
		localLobbyGrid.setHgap(10);
		localLobbyGrid.setVgap(30);
		localLobbyGrid.setPadding(new Insets(10, 10, 10, 10));
		
		//Label playerLobbyLabel = new Label("Player");
		//Label gameLobbyLabel = new Label("Status");
		Label lobbyUsernameLabel = new Label("Logged in as " + nameUser + "  ");
		
		Button playBtn = new Button("play");
		
		
		lobbyUsernameLabel.setId("toolbar-content");
	
		Button backToLoginBtn = new Button("Back");
		backToLoginBtn.setId("back-button");
		
		
		
		Separator separator3 = new Separator();
		separator3.setOrientation(Orientation.VERTICAL);
		
		final ToggleGroup toggle = new ToggleGroup();
		
		RadioButton rb1 = new RadioButton();
		rb1.setText("Tic-Tac-Toe");
		rb1.setToggleGroup(toggle);
		RadioButton rb2 = new RadioButton();
		rb2.setText("Reversi");
		rb2.setToggleGroup(toggle);
		
		
		toolbarLocal.getItems().add(backToLoginBtn);
		toolbarLocal.getItems().add(separator3);
		toolbarLocal.getItems().add(lobbyUsernameLabel);
		
		localLobbyGrid.add(rb1, 1, 1);
		localLobbyGrid.add(rb2, 1, 2);
		//localLobbyGrid.add(playerLobbyLabel, 0, 1);
		//localLobbyGrid.add(gameLobbyLabel, 0, 2);
		localLobbyGrid.add(playBtn, 1, 3);
		VBox toolbarboxLocal = new VBox();
		toolbarboxLocal.getChildren().add(toolbarLocal);
		//root.getChildren().add(lobbyGrid);
	
		backToLoginBtn.setOnAction(e -> {
	    	makeLogin(root);
	    	root.getChildren().remove(localLobbyGrid);
	    	root.getChildren().remove(toolbarboxLocal);
	    });
		
		toolbarboxLocal.getChildren().add(localLobbyGrid);
		
		
		app.requestPlayerList();
		root.getChildren().add(toolbarboxLocal);
	}
}