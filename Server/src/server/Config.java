package server;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Configuration class
 * Config.java
 * Purpose : Used to open configuration file json and extract parameters from it
 * 
 * @author Ervinas
 * @version 1.0
 */
public class Config {
	
	protected static JSONParser parser;
	protected static Object obj;
	protected static JSONObject jsonObject;
	protected static JSONArray array;
	
	private static String host;
	private static int port;
	private static int portDB;
	private static String database;

	private static String username;
	private static String password;
	
	private static InetAddress address;
	
	public Config(){
		this.getJsonParams();
	}
	
	/**
	 * getJsonParams method which gets parameters from configuration file
	 * 
	 */
	private void getJsonParams(){
		
		try {
		    	JSONParser parser = new JSONParser();
		    	Object obj = parser.parse(new FileReader("server_config.json"));
		    	jsonObject = (JSONObject) obj;
			host = (String) jsonObject.get("hostname");
			port = Integer.parseInt((String) jsonObject.get("port"));
			portDB = (Integer.parseInt((String) jsonObject.get("port_db")));
			database = (String) jsonObject.get("database");
			username = (String) jsonObject.get("username");
			password = (String) jsonObject.get("password");		
		} catch (FileNotFoundException e) {
			System.out.println("Configuration file was not found in the same folder! Client closed.");
		} catch (IOException e) {
			System.out.println("Something went bad with I/O operation, closing...");
		} catch (ParseException e) {
			System.out.println("Parsing error, closing...");
		}
	}
	
	/**
	 * 
	 * @return Server hostname
	 */
	protected static String getHost(){
		return host;
	}
	
	/**
	 * 
	 * @return Server listening port
	 */
	protected static int getPort() {
		return port;
	}
	
	/**
	 * 
	 * @return Server address
	 */
	protected static InetAddress getAddress() {
		return address;
	}
	
	/**
	 * 
	 * @return Database port
	 */
	public static int getPortDB() {
		return portDB;
	}
	
	/**
	 * 
	 * @return Database name
	 */	
	protected static String getDatabase() {
		return database;
	}
	
	/**
	 * 
	 * @return Database username
	 */
	protected static String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @return Database password
	 */
	protected static String getPassword() {
		return password;
	}
}
