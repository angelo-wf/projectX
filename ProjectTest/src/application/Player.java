package application;

public class Player {
	String name;
	String game;
	Boolean invited;
	
	Player(String name, String game, Boolean invited){
		this.name = name;
		this.game = game;
		this.invited = invited;
	}
	
	String getName() {
		return name;
	}
	
	String getStatus() {
		return game;
	}
	
	Boolean invited() {
		return invited;
	}
}
