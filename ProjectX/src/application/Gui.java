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

	
	
	private boolean isOnline;

    public Gui(Stage primaryStage, ApplicationHandler app) {
    	this.app = app;
    	primaryStage.setTitle("Project X UltraBotMaximum");
    	//showBoth(root);
        //makeLogin(root);
        //makeLobby(root);

    	//StackPane mainPane;
        
        login = new Login(loginPane, this, app);
        lobby = new Lobby(lobbyPane, this, app);
        game = new Game(gamePane, this, app);
        localLobby = new LocalLobby(localLobbyPane, this, app);
        lobby.setInvisible();
        game.setInvisible();
        localLobby.setInvisible();
        
        
        root.getChildren().addAll(loginPane, lobbyPane, gamePane, localLobbyPane);
        
        Scene scene = new Scene(root, 800, 400);

        //scene.getStylesheets().add(css);
        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
        	app.appClosing();
        });
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public void loginToLobby(String username, String servername) {
    	if(app.setServer(username, servername)) {
    		
    		Platform.runLater(() -> {
        		lobby.setNameServer(nameUser, nameServer);
            	
        	});
    		
    		isOnline = true;
    		nameUser = username;
    		nameServer = servername;
    		app.requestPlayerList();
    		login.setInvisible();
        	lobby.setVisible();
    	}
    	
    	
    }
    
    public void loginToLocalLobby(String username) {
    	
    	Platform.runLater(()-> {
    		localLobby.setName(nameUser);
    		
    	});
    	
    	isOnline = false;
    	app.setLocal(nameUser);
    	
    	nameUser = username;
    	login.setInvisible();
    	localLobby.setVisible();
    }
    
    public boolean getOnline() {
    	return isOnline;
    }
    
    
    public void gameToLobby(){
    	app.requestPlayerList();
    	game.setInvisible();
    	lobby.setVisible();
    }
    
    public void lobbyToGame() {
    	game.setVisible();
    	lobby.setInvisible();
    }
    
    public void localLobbyToGame(){
    	localLobby.setInvisible();
    	game.setVisible();
    }
    
    public void gameToLocalLobby() {
    	game.setInvisible();
    	localLobby.setVisible();
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
    
    public void straightToLogin() {
    	lobby.setInvisible();
    	game.setInvisible();
    	login.setVisible();
    	lobby.clearInvites();
    }
    
    
    
    public void localLobbyToLogin() {
    	 localLobby.setInvisible();
    	 login.setVisible();
    }
    
    public void lobbyToLogin() {
    	
    	
    	
    	
    	app.disconnect();
    	lobby.clearInvites();
    	login.setVisible();
    	lobby.setInvisible();
    }
    
    public String getName() {
    	return nameUser;
    }
    public String getServerName() {
    	return nameServer;
    }
    
	public void setGameScreen(GameView gameview, String player2name) {
	    	
		
			
	    	
	    	if(getOnline()) {
		    	lobbyToGame();
        	}
        	else {
    	    	localLobbyToGame();
        	}
	    	
	    	
	    	Platform.runLater(() -> {
		    	game.setGameView(gameview.getBoardView(), nameUser, player2name, gameview.getPieceNames());

	    		//inviteGrid.getChildren().clear();
	    		
	    		//root.getChildren().remove(drieBox);
	        	//root.getChildren().remove(toolbarbox);
	    		//showBoth(root, gameview);
	    	});
	    	
	    }
    
	public boolean getMode() {
		return lobby.getSelectedMode();
	}
	
	public void updateStats(int yourPoints, int oppPoints, int beurt, String endReason) {
		game.updateStats(yourPoints, oppPoints, beurt, endReason);
	}
}