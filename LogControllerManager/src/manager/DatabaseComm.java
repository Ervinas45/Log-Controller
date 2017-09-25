package manager;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class DatabaseComm {

	public DatabaseComm(){
		
	}
	
	public static ArrayList<String> AddLogsToArrayReturnProjectNames(Map<Integer, Map<String, String>> events) throws SQLException {
		ArrayList<String> clientProjects = new ArrayList<String>();
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		System.out.println("Connected");
			String sql = "SELECT e.event_id, e.date AS _date, d.value, t.title, p.name\n" + 
					"FROM event AS e\n" + 
					"JOIN project AS p on p.project_id = e.project_id\n" +
					"JOIN event_detail AS d on d.event_id = e.event_id\n" + 
					"JOIN detail_titles AS t on (t.project_id = e.project_id AND t.index = d.index)";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()) {
			int eventId = rs.getInt("event_id");
			if (!events.containsKey(eventId)) {
				HashMap<String, String> temp = new HashMap<String, String>();	
				temp.put("Name", rs.getString("name"));
				temp.put("Event_id", rs.getString("event_id"));
				temp.put("Date", rs.getString("_date"));
				events.put(eventId, temp);
			}
			if( !clientProjects.contains(rs.getString("Name"))) {
				clientProjects.add(rs.getString("Name"));
			}
			Map<String, String> event = events.get(eventId);
			event.put(rs.getString("title"), rs.getString("value"));
		}
		rs.close();
		con.close();
		return clientProjects;
	}
	
	public static ArrayList<String> getNewTitles() throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		String getTitles = "SELECT DISTINCT(title) FROM detail_titles";
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		ResultSet rs2 = preparedStatement1.executeQuery();
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("Name");
		titles.add("Event_id");
		titles.add("Date");
		while(rs2.next()) {
			String title = rs2.getString("title");
			titles.add(title);
		}
		preparedStatement1.close();
		con.close();
		return titles;
	}
	
	public static void getColumnNamesToPanel(DefaultTableModel model, ArrayList<String> titles) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		String getTitles = "SELECT DISTINCT(title) FROM detail_titles";
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		ResultSet rs2 = preparedStatement1.executeQuery();
		titles.add("Name");
		titles.add("Event_id");
		titles.add("Date");
		model.addColumn("Name");
		model.addColumn("Event_id");
		model.addColumn("Date");
		while(rs2.next()) {
			String title = rs2.getString("title");
			titles.add(title);
			model.addColumn(title);
		}
		preparedStatement1.close();
		con.close();
	}
	
	public static void resizeColumnWidth(JTable table) {
	    TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	    		int width = 100;
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width = 800;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public static void fillDataToPanel(DefaultTableModel model, Map<Integer, Map<String, String>> events, ArrayList<String> titles, Object[] row) {
		Set<Integer> keys = events.keySet();
		Iterator<Integer> iter = keys.iterator();
		while(iter.hasNext()) {
			int eventID = iter.next();
			Map<String, String> event = events.get(eventID);
			row = new Object[titles.size()];
			for (int x = 0; x < titles.size(); x++) {
				row[x] = event.get(titles.get(x));
			}
			model.addRow(row);
		}
	}
	
	public static HashMap<String, Map<String,String>> getProjectInfo() throws SQLException {
		
		HashMap<String, Map<String,String>> hmap = new HashMap<String, Map<String,String>>();
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		String getTitles =
				"SELECT name, project_key, ip FROM `project`\n" + 
				"WHERE user_id = '1'";
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		ResultSet rs = preparedStatement1.executeQuery();
		
		while(rs.next()){
			
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("project_key", rs.getString("project_key"));
			temp.put("ip", rs.getString("ip"));
			hmap.put(rs.getString("name"), temp);
			
		}
		rs.close();
		con.close();
		return hmap;
	}
	
	public static HashMap<Integer, String> getTitles(int projectID) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		String getTitles = "SELECT `index`, title FROM detail_titles WHERE project_id = ?";
				
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		preparedStatement1.setInt(1, projectID);
		ResultSet rs = preparedStatement1.executeQuery();
		
		HashMap<Integer, String> hmap = new HashMap<Integer, String>();
		
		while(rs.next()) {
			hmap.put(rs.getInt("index"), rs.getString("title"));
		}
		rs.close();
		con.close();
		return hmap;
	}
	
	public static int getProjectId(String projectName) throws SQLException {
	
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		String getTitles = "SELECT project_id FROM `project`\n" + 
				"WHERE name = ?";
				
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		preparedStatement1.setString(1, projectName);
		ResultSet rs = preparedStatement1.executeQuery();
		
		int projectID = 0;
		
		while(rs.next()) {
			projectID = rs.getInt("project_id");
		}
		rs.close();
		con.close();
		return projectID;
	}
	
	public static void changeTitleNames(HashMap<Integer, String> titles, int project_id) throws SQLException {
		
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<String> values = new ArrayList<String>();
		
		for ( Integer key : titles.keySet() ) {
			indexes.add(key);
			values.add(titles.get(key));
		}
		System.out.println(indexes.size());
		System.out.println(values.size());
		
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		
		for(int i = 0; i < titles.size(); i++) {
			
			String sql = "UPDATE detail_titles\n" + 
					"SET title = ?\n" + 
					"WHERE project_id = ? AND `index` = ?";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, values.get(i));
			stmt.setInt(2, project_id);
			stmt.setInt(3, indexes.get(i));
			
			stmt.executeUpdate();
			stmt.close();
		}
		con.close();	
	}
}
