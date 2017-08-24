package client;

import java.io.IOException;
import java.net.Socket;
import org.json.simple.parser.ParseException;
/**
 * Client.java
 * Purpose : Server-Client Log Controller program Client side to run Client.
 * 
 * @author Ervinas
 * @version 1.0
 */
public class Client {
	
	/**
	 * This method starts the Client, creates Configuration object, socket, serverComm object. Sends authentication codes from client configuration file to server
	 * and waits for answer from Server, the value if true, it will create FileProcessing object which will invoice WatcherThread for the files you want to watch.
	 * 
	 * @param args Not used
	 */
	public static void main(String[] args){
		
	System.out.println("Starting Client..");
    Socket socket = null;
	    	try {
			new Config(args[0]);
			socket = new Socket(Config.getAddress(), Config.getPort());   
	        ServerComm serverComm = new ServerComm(socket);
	        if(serverComm.sendAuth() == true) {
	        		new FileProcessing(serverComm);
		  		System.out.println("Client connected to server : " + socket.isConnected());
		    }
		    	else {
		    		socket.close();
				System.out.println("Client was blocked from connection, because authentication process have failed. Client closed.");
	    		}
			} catch (ParseException | IOException e) {
				System.out.println("Communication error. Server probably is closed.");
			} catch (NullPointerException ex) { 
				System.out.println("There was an error loading config file! Closing...");
			}
   	}
}
