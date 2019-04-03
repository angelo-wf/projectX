package application;

import java.util.HashMap;

public class ApplicationHandler {
	
	private Connection connection;
	// global view, gamemodel, players, view
	
	public ApplicationHandler() {
		// make window
	}
	
	public void setServer(String name, String address) {
		connection = new Connection(address.split(":")[0], Integer.parseInt(address.split(":")[1]));
		connection.login(name);
	}
	
	public void requestPlayerList() {
		if(connection == null) {
			return;
		} else {
			connection.getGamelist();
		}
	}
	
	public void recieveMessage(HashMap<String, Object> map) {
		//send to view
		String type = (String) map.get("MESSAGETYPE");
	}
}
