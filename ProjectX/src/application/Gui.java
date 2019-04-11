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
import javafx.scene.control.Labeled;
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
	private Button ffBtn;
	private Button ffBackBtn;
	private boolean currentMode = true;
	
	
	private StackPane loginPane = new StackPane();
	private StackPane lobbyPane = new StackPane();
	private StackPane gamePane = new StackPane();
	private StackPane localLobbyPane = new StackPane();
	
	private Login login;
	private Lobby lobby;
	private Game game;
	private LocalLobby localLobby;
	
	
	private String nameUser;
	private String nameServer;
    
    public Gui(Stage primaryStage, ApplicationHandler app) {
    	this.app = app;
    	primaryStage.setTitle("Game");
    	//showBoth(root);
        //makeLogin(root);
        //makeLobby(root);

    	//StackPane mainPane;
        
        login = new Login(loginPane, this, app);
        lobby = new Lobby(lobbyPane, this, app);
        lobby.setInvisible();
        
        
        root.getChildren().addAll(loginPane, lobbyPane);
        
        Scene scene = new Scene(root, 800, 400);

        //scene.getStylesheets().add(css);
        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void loginToLobby(String username, String servername) {
    	if(app.setServer(username, servername)) {
    		
    		Platform.runLater(() -> {
        		lobby.lobbyUsernameLabel.setText("Logged in as " + nameUser + "  ");
            	lobby.lobbyServerLabel.setText("  server: " + nameServer);
            	
        	});
    		
    		nameUser = username;
    		nameServer = servername;
    		app.requestPlayerList();
    		login.setInvisible();
        	lobby.setVisible();
    	}
    	
    	
    }
    
    
    public void gameToLobby(){
    	game.setInvisible();
    	lobby.setVisible();
    }
    
    public void lobbyToGame() {
    	game.setVisible();
    	lobby.setInvisible();
    }
    
    public void setPlayerList(ArrayList<String> playerArray) {
    	lobby.setPlayerList(playerArray);
    }
    
    public void setChallenge(String name, String gameType, int chalNumber) {
    	lobby.setChallenge(name, gameType, chalNumber);
    }

    public void cancelChallenge(int chalNumber) {
    	lobby.cancelChallenge(chalNumber);
    }
    
    
    public void lobbyToLogin() {
    	
    	
    	
    	
    	app.disconnect();
    	login.setVisible();
    	lobby.setInvisible();
    }
    
    public String getName() {
    	return nameUser;
    }
    public String getServerName() {
    	return nameServer;
    }
    
    
    public void makeStats() {
        
    }
    
    
    
    
    
    
    
    
    
    public void makeLogin(StackPane root) {
    	
    	
    }
    
    
    
    
	public void setGameScreen(GameView gameview) {
	    	
	    	lobbyToGame();
	    	
	    	
	    	Platform.runLater(() -> {
		    	game.setGameView(gameview.getBoardView());

	    		//inviteGrid.getChildren().clear();
	    		
	    		//root.getChildren().remove(drieBox);
	        	//root.getChildren().remove(toolbarbox);
	    		//showBoth(root, gameview);
	    	});
	    	
	    }
    
    
	public void makeLobby(StackPane root) {
			
			
	    	
	    	
	    }
	    
	
	public boolean getMode() {
		return currentMode;
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
		rb1.setText("Tic-tac-toe");
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
	
		
		
//		toggle.setOnAction(e -> {
//            
//        	
//        	
//        	String selectedLobbyMode = lobbySelectMode.getSelectionModel().getSelectedItem();
//            
//        	if(selectedLobbyMode.equals("AI")) {
//	            currentMode = true;
//        	}
//        	if(selectedLobbyMode.equals("You")) {
//        		currentMode= false;
//        	}
//        });
        
		
		
		playBtn.setOnAction(e ->{
			RadioButton selectedRadioButton = (RadioButton) toggle.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();
			
			if(toggleGroupValue.equals("Reversi")) {
				//app.
			}
			if(toggleGroupValue.equals("Tic-tac-toe")) {
							
			}
		});
		
		
		
		
		backToLoginBtn.setOnAction(e -> {
	    	makeLogin(root);
	    	root.getChildren().remove(localLobbyGrid);
	    	root.getChildren().remove(toolbarboxLocal);
	    });
		
		toolbarboxLocal.getChildren().add(localLobbyGrid);
		
		
		//app.requestPlayerList();
		root.getChildren().add(toolbarboxLocal);
	}
	
	public void updateStats(int yourPoints, int oppPoints, int beurt, String endReason) {
		game.updateStats(yourPoints, oppPoints, beurt, endReason);
	}
}