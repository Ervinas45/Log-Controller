package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Server communication class allowing Client communicate with Server through sockets
 * @author Ervinas
 * @version 1.0
 */
public class ServerComm{
	
	private Socket socket;
	public ArrayList<String[]> sendObjects = new ArrayList<String[]>();
	public ObjectOutputStream oos;
	public ObjectInputStream ois;
	
	/**
	 * 
	 * @return ObjectOutputStream
	 */
	public ObjectOutputStream getOos() {
		return oos;
	}
	
	/**
	 * 
	 * @return ObjectInputStream
	 */
	public ObjectInputStream getOis() {
		return ois;
	}

	/**
	 * Contructor which will save socket to variable
	 * @param socket Client's connected socket
	 * @throws UnknownHostException Error if there was a problem with Client socket
	 * @throws IOException I/O Exception
	 */
	public ServerComm(Socket socket) throws UnknownHostException, IOException {

		this.socket = socket;
		
	}

	/**
	 * 
	 * @param line Last line took from the watched file
	 * @param type Type of Log file which was watched
	 * @throws IOException I/O Exception
	 */
	protected void sendLine(String line, String type) throws IOException{
		
		
		String[] a = {line,type};
		
		sendObjects.add(a);

		oos.writeObject(a);
		oos.flush();
		
	    	System.out.println("Sending line: " + line);
	    	System.out.println("Sending type: " + type);
	}
	
	/**
	 * 
	 * @return Client's socket
	 */
	public Socket getSocket() {
		return this.socket;
	}
	
	/**
	 * 
	 * @return A boolean value true if the authentication was successfull, false if not.
	 * @throws IOException I/O Exception
	 */
	public boolean sendAuth() throws IOException {
		
		this.setOos();
		String project_key = Config.getProjectKey();
		String user_key = Config.getUserKey();
		
		String[] auths = {project_key, user_key};
		oos.writeObject(auths);
		oos.flush();
		
        System.out.println("Authenticating...");
		this.setOis(); // wait
		if(getOis().readBoolean() == true) {
			System.out.println("Authentication successfull!");
			return true;
		}
		System.out.println("Authentication failed, dropping connection.");  
		return false;
	}

	/**
	 * Creating ObjectOutputStream method
	 */
	protected void setOos() {
		try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Unexpected error related with I/O.");
		};
	}

	/**
	 * Creating ObjectInputStream method
	 */
	protected void setOis() {
		try {
			this.ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Unexpected error related with I/O.");
		};
	}
	
	
}
