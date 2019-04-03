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
		switch (type) {
		case "MATCH":
			System.out.println(map.toString());
			break;
		case "YOURTURN":
			System.out.println(map.toString());
			break;
		case "MOVE":
			System.out.println(map.toString());
			break;
		case "WIN":
			System.out.println(map.toString());
			break;
		case "LOSS":
			System.out.println(map.toString());
			break;
		case "CHALLENGE":
			System.out.println(map.toString());
			break;
		case "CHALLENGE_CANCELLED":
			System.out.println(map.toString());
			break;
		case "PLAYERLIST":
			System.out.println(map.toString());
			break;
		case "GAMELIST":
			System.out.println(map.toString());
			break;
		default:
			break;
		}
	}
}
