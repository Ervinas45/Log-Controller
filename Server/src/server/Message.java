package server;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Message object class, mainly controlling Client's message
 * Message.jada
 * 
 * @author Ervinas
 * @version 1.0
 */
public class Message {
	
	private String line;
	private String type;
	private String divider;
	
	private String project_key;
	private String user_key;
	
	private DatabaseComm dbComm;
	
	/**
	 * Message object constructor which preparing variables on creating
	 * 
	 * @param array Message array (line and type)
	 * @param auths Authentication variables from database
	 * @throws SQLException Is thrown if there was a problem communicating with Database
	 */
	public Message(String[] array, ArrayList<String> auths) throws SQLException{
		
		this.dbComm = new DatabaseComm();
		
		this.line = array[0];
		this.type = array[1];
		
		this.project_key = auths.get(0);
		this.user_key = auths.get(1);
		
		this.divider = dbComm.getDividerFromDatabase(this.user_key);
		this.execute(this.line, this.type, this.dbComm);

	}
	
	/**
	 * Method adds Log's separated variables to database
	 * 
	 * @param line Line from Log file
	 * @param type Type of Log file
	 * @param dbComm Database communication object
	 * @throws SQLException Is thrown if there was a problem communicating with Database
	 */
	private void execute(String line, String type, DatabaseComm dbComm) throws SQLException{

		int lastInsertedID = dbComm.addLogLineToDatabase(line, this.project_key, this.type);
		String[] temp = this.separateVariables(this.divider);
		dbComm.AddEventDetailsToDatabase(temp, lastInsertedID, type);
		
	}

	/**
	 * separateVariables method used for log variables separation
	 * 
	 * @param divider Used divider from database to separate
	 * 
	 * @return Separated log message by divider
	 */
	private String[] separateVariables(String divider) {
			
		String tempLine = this.line;
		
		String[] variables = tempLine.split(divider);
		
		return variables;
	
	}
	
}
