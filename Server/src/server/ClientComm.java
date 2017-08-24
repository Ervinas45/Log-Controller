package server;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Client communication class.
 * ClientComm.java
 * Purpose : Server-Client Authentication process, security management.
 * 
 * @author Ervinas
 * @version 1.0
 */
public class ClientComm extends ServerThread{
	
	
	public ClientComm(Socket socket) throws ClassNotFoundException, IOException {
		super(socket);	
	}
	
	/**
	 * checkAuth method for checking authentication codes from Database with client
	 * 
	 * @return A boolean value true if auths and ip address matched in database, false if it's failed
	 * 
	 * @throws IOException I/O Exception is thrown if Config file managed incorrectly
	 * @throws ClassNotFoundException Is thrown when Config or DatabaseComm classes are not found
	 * @throws SQLException Is thrown if there was a problem communicating with Database
	 */
	public boolean checkAuth() throws IOException, ClassNotFoundException, SQLException {
		ArrayList<String> auths = new ArrayList<String>();
				
		String[] authsFromClient = (String[]) ois.readObject();
		
		for(String auth : authsFromClient) {

			auths.add(auth);
		}				
		this.authCodes = auths;
		if(DatabaseComm.scanForAuthCodes(auths, remoteIp) == true) {
			oos.writeBoolean(true);
			oos.flush();
			return true;
		}
		else {
    			oos.writeBoolean(false);
    			oos.flush();
			return false;
		}		
	}	
}
