package application;

public class ApplicationHandler {
	
	private Connection connection;
	// global view, gamemodel, players, view
	
	public ApplicationHandler() {
		// make window
	}
	
	public void setServer(String name) {
		//connection = new Connection(name.split(":")[0], Integer.parseInt(name.split(":")[1]));
	}
	
	public void requestPlayerList() {
		if(connection == null) {
			return;
		} else {
			connection.getGamelist();
		}
	}
	
	public void recievePlayerList() {
		//send to view
	}
}
