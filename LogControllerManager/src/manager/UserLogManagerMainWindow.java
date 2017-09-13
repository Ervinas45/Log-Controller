package manager;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;

public class UserLogManagerMainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JPanel contentPane;
	private JTable table;
	@SuppressWarnings("unused")
	private ProjectsListPanel filterListView;
	private Object[] row;
	private JButton filter;
	private JButton refresh;
	private JButton settingsButton;
	
	private FilterWindow filterWindow;
	
	Map<Integer, Map<String, String>> events = new HashMap<Integer, Map<String, String>>();
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> projects = new ArrayList<String>();
	private JMenuBar menuBar;

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
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setSize(ActionListeners.screenSize.width,ActionListeners.screenSize.height);
		setResizable(true);
		this.filterListView = new ProjectsListPanel();
		model = new DefaultTableModel();
		contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		//------------------------------------------------
		importButtons(menuBar);
		DatabaseComm.getColumnNamesToPanel(model, titles);
		this.projects = DatabaseComm.AddLogsToArrayReturnProjectNames(this.events);
		DatabaseComm.fillDataToPanel(model, events, titles, row);
		DatabaseComm.resizeColumnWidth(table); 
		//------------------------------------------------	
	}
	
	private void importButtons(JMenuBar menuBar){
		this.filter = new JButton("Filter");
//		this.login = new JButton("Login");
//		this.register = new JButton("Register");
		this.refresh = new JButton("Refresh");
		this.settingsButton = new JButton("Settings");
		menuBar.add(this.filter);
		menuBar.add(this.refresh);
		menuBar.add(this.settingsButton);
		this.refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					projects = ActionListeners.refreshTable(model, titles, projects, events, row, table);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}	
		});
		
		this.settingsButton.addActionListener(new ActionListener() {
			boolean isOpened = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				isOpened = true;
				try {
					if(isOpened) {
						HashMap<String, Map<String, String>> projectsAndIp = DatabaseComm.getProjectInfo();
						SettingsPanel settings = new SettingsPanel(projectsAndIp, model, row, events, projects, projects, table);
						settings.setVisible(isOpened);
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		this.filter.addActionListener(new ActionListener() {
			int i = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				i++;
				if(i == 1) {
					
					filterWindow = new FilterWindow(table, titles, projects, model);
					filterWindow.setVisible(true);
					for(String title: titles) {
						System.out.println("New title: " + title);
					}
					
				}
				else {
					filterWindow.setVisible(true);
					for(String title: titles) {
						System.out.println("New title: " + title);
					}
				}
				
			}
		});
		

	}
	
}
