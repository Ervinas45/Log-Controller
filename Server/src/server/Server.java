package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.parser.*;

/**
 * Log Controller
 * Server.java
 * Purpose: Boots up Server for listening for connections.
 *
 * @author Ervinas
 * @version 1.0
 */

public class Server {

	/**
	 * Main method for Server class
	 * 
	 * @param args Used for passing configuration file path to open it
	 */
	public static void main(String args[]) throws ParseException{
	
	    ServerSocket serverSocket = null;
	    ArrayList<ClientComm> clients = new ArrayList<ClientComm>();
	    
	    try{
	    		new Config();
	        serverSocket = new ServerSocket(Config.getPort());
	        System.out.println("Server started");
	    }
	    catch(IOException e){
		    System.out.println("Server error");
	    }

	    while(true){
	        try{
	            Socket socket = serverSocket.accept();
	            System.out.println("New connection : " + (socket.getInetAddress()).getHostAddress() + ", authenticating...");
	            ClientComm clientComm = new ClientComm(socket);
	            clients.add(clientComm);
	   
	            if(clientComm.checkAuth() == false) {
	            		System.out.println(socket.getInetAddress() + " authentification failed");
	            		socket.close();
	            }
	            else {
	            		System.out.println("Authentification process complete");
	            		clientComm.start();
	            }  
	        }
		    catch(Exception e){
		        System.out.println("Connection Error");
		    }
	    }
	
	}

}