package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Mainly working thread to catch incomming connected client message
 * ServerThread.java
 * 
 * @author Ervinas
 * @version 1.0
 */
class ServerThread extends Thread {  
	
	public String name;

    String line = null;
    BufferedReader is = null;
    PrintWriter os = null;
    int clientID;
    public Socket socket = null;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
	public ArrayList<String> authCodes;

	private InetAddress ip;
	public String remoteIp;
		
	/**
	 * ServerThread Constructor used to set all initial variables for further working
	 * 
	 * @param socket Connected client socket
	 * @throws ClassNotFoundException Is thrown if there are problems with Classes
	 * @throws IOException I/O Exception is thrown if ObjectOutputStream header was invalid
	 */
	public ServerThread(Socket socket) throws ClassNotFoundException, IOException{
    	
		
    		this.setName("Client");
    		name = this.getName();
		this.socket = socket;
		this.ip = socket.getInetAddress();
		this.remoteIp = ip.getHostAddress();
		this.authCodes = new ArrayList<String>();
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(socket.getInputStream());

    }

	/**
	 * ServerThread run method with infinite while loop. It's a working loop thread used to get Client's sockets for further use
	 */
    public void run() {
    		
    	@SuppressWarnings("unused")
		int messageNumber = 0;
    	
	    try {
	    	
	        while(true) {
		    		String[] variables = (String[]) ois.readObject();
		    		System.out.println("Message from : " + ip);
		    		new Message(variables, this.authCodes);
	        }
	    }
	    catch(NullPointerException e)
	    {
	        System.out.println("Client "+remoteIp+" Closed");
	    } catch (ClassNotFoundException e) {
	    		System.out.println("Client "+remoteIp+" Closed");
		} catch (IOException e) {
			System.out.println("Client "+remoteIp+" Closed");
		} catch (SQLException e) {
			System.out.println("Client "+remoteIp+" Closed");
		}
	    finally
	    {    
		    try{
		        System.out.println("Connection Closing..");
		        if (is != null){
		            is.close(); 
		            System.out.println("Socket Input Stream Closed");
		        }
		
		        if(os!=null){
		            os.close();
		            System.out.println("Socket Out Closed");
		        }
		        if (socket!=null){
		        socket.close();
		        System.out.println("Socket was closed");
		        }
		
		        }
		    catch(IOException ie){
		        System.out.println("Socket Close Error");
		    }
	    }
    }
}

