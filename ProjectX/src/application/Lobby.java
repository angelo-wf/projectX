package application;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Lobby {

	private StackPane lobbyPane;
	private GridPane lobbyGrid;
	private GridPane inviteGrid;
	private Gui gui;
	private VBox toolbarbox;
	private HBox drieBox;
	private ArrayList<String> playerArrayList;
	private ButtonBase refreshBtn;
	private ApplicationHandler app;
	private ComboBox<String> lobbySelectGame;
	private ComboBox<String> lobbySelectMode;
	private Label lobbyUsernameLabel = new Label();
	private Label lobbyServerLabel = new Label();
	private ArrayList<Player> inviteArrayList = new ArrayList<Player>();

	
	Lobby(StackPane mainPane, Gui gui, ApplicationHandler app){
		this.lobbyPane = mainPane;
		this.gui = gui;
		this.app = app;
		
		drieBox = new HBox();
		
		lobbyGrid = new GridPane();
		
    	lobbyGrid.setAlignment(Pos.CENTER);
    	lobbyGrid.setPadding(new Insets(15));
	
    	VBox lobbyVbox = new VBox();
    	
    	inviteGrid = new GridPane();
		
    	inviteGrid.setAlignment(Pos.CENTER);
    	inviteGrid.setPadding(new Insets(15));
    	
    	Label playerLobbyLabel = new Label("Invite");
    	Label gameLobbyLabel = new Label("Game");
    	
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
    	Button subBtn= new Button("subscribe");
    	subBtn.setId("small-button");
    	
    	
    	
    	ObservableList<String> lobbyOptionsGame = 
    		    FXCollections.observableArrayList(
    		        "Reversi",
    		        "Tic-tac-toe"
    		    );
    	
    	
    	lobbySelectGame = new ComboBox<String>(lobbyOptionsGame);
        lobbySelectGame.getSelectionModel().selectFirst();
        
        
        
        ObservableList<String> lobbyOptionsMode = 
    		    FXCollections.observableArrayList(
    		        "AI",
    		        "You"
    		    );
    	
    	
    	lobbySelectMode = new ComboBox<String>(lobbyOptionsMode);
        lobbySelectMode.getSelectionModel().selectFirst();
        
        
    	optionsGrid.add(optionsGameLabel, 0, 1);
    	optionsGrid.add(optionsModeLabel, 0, 2);
    	
    	optionsGrid.add(lobbySelectGame, 1, 1);
    	optionsGrid.add(lobbySelectMode, 1, 2);
    	optionsGrid.add(subBtn, 1, 3);
    	
    	
    	subBtn.setOnAction(e ->{
            app.subscribe(getSelectedGame());
    	});
    	
    	
    	lobbySelectMode.setId("small-dropdown");
    	lobbySelectGame.setId("small-dropdown");
    	
    	
	
		ToolBar toolbar = new ToolBar();
    	
    	lobbyUsernameLabel = new Label("Logged in as " + "" + "  ");
    	lobbyServerLabel = new Label("  server: " + "");
    	lobbyUsernameLabel.setId("toolbar-content");
    	lobbyServerLabel.setId("toolbar-content");

    	Button backToLoginBtn = new Button("Disconnect");
    	backToLoginBtn.setId("back-button");
    	
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

    	inviteGrid.setId("invites-table");
    	
    	
    	
    	inviteGrid.minHeight(200);
    	inviteGrid.minWidth(200);
    	lobbyVbox.getChildren().add(lobbyGrid);
    	inviteVbox.getChildren().add(inviteGrid);
    	optionsVbox.getChildren().add(optionsGrid);
    	
    	drieBox.getChildren().add(lobbyVbox);
    	drieBox.getChildren().add(inviteVbox);
    	drieBox.getChildren().add(optionsVbox);
    	
    	
    	ScrollPane scrollpane = new ScrollPane();
    	scrollpane.setContent(null);
    	scrollpane.setContent(drieBox);
    	
    	scrollpane.setId("scroll-pane");
    	drieBox.minHeight(400);
    	drieBox.minWidth(800);
    	drieBox.prefWidth(800);
    	drieBox.prefHeight(400);
    	scrollpane.minHeight(400);
    	scrollpane.prefHeight(400);
    	toolbarbox.getChildren().add(scrollpane);
    	lobbyPane.getChildren().add(toolbarbox);
    	
    	
    	backToLoginBtn.setOnAction(e -> {
        	gui.lobbyToLogin();
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
	
	public void setNameServer(String name, String server) {
		lobbyUsernameLabel.setText("Logged in as " + name + "  ");
    	lobbyServerLabel.setText("  server: " + server);
	}
	
	public void setVisible() {
		lobbyPane.setVisible(true);
	}
	
	public void setInvisible() {
		lobbyPane.setVisible(false);
	}
	
	public String getSelectedGame() {
		return lobbySelectGame.getSelectionModel().getSelectedItem();
	}
	
	public boolean getSelectedMode() {
		return lobbySelectMode.getSelectionModel().getSelectedItem().equals("AI");
	}
	
	public void clearInvites() {
		inviteArrayList.clear();
		updateChalList();
	}
	
	public void setPlayerList(ArrayList<String> playerArray) {
    	this.playerArrayList = playerArray;
    	
    	
    	Platform.runLater(() -> {
    		lobbyGrid.getChildren().clear();
    		Label playerLobbyLabel2 = new Label("Players");
        	Label gameLobbyLabel2 = new Label("(" + playerArray.size() + " online)");
        	
        	lobbyGrid.add(playerLobbyLabel2, 0, 1);
        	lobbyGrid.add(gameLobbyLabel2, 1, 1);
        	
        	playerLobbyLabel2.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            gameLobbyLabel2.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            playerLobbyLabel2.setId("label-big");
            gameLobbyLabel2.setId("label-big");
        	
            refreshBtn = new Button("refresh");
        	refreshBtn.setId("refresh-button");
        	
        	lobbyGrid.add(refreshBtn, 2, 1);
    		
        	refreshBtn.setOnAction(e -> {
        		app.requestPlayerList();
        	});
    		
    		int playerCount = playerArrayList.size();
    		
			for (int r = 0; r < playerCount; r++) {
				
				if(!(playerArrayList.get(r).equals(gui.getName()))) {
					lobbyGrid.add(new Label(playerArrayList.get(r)), 0, r+2);

					String currentPlayer = playerArrayList.get(r);
					
					Button tempBtn = new Button("Invite");
					
					tempBtn.setId("small-button");
					
					lobbyGrid.add(tempBtn, 1, r+2);
					
					tempBtn.setOnAction(e -> {
						String gameCurrent = getSelectedGame();
						app.challengePlayer(currentPlayer, gameCurrent);
					});
    					
    				if(currentPlayer.length() > 18) {
    					r=r+1;
    				}
				}
			}    		
    	});    	
    }
	
	
	
public void setChallenge(String name, String gameType, int chalNumber) {
    	
    	Player tempPlayer = new Player(name, gameType, chalNumber);
    	inviteArrayList.add(tempPlayer);
    	
    	updateChalList();  	
    }

	
public void cancelChallenge(int chalNumber) {
	
	int index=0;
	
	for(int i = 0; i < inviteArrayList.size(); i++) {
		if(inviteArrayList.get(i).getChalNumber()==chalNumber) {
			index = i;
		}
	}
	
	inviteArrayList.remove(index);
	updateChalList();
}
	
private void updateChalList() {
	Platform.runLater(() -> {
		inviteGrid.getChildren().clear();

		Label playerLobbyLabel = new Label("Invite");
    	Label gameLobbyLabel = new Label("Game");
    	
    	inviteGrid.add(playerLobbyLabel, 0, 1);
    	inviteGrid.add(gameLobbyLabel, 1, 1);
    	
    	playerLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        gameLobbyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        playerLobbyLabel.setId("label-big");
        gameLobbyLabel.setId("label-big");

		int playerCount = inviteArrayList.size();	      
			for (int r = 0; r < playerCount; r++) {
				
				Player currentPlayer = inviteArrayList.get(r);
				Label tempLabel = new Label(currentPlayer.getName());	
				Label tempGameLabel = new Label(currentPlayer.getGame());
				inviteGrid.add(tempLabel, 0, r+2);
				inviteGrid.add(tempGameLabel, 1, r+2);
				int currentIndex = r;Button tempAccept = new Button("Accept");
					tempAccept.setOnAction(e -> {
						app.acceptChallenge(currentPlayer.getChalNumber());
						int acceptIndex = currentIndex;
						inviteArrayList.remove(acceptIndex);
						updateChalList();
					});
					
                  	inviteGrid.add(tempAccept, 2, r+2);
                  	tempAccept.setId("small-button");
              }
	});
}
}
