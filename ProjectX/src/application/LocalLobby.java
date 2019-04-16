package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private ComboBox<String> localLobbySelectMode;

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
		
		rb1.setSelected(true);
		
		 ObservableList<String> localLobbyOptions = 
	    		    FXCollections.observableArrayList(
	    		    	"AI vs AI",
	    		        "YOU vs AI",
	    		        "AI vs YOU"
	    		    );
	    	
	    	
	    	localLobbySelectMode = new ComboBox<String>(localLobbyOptions);
	    	localLobbySelectMode.getSelectionModel().selectFirst();
		
		localLobbySelectMode.setId("small-dropdown");
		
		toolbarLocal.getItems().add(backToLoginBtn);
		toolbarLocal.getItems().add(separator3);
		toolbarLocal.getItems().add(lobbyUsernameLabel);
		
		localLobbyGrid.add(localLobbySelectMode, 1, 0);
		localLobbyGrid.add(rb1, 1, 1);
		localLobbyGrid.add(rb2, 1, 2);
		localLobbyGrid.add(playBtn, 1, 3);
		VBox toolbarboxLocal = new VBox();
		toolbarboxLocal.getChildren().add(toolbarLocal);
		
		
		playBtn.setOnAction(e ->{
			RadioButton selectedRadioButton = (RadioButton) toggle.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();
			String modeSelected = localLobbySelectMode.getSelectionModel().getSelectedItem();
			
			
			if(toggleGroupValue.equals("Reversi")) {
				switch(modeSelected) {
					case "YOU vs AI":
						app.startGame("Reversi", 1, 0, "CPU");
					break;
					case "AI vs YOU":
						app.startGame("Reversi", 2, 0, "CPU");
					break;
					case "AI vs AI":
						app.startGame("Reversi", 1, 3, "CPU");
					break;
					
				}
			}
			if(toggleGroupValue.equals("Tic-tac-toe")) {
				switch(modeSelected) {
				case "YOU vs AI":
					app.startGame("Tic-tac-toe", 1, 0, "CPU");
				break;
				case "AI vs YOU":
					app.startGame("Tic-tac-toe", 2, 0, "CPU");
				break;
				case "AI vs AI":
					app.startGame("Tic-tac-toe", 1, 3, "CPU");
				break;
				
			}		
			}
		});
		
		
		
		
		backToLoginBtn.setOnAction(e -> {
			gui.localLobbyToLogin();
	    });
		
		toolbarboxLocal.getChildren().add(localLobbyGrid);
		
		
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
