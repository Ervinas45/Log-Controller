package manager;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ActionListeners {

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public ActionListeners(){
		
	}
	
	public static void closeWindow(Container container) {
		container.setVisible(false);
	}

	public static ArrayList<String> refreshTable(DefaultTableModel model, ArrayList<String> titles, ArrayList<String> projects, Map<Integer, Map<String, String>> events, Object[] row, JTable table) throws SQLException {
		model = new DefaultTableModel();
		table.setModel(model);
		model.setRowCount(0);
		projects.clear();
		titles.clear();
		events.clear();
		DatabaseComm.getColumnNamesToPanel(model, titles);
		projects = DatabaseComm.AddLogsToArrayReturnProjectNames(events);
		DatabaseComm.fillDataToPanel(model, events, titles, row);
		DatabaseComm.resizeColumnWidth(table);
		return projects;
	}
	
	public static void filter(JTable table, ArrayList<String> checkedItemList, DefaultListModel<String> projectsToFilter, String dateFrom, String dateUntil, DefaultTableModel model) throws SQLException {
		
		
		Connection con = DriverManager.getConnection("jdbc:mysql://" + DatabaseConnectionDialog.address + ":" + DatabaseConnectionDialog.port +  "/logctrl?user=" + DatabaseConnectionDialog.username + "&password=" + DatabaseConnectionDialog.password );
		String sql = sql(checkedItemList, projectsToFilter, dateFrom, dateUntil);
		
		System.out.println(sql);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		int i = 0;
		if(!checkedItemList.isEmpty()) {
			for(String item : checkedItemList) {
				i += 1;
				stmt.setString(i, item);
			}
		}
		if(!projectsToFilter.isEmpty()) {
			for(int b = 0 ; b < projectsToFilter.size() ; b++){
				i += 1;
	            stmt.setString(i, projectsToFilter.getElementAt(b));
	        }
		}
		System.out.println(dateFrom);
		System.out.println(dateUntil);
		System.out.println(i);
		if(!isEmpty(dateFrom) || !isEmpty(dateUntil)) {
			if(!isEmpty(dateFrom)) {
				i+=1;
				stmt.setString(i, dateFrom);
			}
			if(!isEmpty(dateUntil)) {
				i+=1;
				stmt.setString(i, dateUntil);
			}
		}	
		
		model = new DefaultTableModel();
		table.setModel(model);
		model.setRowCount(0);
		for(String item: checkedItemList) {
			model.addColumn(item);
		}
	
		
		ResultSet rs2 = stmt.executeQuery();
		
		Map<Integer, Map<String, String>> events = new HashMap<Integer, Map<String, String>>();
		
		while(rs2.next()) {
			int eventId = rs2.getInt("Event_id");
			if(!events.containsKey(eventId)) {
				HashMap<String, String> temp = new HashMap<String, String>();	
				temp.put("Name", rs2.getString("name"));
				temp.put("Event_id", rs2.getString("event_id"));
				temp.put("Date", rs2.getString("date"));
				temp.put(rs2.getString("title"), rs2.getString("value"));
				events.put(rs2.getInt("Event_id"), temp);
			}
			Map<String, String> event = events.get(eventId);
			event.put(rs2.getString("title"), rs2.getString("value"));
		}
		Object[] row = null;
		DatabaseComm.fillDataToPanel(model, events, checkedItemList, row);
		DatabaseComm.resizeColumnWidth(table);
		i = 0;


	}
	
	public static String toArray(ArrayList<String> array) {
		String sqlArray = "";
		String add = "?";
		
		int size = array.size();
		
		for(int i = 0; i < size; i++) {
			if(i == size - 1) {
				sqlArray += add;
			}
			else {
				sqlArray += add + ",";
			}
		}
		
		System.out.println("Made array: " + sqlArray);
		
		
		return sqlArray;
	}
	
	public static String toArray(HashMap<Integer, String> map) {
		String sqlArray = "";
		String add = "?";
		
		int size = map.size();
		
		for(int i = 0; i < size; i++) {
			if(i == size - 1) {
				sqlArray += add;
			}
			else {
				sqlArray += add + ",";
			}
		}
		
		System.out.println("Made array: " + sqlArray);
		return sqlArray;
	}
	
	
	public static String toArray(DefaultListModel array) {
		String sqlArray = "";
		String add = "?";
		
		int size = array.size();
		
		for(int i = 0; i < size; i++) {
			if(i == size - 1) {
				sqlArray += add;
			}
			else {
				sqlArray += add + ",";
			}
		}
		
		System.out.println("Made array: " + sqlArray);
		
		
		return sqlArray;
	}	

	public static String sql(ArrayList<String> checkedItemList, DefaultListModel projectsToFilter, String dateFrom, String dateUntil) {
		
		String sql = "SELECT e.event_id, e.date, d.value, t.title, p.name, p.project_id\n" + 
				"FROM event AS e\n" + 
				"JOIN project AS p on p.project_id = e.project_id\n" + 
				"JOIN event_detail AS d on d.event_id = e.event_id\n" + 
				"JOIN detail_titles AS t on (t.project_id = e.project_id AND t.index = d.index)\n";
		
		if(!checkedItemList.isEmpty() || !projectsToFilter.isEmpty() || !dateFrom.isEmpty() || !dateUntil.isEmpty()) {
			boolean andRequired = false;
			sql += "WHERE\n";
			if(!checkedItemList.isEmpty()) {
				sql += "t.title IN (" + toArray(checkedItemList) + ") ";
				andRequired = true;
			}
			if(!projectsToFilter.isEmpty()) {
				if(andRequired) {
					sql += "AND\n";
				}
				sql += "p.name IN (" + toArray(projectsToFilter) + ") ";
				andRequired = true;
			}
			if(!isEmpty(dateFrom)) {
				if(andRequired) {
					sql += "AND\n";
				}
				sql += "e.date >= ? ";
				andRequired = true;
			}
			if(!isEmpty(dateUntil)) {
				if(andRequired) {
					sql += "AND\n";	
				}
				sql += "e.date <= ? ";
				andRequired = true;
			}
		}
		else {
			return sql;
		}
		return sql;
	}
	
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
