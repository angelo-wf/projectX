package application;

import java.util.ArrayList;

import gamehandler.GameView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gui {
        
    HBox hBox = new HBox();
    
    ApplicationHandler app;
   	StackPane root = new StackPane();
	HBox drieBox = new HBox();
	
	
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
        primaryStage.getIcons().add(new Image("assets/reversi_icon.jpg"));
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
    	} else {
    		Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR, "Can't connect to the server", ButtonType.OK);
				alert.showAndWait();
			});
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

	    	});
	    	
	    }
    
	public boolean getMode() {
		return lobby.getSelectedMode();
	}
	
	public void updateStats(int yourPoints, int oppPoints, int beurt, String endReason) {
		game.updateStats(yourPoints, oppPoints, beurt, endReason);
	}
}