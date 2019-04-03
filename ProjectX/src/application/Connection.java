package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection {
	
	//represent streams
    public String toServer;
    public String fromServer;
    private ClientSocket clientSocket;
    //new queue for messages
    LinkedBlockingQueue<HashMap<String, Object>> messageQueue = new LinkedBlockingQueue<>();

    public Connection(String address, int port) {
    	clientSocket = new ClientSocket(address, port, this);
    	
    }
    
    public void close() {
    	clientSocket.close();
    }
    
    public LinkedBlockingQueue<HashMap<String, Object>> getQueue(){
        return messageQueue;
    }

    public void sendMessage(String message){
        // send to socket
    	toServer = message;
    }

    public String getMessage() {
    	return toServer;
    }
    
    public void checkInput(String fromServer){
    	
    	String firstWord[] = fromServer.split(" ",2);
    	switch (firstWord[0]) {
        	case "OK":
        		ok();
        		break;
        	case "SVR":
        		String secondWord[] = firstWord[1].split(" ", 2);
        		switch (secondWord[0]) {
                 	case "GAME":
                 		String thirdWord[] = secondWord[1].split(" ",2);
                 		String otherWords = thirdWord[1];
                 		switch (thirdWord[0]) {
                 			case "MATCH":
                 				match(otherWords);
                 				break;
                 			case "YOURTURN":
                 				yourturn(otherWords);
                 				break;
                 			case "MOVE":
                 				move(otherWords);
                 				break;
                 			case "WIN":
                 				win(otherWords);
                 				break;
                 			case "LOSS":
                 				loss(otherWords);
                 				break;
                 			case "DRAW":
                 				draw(otherWords);
                 				break;
                 			case "CHALLENGE":
                 				String forthWord[] = otherWords.split(" ",2);
                 				// Check if challenge is cancelled
                 				if(forthWord[0] == "CANCELLED") {
                 					cancelled(forthWord[1]);
                 					break;
                 				}
                 				challenge(otherWords);
                 				break;
                 			default:
                 				// not recognized
                 				System.out.println("Could not recognize command. /n" + "Command: " + secondWord[1]);
                 				break;
                 		}
                 	case "PLAYERLIST":
                 		playerlist(secondWord[1]);                	 
                 		break;
                 	case "GAMELIST":
                 		gamelist(secondWord[1]);
                 		break;
                 	default:
                 		// not recognized
                 		System.out.println("Could not recognize command. /n" + "Command: " + firstWord[1]);
                 		break;
        			}
        default:
        // not recognized
        System.out.println("Could not recognize command. /n" + "Command: " + fromServer);
           break;
       }
   }

    public void setStringInHashMap(String stringMap, String messageType){
    	// Turn string which contains map into a HashMap
        HashMap<String, Object> gameMap = new HashMap<>();    
        stringMap.substring(1, stringMap.length()-1);
        String[] keyValuePairs = stringMap.split(",");
        // enter items in the HashMap
        gameMap.put("MESSAGETYPE", messageType);
        for(String pair : keyValuePairs){
            String[] entry = pair.split(":");
            gameMap.put(entry[0].trim(), entry[1].trim().substring(1,entry[1].length()));
        }
        // Enter HashMap in message queue
        messageQueue.add(gameMap);
    }
    
    public void setListInHashMap(String stringMap, String messageType) {
    	// Turn the string which contains a list into a HashMap
        HashMap<String, Object> gameMap = new HashMap<>();
        // Enter items in new list
        List<String> newList = new ArrayList<String>(Arrays.asList(stringMap.split(",")));
        // remove quotation marks from strings
        for (int i = 0; i < newList.size(); i++) {
        	newList.set(i, newList.get(i).substring(1, newList.size()));
        }
        // enter list and message type in the HashMap
        gameMap.put("MESSAGETYPE", messageType);
        gameMap.put("LIST", newList);
        // Enter HashMap in message queue
        messageQueue.add(gameMap);
    }
    
    /**
     * toSever methods
     */
    public void login(String username){
        toServer = "login " + username;
        sendMessage(toServer);
    }
    
    public void logout(){
        toServer = "logout";
        sendMessage(toServer);
    }
    
    public void getGamelist(){
        toServer = "get gamelist";
        sendMessage(toServer);
    }
    
    public void getPlayerlist(){
        toServer= "get playerlist";
        sendMessage(toServer);
    }
    
    public void subscirbe(String game){
        toServer= "subscribe " + game;
        sendMessage(toServer);
    }
    
    public void move(int pos){
        toServer= "move " + pos;
        sendMessage(toServer);
    }
    
    public void forfeit(){
        toServer= "forfeit";
        sendMessage(toServer);
    }
    
    public void challenge(String player, String game){
        toServer= "challenge " + player + " " + game;
        sendMessage(toServer);
    }
    
    public void challengeAccept(int index){
        toServer= "challenge accept " + index;
        sendMessage(toServer);
    }
    
    /**
     * fromServer methods
     */
    public void ok(){
        // take next message out of queue;
        System.out.println("OK");
    }
    
    public void match(String otherWords){
        // do match stuff;
        setStringInHashMap(otherWords, "MATCH");
    }
    
    public void yourturn(String otherWords){
        // do yourturn stuff;
        setStringInHashMap(otherWords, "YOURTURN");
    }
    
    public void move(String otherWords){
        // do move stuff;
        setStringInHashMap(otherWords, "MOVE");
    }
    
    public void win(String otherWords){
        // do win stuff;
        setStringInHashMap(otherWords, "WIN");
    }
    
    public void loss(String otherWords){
        // do loss stuff;
        setStringInHashMap(otherWords, "LOSS");
    }
    
    public void draw(String otherWords){
        // do draw stuff;
        setStringInHashMap(otherWords, "DRAW");
    }
    
    public void challenge(String otherWords){
        // do challenge stuff;
        setStringInHashMap(otherWords, "CHALLENGE");
    }
    
    public void cancelled(String otherWords) {
    	// do challenge cancelled stuff
    	setStringInHashMap(otherWords, "CHALLENGE_CANCELLED");
    }
    
    public void playerlist(String otherWords) {
    	setListInHashMap(otherWords, "PLAYERLIST");
    }
    
    public void gamelist(String otherWords) {
    	setListInHashMap(otherWords, "GAMELIST");
    }
}
