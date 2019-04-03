package application;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection {
	
	//represent streams
    public String toServer;
    public String fromServer;
    private ClientSocket clientSocket;
    //new queue for messages
    LinkedBlockingQueue<HashMap<String, String>> messageQueue = new LinkedBlockingQueue<>();

    private Connection(String address, int port) {
    	clientSocket = new ClientSocket(address, port, this);
    	
    }
    
    public void close() {
    	clientSocket.close();
    }
    
    public LinkedBlockingQueue<HashMap<String, String>> getQueue(){
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
                            challenge(otherWords);
                            break;
                        default:
                            // not recognized
                            System.out.println("Could not recognize command. /n" + "Command: " + secondWord[1]);
                            break;
                    }
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
    	// Turn string into a HashMap
        HashMap<String, String> gameMap = new HashMap<>();    
        stringMap.substring(1, stringMap.length()-1);
        String[] keyValuePairs = stringMap.split(",");
        gameMap.put("MESSAGETYPE", messageType);
        for(String pair : keyValuePairs){
            String[] entry = pair.split(":");
            gameMap.put(entry[0].trim(), entry[1].trim().substring(1,entry[1].length()));
        }
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
}
