package manager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
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
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JList;

public class UserLogManagerMainWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private FilterListView filterListView;
	Map<Integer, Map<String, String>> events = new HashMap<Integer, Map<String, String>>();
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> projects = new ArrayList<String>();

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width,screenSize.height);
		
		setResizable(true);
		
		this.filterListView = new FilterListView();
		
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(50, 25, screenSize.width - 100, screenSize.width/3);
		c.insets = new Insets(10,10,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(scrollPane, c);
		
		DefaultTableModel model = new DefaultTableModel(); 
		
		table_1 = new JTable(model);
		scrollPane.setViewportView(table_1);		
		

		//------------------------------------------------
		getColumnNamesToPanel(model);
		this.projects = AddLogsToArrayReturnProjectNames();
		fillDataToPanel(model);
		//------------------------------------------------	
		
		
		
		
		String[] array = new String[projects.size()];
		for(int i = 0; i < array.length; i++) {
		    array[i] = projects.get(i).toString();
		}
		
		JComboBox comboBox = new JComboBox(array);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!filterListView.checkIfProjectExists(comboBox.getSelectedItem().toString()) == true) {
					filterListView.addNewProject(comboBox.getSelectedItem().toString()); 
				}	
			}
		});
		
		GridBagConstraints c1 = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 1;
		contentPane.add(comboBox, c1);
		
		GridBagConstraints c2 = new GridBagConstraints();
		FilterPanel filterPanel = new FilterPanel(this.titles, this.projects);
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 0;
		c2.gridy = 2;
		contentPane.add(filterPanel,c2);
		
		GridBagConstraints c3 = new GridBagConstraints();
		c3.fill = GridBagConstraints.HORIZONTAL;
		c3.gridx = 1;
		c3.gridy = 2;	
		contentPane.add(filterListView, c3);
		

		

		
		
		
		
		
		
		
		
		
		
	}
	
	private void fillDataToPanel(DefaultTableModel model) {
		
		Set<Integer> keys = events.keySet();
		Iterator<Integer> iter = keys.iterator();
		
		while(iter.hasNext()) {
			int eventID = iter.next();
			Map<String, String> event = events.get(eventID);
			Object[] row = new Object[this.titles.size()];
			for (int x = 0; x < this.titles.size(); x++) {
				row[x] = event.get(this.titles.get(x));
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
	
	private void getColumnNamesToPanel(DefaultTableModel model) throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql:3306/logctrl?user=logctrl&password=kavospertraukele");
		String getTitles = "SELECT DISTINCT(title) FROM detail_titles";
		PreparedStatement preparedStatement1 = con.prepareStatement(getTitles);
		ResultSet rs2 = preparedStatement1.executeQuery();

		//ArrayList<String> titles = new ArrayList<String>();
		
		this.titles.add("Name");
		this.titles.add("Event_id");
		
		model.addColumn("Name");
		model.addColumn("Event_id");
		
		while(rs2.next()) {
			String title = rs2.getString("title");
			this.titles.add(title);
			model.addColumn(title);
		}
		
		rs2.close();
		con.close();
		
	}
	
	private ArrayList AddLogsToArrayReturnProjectNames() throws SQLException {
		
		ArrayList<String> clientProjects = new ArrayList<String>();
		
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql:3306/logctrl?user=logctrl&password=kavospertraukele");
		System.out.println("Connected");
		String sql = "SELECT e.event_id, d.value, t.title, p.name\n" + 
				"FROM event AS e\n" + 
				"JOIN project AS p on p.project_id = e.project_id\n" +
				"JOIN event_detail AS d on d.event_id = e.event_id\n" + 
				"JOIN detail_titles AS t on (t.project_id = e.project_id AND t.index = d.index)";
				//"WHERE t.project_id = 11";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()) {
			int eventId = rs.getInt("event_id");
			if (!this.events.containsKey(eventId)) {
				HashMap<String, String> temp = new HashMap<String, String>();
				
				temp.put("Name", rs.getString("name"));
				temp.put("Event_id", rs.getString("event_id"));
				this.events.put(eventId, temp);
			}
			if( !clientProjects.contains(rs.getString("Name"))) {
				clientProjects.add(rs.getString("Name"));
			}
			
			Map<String, String> event = this.events.get(eventId);
			event.put(rs.getString("title"), rs.getString("value"));
		
		}
		
		rs.close();
		con.close();
		
		return clientProjects;
	
	}
}
