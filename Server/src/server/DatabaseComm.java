package server;

import java.sql.*;
import java.util.ArrayList;

/**
 * Database communication class, having a connection Server-Database.
 * DatabaseComm.java
 * 
 * @author Ervinas
 * @version 1.0
 */
public class DatabaseComm {

	public DatabaseComm() {
		
	}
	
	/**
	 * Main Server-Database security checking method
	 * 
	 * @param auths Authentication codes from Client
	 * @param ipAddress Connected client's ip address
	 * @return A boolean value true if there are all matches positive, false if failed
	 */
	protected static boolean scanForAuthCodes(ArrayList<String> auths, String ipAddress){
		
		String project_key = null;
		String user_key = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    Connection con = DriverManager.getConnection("jdbc:mysql://mysql:" + Config.getPortDB() +  "/" + Config.getDatabase() + "?"
		    		+ "user=" + Config.getUsername() + "&password=" + Config.getPassword());
		    
		    String query = "SELECT p.project_key, u.user_key FROM (select project_key from project WHERE project_key = ?) p, (select user_key from user WHERE user_key = ?) u";
		       	PreparedStatement preparedStatement = con.prepareStatement(query);
		       
		       	for(int i = 0; i < auths.size() ; i++ ) {
		       		preparedStatement.setString(i+1, auths.get(i));
		       	}
	
		       	ResultSet rs = preparedStatement.executeQuery();
		          
			while(rs.next())  
			{	
				project_key = rs.getString("project_key");
				user_key = rs.getString("user_key");
			}
			if(auths.get(0).equals(project_key) && auths.get(1).equals(user_key) && isIpAddressMatched(project_key, ipAddress) == true) {
				System.out.println("Authentication codes valid");
				project_key = null;
				user_key = null;
				rs.close();
				con.close(); 
				return true;
			}
			project_key = null;
			user_key = null;
			auths.clear();
			rs.close();
			con.close(); 			

		} catch (ClassNotFoundException e) {
			System.out.println("Internal error");
		} catch (SQLException e) {
			System.out.println("Database error. Check your database login/password and name.");
		}  
		return false;	
	}
	
	/**
	 * Method which gets divider symbol from database from unique project
	 * 
	 * @param user_key A unique user key from client
	 * @return Divider from database
	 */
	protected String getDividerFromDatabase(String user_key){
		
		String divider = null;
	    Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://mysql:" + Config.getPortDB() +  "/" + Config.getDatabase() + "?"
					+ "user=" + Config.getUsername() + "&password=" + Config.getPassword());
			
		    String getUserID = "SELECT user_id FROM user WHERE user_key = ?";
		    PreparedStatement prepstmt = con.prepareStatement(getUserID);
		    prepstmt.setString(1, user_key);
		    ResultSet userIDrs = prepstmt.executeQuery();
		    String userID = null;
		    
		    while(userIDrs.next()) {
		    		userID = userIDrs.getString("user_id");
		    }	
	 
		    String query = "SELECT divider FROM project WHERE user_id = ?";
		    PreparedStatement preparedStatement = con.prepareStatement(query);
		    preparedStatement.setString(1, userID);
		    ResultSet rs = preparedStatement.executeQuery();
		    
		    while(rs.next()) {
		    		divider = rs.getString("divider");
		    }
		    
		    con.close();
		    rs.close();			
	
		} catch (SQLException e) {
			System.out.println("Database error. Check your database login/password and name.");
		}
		return divider;
	    
	}

	/**
	 * addLogLineToDatabase method adds log line to database, registering an event
	 * 
	 * @param line A line from Log
	 * @param project_key Client's unique project key
	 * @param type Log file type
	 * @return Last inserted id to database of Log line
	 */
	protected int addLogLineToDatabase(String line, String project_key, String type){
		
	    Connection con;
	    int last_inserted_id = 0;
		try {
			con = DriverManager.getConnection("jdbc:mysql://mysql:" + Config.getPortDB() +  "/" + Config.getDatabase() + "?"
					+ "user=" + Config.getUsername() + "&password=" + Config.getPassword());
			
			String query = "INSERT INTO event ( project_id, type ) VALUES (?,?)";
			String select = "SELECT project_id FROM project WHERE project_key = ?";
			
			PreparedStatement preparedStatement1 = con.prepareStatement(select);
			preparedStatement1.setString(1, project_key);
			ResultSet rs = preparedStatement1.executeQuery();
			
			int project_id = 0;
			
		    if(rs.next()) {
	    			project_id = rs.getInt("project_id");
		    }
		    
			PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, project_id);
			preparedStatement.setString(2, type);
			preparedStatement.executeUpdate();
					
	        ResultSet rs1 = preparedStatement.getGeneratedKeys();
	        
	        
	        
	        if(rs1.next())
	        {
	            last_inserted_id = rs1.getInt(1);
	        }
	        
	        query = null;
			select = null;
			project_id = 0;
			
	        rs.close();
			con.close();		
			
			
		} catch (SQLException e) {
			System.out.println("Database error. Check your database login/password and name.");
		}
		return last_inserted_id;
		
	}
	
	/**
	 * Method adds separated line variables to database
	 * 
	 * @param temp Temporary variable
	 * @param lastInsertedID Last inserted value to database
	 * @param type Log file type
	 */
	protected void AddEventDetailsToDatabase(String[] temp, int lastInsertedID, String type){

	    Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://mysql:" + Config.getPortDB() +  "/" + Config.getDatabase() + "?"
					+ "user=" + Config.getUsername() + "&password=" + Config.getPassword());
			
		    int index = 1;
			for(int i = 0 ; i < temp.length ; i++) {
			    String query = "INSERT into event_detail (event_id,`index`,value) VALUES (?,?,?)"; 
			    PreparedStatement stmt1 = con.prepareStatement(query);
			    stmt1.setInt(1, lastInsertedID);
			    stmt1.setInt(2, index);
			    stmt1.setString(3, temp[i]);
			    stmt1.executeUpdate();
			    query = null;
			    index = index + 1;
			    
			}
			con.close();			
		} catch (SQLException e) {
			System.out.println("Database error. Check your database login/password and name.");
		}
	}

	/**
	 * 
	 * @param project_key Client's unique project key
	 * @param connectedIp Client's ip 
	 * 
	 * @return A boolean value true if ip address is matched one from database, false if failed
	 */
	protected static Boolean isIpAddressMatched(String project_key, String connectedIp) {
		
		String ip = null;
		
	    try {
			Connection con = DriverManager.getConnection("jdbc:mysql://mysql:" + Config.getPortDB() +  "/" + Config.getDatabase() + "?"
					+ "user=" + Config.getUsername() + "&password=" + Config.getPassword());
			
			String query = "SELECT ip FROM project WHERE project_key = ? ";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, project_key);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				ip = rs.getString("ip");
			}
			
			if(connectedIp.equals(ip)) {
				con.close();
				rs.close();
				stmt.close();
				System.out.println("IP Matched");
				return true;
			}
			con.close();
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println("Database error. Check your database login/password and name.");
		}
	    
		return false;
	    
	    
	    
	}
}
