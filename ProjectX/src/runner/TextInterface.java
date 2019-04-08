package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextInterface extends Application {
	
	private boolean running;
	private TextArea output;
	private OutputStreamWriter outputStream;
	private Socket socket;
	
	class ClientWorker implements Runnable {
		
		@Override
		public void run() {
			try {
				socket = new Socket("145.33.225.170", 7789);
				//socket = new Socket("localhost", 7789);
				BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outputStream = new OutputStreamWriter(socket.getOutputStream());
				Platform.runLater(() -> {
					output.appendText("Connected to server\n");
				});
				while(running) {
					String line = inputStream.readLine();
					if(line == null) {
						inputStream.close();
						outputStream.close();
						break;
					}
					Platform.runLater(() -> {
						output.appendText("SERVER: " + line + "\n");
					});
				}
				socket.close();
				Platform.runLater(() -> {
					output.appendText("The server has disconnected\n");
				});
			} catch(IOException ex) {
				Platform.runLater(() -> {
					output.appendText("Failed to connect to server:\n" + ex.toString() + "\n");
				});
			}
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		VBox container = new VBox();
		HBox sendBox = new HBox();
		
		TextField input = new TextField();
		output = new TextArea();
		output.setEditable(false);
		
		sendBox.getChildren().add(input);
		
		container.getChildren().add(output);
		container.getChildren().add(sendBox);
		
		Scene scene = new Scene(container, 500, 250);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Simple gameserver client");
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			running = false;
			try {
//				if(inputStream != null) {
//					inputStream.close();
//				}
//				if(outputStream != null) {
//					outputStream.close();
//				}
				if(socket != null) {
					socket.close();
				}
			} catch(IOException ex) {
				// ignored, closing anyways
			}
		});
		
		input.setOnAction(e ->  {
			try {
				if(outputStream != null) {
					outputStream.write(input.getText() + "\n");
					outputStream.flush();
					Platform.runLater(() -> {
						output.appendText("CLIENT: " + input.getText() + "\n");
						input.setText("");
					});
				}
			} catch(IOException ex) {
				Platform.runLater(() -> {
					output.appendText("Failed to send:\n" + ex.toString() + "\n");
				});
			}
		});
		
		running = true;
		Thread clientThread = new Thread(new ClientWorker());
		clientThread.start();
	}

	public static void main(String[] args) {
		//System.out.println("Start");
		Application.launch(args);
	}

}
