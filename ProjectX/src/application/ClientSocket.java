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
	
	public ClientSocket(String address, int port, Connection connection) {
		try { 
			socket = new Socket(address, port);
			this.connection = connection;
			running = true;
			Thread serverThread = new Thread(new ServerWorker());
			serverThread.start();
		} catch(IOException ex) {
			System.out.println(ex);
		}
	}
	
	public void sendToServer(String message) {
		// send to server
		try {
			outputStream.write(message);
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
				inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outputStream = new OutputStreamWriter(socket.getOutputStream());
				
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
			} catch(IOException e) {
				System.out.println(e);
			}
		}
	}
	
	public void close() {
		//close the connection safely
		if(inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
