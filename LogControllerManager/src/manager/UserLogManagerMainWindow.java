package manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;


import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.GridBagConstraints;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UserLogManagerMainWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogManagerMainWindow frame = new UserLogManagerMainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public UserLogManagerMainWindow() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setSize(1200,1024);
		contentPane.setLayout(null);
		
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql:3306/logctrl?user=logctrl&password=kavospertraukele");
		System.out.println("Connected");
		String sql = "SELECT e.event_id, d.value, t.title, p.name\n" + 
				"FROM event AS e\n" + 
				"JOIN project AS p on p.project_id = e.project_id\n" +
				"JOIN event_detail AS d on d.event_id = e.event_id\n" + 
				"JOIN detail_titles AS t on (t.project_id = e.project_id AND t.index = d.index)" +
				"WHERE t.project_id = 11";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(101, 41, 1000, 200);
		contentPane.add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel(); 
		
		table_1 = new JTable(model);
		scrollPane.setViewportView(table_1);
		
		Map<Integer, Map<String, String>> events = new HashMap<Integer, Map<String, String>>();
		
		while(rs.next()) {
			int eventId = rs.getInt("event_id");
			if (!events.containsKey(eventId)) {
				HashMap<String, String> temp = new HashMap<String, String>();
				temp.put("Name", rs.getString("name"));
				temp.put("Event_id", rs.getString("event_id"));
				events.put(eventId, temp);
			}
			
			Map<String, String> event = events.get(eventId);
			event.put(rs.getString("title"), rs.getString("value"));
		
		}
		
		String getTitles = "SELECT DISTINCT(title) FROM detail_titles";
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		ResultSet rs2 = preparedStatement1.executeQuery();

		ArrayList<String> titles = new ArrayList<String>();
		
		titles.add("Name");
		titles.add("Event_id");
		
		model.addColumn("Name");
		model.addColumn("Event_id");
		
		while(rs2.next()) {
			String title = rs2.getString("title");
			titles.add(title);
			model.addColumn(title);
		}
		
		

	
	
		System.out.println(titles.size());
		
		Set<Integer> keys = events.keySet();
		Iterator<Integer> iter = keys.iterator();
		
		while(iter.hasNext()) {
			int eventID = iter.next();
			Map<String, String> event = events.get(eventID);
			Object[] row = new Object[titles.size()];
			for (int x = 0; x < titles.size(); x++) {
				row[x] = event.get(titles.get(x));
			}
//			row[0] = "foo";
//			model.addRow(new Object[] {
//					eventID,
//					event.get("ip"),
//					event.get("-"),
//					event.get("--"),
//					event.get("date"),
//					event.get("url_method"),
//					event.get("number"),
//					event.get("number2"),
//					event.get("url"),
//					event.get("user-agent")
//				});
			model.addRow(row);
		}	
	}
}
