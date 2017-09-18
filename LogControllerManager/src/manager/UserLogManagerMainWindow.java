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
import javax.swing.JOptionPane;

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
	private JButton reset;
	private JButton refresh;
	private JButton settingsButton;
	private SettingsPanel settings;
	boolean isFullyReset = false;
	boolean isSettingsOpen = false;
	boolean isFilterOpen = false;
	private FilterWindow filterWindow;
	ArrayList<String> newTitles = new ArrayList<String>();
	Map<Integer, Map<String, String>> events = new HashMap<Integer, Map<String, String>>();
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> projects = new ArrayList<String>();
	private JMenuBar menuBar;
	private boolean isReseted = false;

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
		this.reset = new JButton("Fully reset");
		this.refresh = new JButton("Refresh");
		this.settingsButton = new JButton("Settings");
		menuBar.add(this.filter);
		menuBar.add(this.reset);
		menuBar.add(this.settingsButton);
		menuBar.add(this.refresh);
		
		this.refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(filterWindow != null) {
					
				
					if(filterWindow.isResetButtonPressed == false) {
						try {
							ActionListeners.filter(table, filterWindow.filterSettingCheckBoxPanel.checkedItemList, filterWindow.projectsListPanel.getProjectsToFilter(), filterWindow.datePanel.dateFrom, filterWindow.datePanel.dateUntil, model);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				
				if(isFilterOpen == true) {
					try {
						if(settings != null) {
							if(settings.isSaveButtonPressed == false) {
								ActionListeners.filter(table, filterWindow.filterSettingCheckBoxPanel.checkedItemList, filterWindow.projectsListPanel.getProjectsToFilter(), filterWindow.datePanel.dateFrom, filterWindow.datePanel.dateUntil, model);
							}
						} 
						if(filterWindow.isResetButtonPressed == true) {
							filterWindow.isResetButtonPressed = false;
							isReseted = false;
							System.out.println("YES");
							try {
								projects = ActionListeners.refreshTable(model, newTitles, projects, events, row, table);
								isFilterOpen = false;
								filterWindow = null;
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}


					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(isFullyReset == true) {
					JOptionPane.showMessageDialog(getParent(),
						    "Nothing to refresh");
				}
				if(isSettingsOpen == true) {
					if(settings.isSaveButtonPressed == true){
						filterWindow = null;
						isSettingsOpen = false;
						isFilterOpen = false;
					}
				}	
				if(isFilterOpen == false) {
					try {
						System.out.println("AAAA");
						projects = ActionListeners.refreshTable(model, newTitles, projects, events, row, table);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		this.settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isSettingsOpen = true;
				isFullyReset = false;
				try {
					if(isSettingsOpen) {
						HashMap<String, Map<String, String>> projectsAndIp = DatabaseComm.getProjectInfo();
						settings = new SettingsPanel(projectsAndIp, model, row, events, titles, table);
						settings.setVisible(isSettingsOpen);
						
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		
		this.filter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isReseted = false;
				isFilterOpen = true;
				isFullyReset = false;
				isSettingsOpen = false;
				
					if(filterWindow != null) {
						if(filterWindow.isResetButtonPressed == true) {
							isReseted = true;
//							isFilterOpen = false;
							filterWindow = new FilterWindow(table, titles, filterWindow.projects, model, events, row);
						
						}
					}
						
				
					if(filterWindow == null){
						System.out.println("Nera klase veikianti");
						filterWindow = new FilterWindow(table, titles, projects, model, events, row);
						filterWindow.setVisible(true);

					}
					else if(filterWindow != null && settings == null){
						filterWindow.setVisible(true);
						System.out.println("yra veikianti klasse");
					}
					else if(filterWindow != null && settings != null) {
						if(settings.isSaveButtonPressed == false) {
							filterWindow.setVisible(true);
						} else {
							filterWindow = new FilterWindow(table, titles, filterWindow.projects, model, events, row);
						}
							
					}
			}});
	}
}
