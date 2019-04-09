package runner;

import application.ApplicationHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class Runner extends Application {

	@Override
	public void start(Stage primaryStage) {
//		
		ApplicationHandler app = new ApplicationHandler(primaryStage);
		GameWindow window = new GameWindow();
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
