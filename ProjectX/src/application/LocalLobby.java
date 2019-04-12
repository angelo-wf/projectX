package application;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LocalLobby {

	
	private StackPane localLobbyPane = new StackPane();;
	private Gui gui;
	private ApplicationHandler app;
	private Label lobbyUsernameLabel;

	LocalLobby(StackPane mainPane, Gui gui, ApplicationHandler app){
		this.localLobbyPane = mainPane;
		this.gui = gui;
		this.app = app;
		
		
		ToolBar toolbarLocal = new ToolBar();
		GridPane localLobbyGrid = new GridPane();
		
		localLobbyGrid.setAlignment(Pos.CENTER);
		localLobbyGrid.setPadding(new Insets(15));
		
		localLobbyGrid.setHgap(10);
		localLobbyGrid.setVgap(30);
		localLobbyGrid.setPadding(new Insets(10, 10, 10, 10));
		
		//Label playerLobbyLabel = new Label("Player");
		//Label gameLobbyLabel = new Label("Status");
		lobbyUsernameLabel = new Label("Logged in as " + "" + "  ");
		
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
			gui.localLobbyToLogin();
	    	//makeLogin(root);
	    	//root.getChildren().remove(localLobbyGrid);
	    	//root.getChildren().remove(toolbarboxLocal);
	    });
		
		toolbarboxLocal.getChildren().add(localLobbyGrid);
		
		
		//app.requestPlayerList();
		localLobbyPane.getChildren().add(toolbarboxLocal);
	}
	
	public void setName(String name) {
		lobbyUsernameLabel.setText("Logged in as " + name + "  ");
	}
	
	public void setVisible() {
		localLobbyPane.setVisible(true);
	}
	
	public void setInvisible() {
		localLobbyPane.setVisible(false);
	}
	
}
