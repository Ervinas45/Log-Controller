package client;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Configuration class, which take care of json file from Client side, it take all parameters and prepares them for further use
 * Config.java
 * 
 * @author Ervinas
 * @version 1.0
 */
public class Config {
	
	private static JSONParser parser;
	private static Object obj;
	private static JSONObject jsonObject;
	private static JSONArray array;
	private static String host;
	private static int port;
	private static InetAddress address;
	
	private static String project_key;
	private static String user_key;
	
	private String fileName;
	
	/**
	 * Constructor takes fileName which you want to be watched and stores him inside this class. GetJsonParams() is a method to boot up this class, load every variable
	 * and store it
	 * @param fileName The name of file which will be watched
	 * @throws FileNotFoundException Is being shown only when Config file or File you want to watch is not found
	 * @throws IOException I/O Exception
	 */
	public Config(String fileName) throws FileNotFoundException, IOException, ParseException{
		this.fileName = fileName;
		this.getJsonParams();
	}
	
	/**
	 * 
	 * @throws FileNotFoundException Is being shown only when Config file or File you want to watch is not found
	 * @throws IOException I/O Exception
	 * @throws ParseException Is thrown if there was a problem parsing variables from configuration file
	 */
	private void getJsonParams() throws FileNotFoundException, IOException, ParseException{

		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(fileName));
	    		jsonObject = (JSONObject) obj;
	    		array = (JSONArray) jsonObject.get("watchlist");
			host = (String) jsonObject.get("hostname");
			port = Integer.parseInt((String) jsonObject.get("port"));
			project_key = (String) jsonObject.get("project_key");
			user_key = (String) jsonObject.get("user_key");
			address = InetAddress.getByName(host);
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
	 * @return Project_key from this class
	 */
	protected static String getProjectKey() {
		return project_key;
	}

	/**
	 * 
	 * @return User_key from this class
	 */
	protected static String getUserKey() {
		return user_key;
	}

	/**
	 * 
	 * @return Host this class
	 */
	protected static String getHost(){
		return host;
	}

	/**
	 * 
	 * @return Port from this class
	 */
	protected static int getPort() {
		return port;
	}

	/**
	 * 
	 * @return Address from this class
	 */
	protected static InetAddress getAddress() {
		return address;
	}

	/**
	 * 
	 * @return JsonObject from this class
	 */
	protected static JSONObject getJsonObject() {
		return jsonObject;
	}
	
	/**
	 * 
	 * @return Parser from class
	protected static JSONParser getParser() {
		return parser;
	}

	/**
	 * 
	 * @return Json file as object
	 */
	protected static Object getObj() {
		return obj;
	}

	/**
	 * 
	 * @return Array of files from configuration file
	 */
	protected static JSONArray getArray() {
		return array;
	}
	
}
