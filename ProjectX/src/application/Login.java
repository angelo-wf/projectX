package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Login {

	private StackPane loginPane;
	private String nameUser;
	private String nameServer;
	private Gui gui;
	private ApplicationHandler app;
	
	Login(StackPane mainPane, Gui gui, ApplicationHandler app){
		this.loginPane = mainPane;
		this.gui = gui;
		this.app = app;
		
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
		        
	    ComboBox<String> selectMode = new ComboBox<String>(options);
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
		
		serverInput.setOnAction(e -> {
			enterButton.fire();
		});

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

	        		gui.loginToLobby(nameUser, nameServer);
	        	}
	        	if(canLogin == true && selectedMode1 == "Local") {
	        		gui.loginToLocalLobby(nameUser);
	        	}
	    	
	    });
		
		enterButton.setId("record-sales");
		
		loginGrid.setHgap(10);
		loginGrid.setVgap(10);
		loginGrid.setPadding(new Insets(10, 10, 10, 10));
		
		loginPane.getChildren().add(loginGrid);
	}
	
	
	
	
	
	
	public void setVisible(){
		loginPane.setVisible(true);
	}
	
	public void setInvisible(){
		loginPane.setVisible(false);
	}
}
