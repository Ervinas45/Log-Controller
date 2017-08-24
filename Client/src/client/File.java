package client;

/**
 * File class is used to create a file from given path in configuration file. Used as model.
 * File.java
 * 
 * @author Ervinas
 * @version 1.0
 */
public class File {
	
	private String path;
	private String type;
	
	/**
	 * Constructor uses these value to store them inside this object
	 * 
	 * @param path Path to file in client's configuration file
	 * @param type Type of file which has to be written in configuration file
	 */
	public File(String path, String type){
		this.path = path;
		this.type = type;
	}
	
	/**
	 * Gets path
	 * @return Path from this class
	 */
	protected  String getPath() {
		return path;
	}
	
	/**
	 * Gets type
	 * @return Type from this class
	 */
	protected  String getType() {
		return type;
	}

	
	
	
}
