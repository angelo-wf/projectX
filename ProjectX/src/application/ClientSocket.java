package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientSocket {
	
	private boolean running;
	private OutputStreamWriter outputStream;
	private BufferedReader inputStream;
	private Socket socket;
	private Connection connection;
	private boolean intendedClose;
	
	public ClientSocket(String address, int port, Connection connection) throws IOException {
		socket = new Socket(address, port);
		this.connection = connection;
		running = true;
		outputStream = new OutputStreamWriter(socket.getOutputStream());
		inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		Thread serverThread = new Thread(new ServerWorker());
		intendedClose = false;
		serverThread.start();
	}
	
	public void sendToServer(String message) {
		// send to server
		try {
			outputStream.write(message + "\n");
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ServerWorker implements Runnable {	
		@Override
		public void run() {
			try {

				
				
				while(running) {
					String line = inputStream.readLine();
					// when connection stops sending
					if(line == null) {
						//close connection
						running = false;
						inputStream.close();
						outputStream.close();
						break;
					}
					// process line
					connection.checkInput(line);
				}
				System.out.println("Server closed connection.");
				if(!intendedClose) {
					connection.notifyConnectionLost();
				}
			} catch(IOException e) {
				System.out.println(e);
			}
		}
	}
	
	public void setIntendedClose() {
		intendedClose = true;
	}
	
	public void close() {
		//close the connection safely
		intendedClose = true;
		running = false;
//		if(inputStream != null) {
//			try {
//				inputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if(outputStream != null) {
//			try {
//				outputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		// those get closed when socket gets closed, will throw an exception, but it doesn't matter
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
