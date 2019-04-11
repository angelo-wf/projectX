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
import javafx.scene.control.CheckBox;
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
	private boolean currentMode = true;
	private String currentGame = "Reversi";
	private VBox toolbarbox;
	//private String nameUser;
	//private String nameServer;
	private HBox drieBox;
	private ArrayList<String> playerArrayList;
	private ButtonBase refreshBtn;
	private ApplicationHandler app;
	private ComboBox<String> lobbySelectGame;
	public Label lobbyUsernameLabel = new Label();
	public Label lobbyServerLabel = new Label();
	private ArrayList<Player> inviteArrayList = new ArrayList<Player>();
	private ArrayList<String> challengedPlayers = null;

	
	Lobby(StackPane mainPane, Gui gui, ApplicationHandler app){
		this.lobbyPane = mainPane;
		this.gui = gui;
		this.app = app;
		//root.getChildren().clear();
		
		
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
    	CheckBox subBtn= new CheckBox("subscribe");
    	subBtn.setId("small-button");
    	
    	
    	
    	ObservableList<String> lobbyOptionsGame = 
    		    FXCollections.observableArrayList(
    		        "Reversi",
    		        "Tic-tac-toe"
    		    );
    	
    	
    	lobbySelectGame = new ComboBox(lobbyOptionsGame);
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
    	
        
        lobbySelectMode.setOnAction(e -> {
            
        	
        	
        	String selectedLobbyMode = lobbySelectMode.getSelectionModel().getSelectedItem();
            
        	if(selectedLobbyMode.equals("AI")) {
	            currentMode = true;
        	}
        	if(selectedLobbyMode.equals("You")) {
        		currentMode= false;
        	}
        });
        
        
    	optionsGrid.add(optionsGameLabel, 0, 1);
    	optionsGrid.add(optionsModeLabel, 0, 2);
    	
    	optionsGrid.add(lobbySelectGame, 1, 1);
    	optionsGrid.add(lobbySelectMode, 1, 2);
    	optionsGrid.add(subBtn, 1, 3);
    	
    	
    	lobbySelectMode.setId("small-dropdown");
    	lobbySelectGame.setId("small-dropdown");
    	
    	
	
		ToolBar toolbar = new ToolBar();
    	
    	lobbyUsernameLabel = new Label("Logged in as " + "" + "  ");
    	lobbyServerLabel = new Label("  server: " + "");
    	lobbyUsernameLabel.setId("toolbar-content");
    	lobbyServerLabel.setId("toolbar-content");

    	Button backToLoginBtn = new Button("Disconnect");
    	backToLoginBtn.setId("back-button");
    	
    	
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
    	
    	
    	
    	inviteGrid.minHeight(200);
    	inviteGrid.minWidth(200);
    	lobbyVbox.getChildren().add(lobbyGrid);
    	inviteVbox.getChildren().add(inviteGrid);
    	optionsVbox.getChildren().add(optionsGrid);
    	
    	//drieBox.getChildren().clear();
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
    		//app.disconnect();
    		//scrollpane.setContent(null);
    		//root.getChildren().clear();
        	//toolbarbox.getChildren().clear();
        	//makeLogin(root);
        	
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
	
	public void setVisible() {
		lobbyPane.setVisible(true);
	}
	
	public void setInvisible() {
		lobbyPane.setVisible(false);
	}
	
	public String getSelectedGame() {
		return lobbySelectGame.getSelectionModel().getSelectedItem();
	}
	
	public void setPlayerList(ArrayList<String> playerArray) {
    	this.playerArrayList = playerArray;
    	
    	
    	Platform.runLater(() -> {
    		lobbyGrid.getChildren().clear();
    		Label playerLobbyLabel2 = new Label("Player");
        	Label gameLobbyLabel2 = new Label("Status");
        	
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
//    				
					
					//System.out.println(gameCurrent);
					
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
	
	
}
