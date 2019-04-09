package application;

public class Player {
	String name;
	String game;
	int chalNumber;
	
	Player(String name, String game, int chalNumber){
		this.name = name;
		this.game = game;
		this.chalNumber = chalNumber;
	}
	
	String getName() {
		return name;
	}
	
	String getGame() {
		return game;
	}
	
	int getChalNumber() {
		return chalNumber;
	}
	
	
}
