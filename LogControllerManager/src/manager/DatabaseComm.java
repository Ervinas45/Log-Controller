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
	
	public static ArrayList AddLogsToArrayReturnProjectNames(Map<Integer, Map<String, String>> events) throws SQLException {
		ArrayList<String> clientProjects = new ArrayList<String>();
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql:3306/logctrl?user=logctrl&password=kavospertraukele");
		System.out.println("Connected");
		String sql = "SELECT e.event_id, d.value, t.title, p.name\n" + 
				"FROM event AS e\n" + 
				"JOIN project AS p on p.project_id = e.project_id\n" +
				"JOIN event_detail AS d on d.event_id = e.event_id\n" + 
				"JOIN detail_titles AS t on (t.project_id = e.project_id AND t.index = d.index)";
				//"WHERE t.project_id = 10";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()) {
			int eventId = rs.getInt("event_id");
			if (!events.containsKey(eventId)) {
				HashMap<String, String> temp = new HashMap<String, String>();	
				temp.put("Name", rs.getString("name"));
				temp.put("Event_id", rs.getString("event_id"));
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
	
	public static void getColumnNamesToPanel(DefaultTableModel model, ArrayList<String> titles) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql:3306/logctrl?user=logctrl&password=kavospertraukele");
		String getTitles = "SELECT DISTINCT(title) FROM detail_titles";
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		ResultSet rs2 = preparedStatement1.executeQuery();
		titles.add("Name");
		titles.add("Event_id");
		model.addColumn("Name");
		model.addColumn("Event_id");
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
	    		int width = 100; // Min width
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
	
	

}
