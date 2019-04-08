package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection {
	
	//represent streams
    private ClientSocket clientSocket;
    //new queue for messages
    LinkedBlockingQueue<HashMap<String, Object>> messageQueue = new LinkedBlockingQueue<>();

    public Connection(String address, int port, ApplicationHandler applicationHandler) {
    	clientSocket = new ClientSocket(address, port, this);
    }
    
    public void close() {
    	clientSocket.close();
    }
    
    public LinkedBlockingQueue<HashMap<String, Object>> getQueue(){
        return messageQueue;
    }

    public void sendMessage(String toServer){
        // send to socket
    	System.out.println("toServer: " + toServer);
    	clientSocket.sendToServer(toServer);
    }
    
    public void checkInput(String fromServer){
    	System.out.println(fromServer);
    	// check server message word by word
    	String firstWord[] = fromServer.split(" ",2);
    	switch (firstWord[0]) {
        	case "OK":
        		System.out.println("OK: OK");
        		break;
        	case "ERR":
        		setErrorInHashMap(firstWord[1], "ERROR");
        		break;
        	case "SVR":
        		String secondWord[] = firstWord[1].split(" ", 2);
        		switch (secondWord[0]) {
                 	case "GAME":
                 		String thirdWord[] = secondWord[1].split(" ",2);
                 		String otherWords = thirdWord[1];
                 		String forthWord[] = otherWords.split(" ",2);
                 		if(forthWord[0].equals("CANCELLED")) {
                 			setStringInHashMap(forthWord[1], "CHALLENGE_CANCELLED");
                 		}else {
                 			setStringInHashMap(otherWords, thirdWord[0]);
                 		}
                 		break;
                 	case "PLAYERLIST": 
                 		setListInHashMap(secondWord[1], "PLAYERLIST");
                 		break;
                 	case "GAMELIST":
                 		setListInHashMap(secondWord[1], "GAMELIST");
                 		break;
                 	default:
                 		// not recognized
                 		System.out.println("Could not recognize command 2. \n" + "Command: " + firstWord[1]);
                 		break;
        			}
        		break;
        	case "Strategic":
        		System.out.println("STARTMESSAGE: " + fromServer);
        		break;
        	case "(C)":
        		System.out.println("STARTMESSAGE: " + fromServer);
        		break;
        default:
        // not recognized
        System.out.println("Could not recognize command 1. \n" + "Command: " + fromServer);
           break;
       }
   }

    public void setStringInHashMap(String stringMap, String messageType){
    	// Turn string which contains map into a HashMap
//    	System.out.println(stringMap);
        HashMap<String, Object> gameMap = new HashMap<>();    
        stringMap = stringMap.substring(1, stringMap.length()-1);
        String[] keyValuePairs = stringMap.split(", ");
        // enter items in the HashMap
        gameMap.put("MESSAGETYPE", messageType);
        for(String pair : keyValuePairs){
            String[] entry = pair.split(": ");
            gameMap.put(entry[0], entry[1].substring(1,entry[1].length()-1));
        }
        // Enter HashMap in message queue
        messageQueue.add(gameMap);
        System.out.println("setMap: " + gameMap.toString());

    }
    
    public void setListInHashMap(String stringList, String messageType) {
//    	System.out.println(stringMap);
    	// Turn the string which contains a list into a HashMap
        HashMap<String, Object> gameMap = new HashMap<>();
        stringList = stringList.substring(1, stringList.length() - 1);
        String[] stringItems = stringList.split(", ");
        ArrayList<String> output = new ArrayList<>();
        for(String item : stringItems) {
        	output.add(item.substring(1, item.length() - 1));
        }
        gameMap.put("MESSAGETYPE", messageType);
        gameMap.put("LIST", output);
        // Enter HashMap in message queue
        messageQueue.add(gameMap);
        System.out.println("setList: " + gameMap.toString());
    }
    
    public void setErrorInHashMap(String errorMessage, String messageType) {
    	// Turn the string into a HashMap
        HashMap<String, Object> gameMap = new HashMap<>();
        gameMap.put("MESSAGETYPE", messageType);
        gameMap.put("ERRORMESSAGE", errorMessage);
        System.out.println("setError: " + gameMap.toString());
    }
    
    /**
     * toSever methods
     */
    public void login(String username){
        sendMessage("login " + username);
    }
    
    public void logout(){
        sendMessage("logout");
    }
    
    public void getGamelist(){
        sendMessage("get gamelist");
    }
    
    public void getPlayerlist(){
        sendMessage("get playerlist");
    }
    
    public void subscirbe(String game){
        sendMessage("subscribe " + game);
    }
    
    public void move(int pos){
        sendMessage("move " + pos);
    }
    
    public void forfeit(){
        sendMessage("forfeit");
    }
    
    public void challengePlayer(String player, String game){
        sendMessage("challenge \"" + player + "\" \"" + game + "\"");
    }
    
    public void challengeAccept(int index){
        sendMessage("challenge accept " + index);
    }
}
