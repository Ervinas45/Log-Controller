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
	boolean isTriggered = false;
	private FilterWindow filterWindow;
	ArrayList<String> newTitles = new ArrayList<String>();
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
				try {

					if(filterWindow != null) {
						if(settings != null) {
							if(settings.isRefreshed == true) {
								settings.isRefreshed = false;
								JOptionPane.showMessageDialog(getParent(),
									    "Nothing to refresh");
							}
						
						}
						else {
							ActionListeners.filter(table, filterWindow.filterSettingCheckBoxPanel.checkedItemList, filterWindow.projectsListPanel.getProjectsToFilter(), filterWindow.datePanel.dateFrom, filterWindow.datePanel.dateUntil, model);
						}
						
						
					}
					if(isTriggered == true) {
						JOptionPane.showMessageDialog(getParent(),
							    "Nothing to refresh");
						isTriggered = false;
					}
//					else{
//						ActionListeners.filter(table, filterWindow.filterSettingCheckBoxPanel.checkedItemList, filterWindow.projectsListPanel.getProjectsToFilter(), filterWindow.datePanel.dateFrom, filterWindow.datePanel.dateUntil, model);
//					}
//					else {
//						JOptionPane.showMessageDialog(getParent(),
//							    "Nothing to refresh");
//					}
//					if(isTriggered == false && filterWindow != null) {
//						ActionListeners.filter(table, filterWindow.filterSettingCheckBoxPanel.checkedItemList, filterWindow.projectsListPanel.getProjectsToFilter(), filterWindow.datePanel.dateFrom, filterWindow.datePanel.dateUntil, model);
//					}
//					if(isTriggered == true) {
//						JOptionPane.showMessageDialog(getParent(),
//							    "Nothing to refresh");
//					}
//					if(isTriggered == true && filterWindow == null) {
//						JOptionPane.showMessageDialog(getParent(),
//							    "Nothing to refresh");
//					}
//					if(isTriggered == false && filterWindow == null) {
//						JOptionPane.showMessageDialog(getParent(),
//							    "Nothing to refresh");
//					}
//					if(settings != null && isTriggered == false && filterWindow == null) {
//						if(settings.isRefreshed == true) {
//							JOptionPane.showMessageDialog(getParent(),
//								    "Nothing to refresh");
//						}
//					}
						

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
				e1.printStackTrace();
				}
				
			}
			
		});
		
		this.reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
						projects = ActionListeners.refreshTable(model, titles, projects, events, row, table);
						filterWindow = null;
						isTriggered = true;
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
						settings = new SettingsPanel(projectsAndIp, model, row, events, titles, table);
						settings.setVisible(isOpened);
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		this.filter.addActionListener(new ActionListener() {
//			int i = 0;
//			ArrayList<String> oldTitles = new ArrayList<String>();
//			ArrayList<String> newTitles = new ArrayList<String>();
			@Override
			public void actionPerformed(ActionEvent e) {
					if(filterWindow == null){
						System.out.println("Nera klase veikianti");
						filterWindow = new FilterWindow(table, titles, projects, model);
						filterWindow.setVisible(true);

					}
					else if(filterWindow != null && settings == null){
						filterWindow.setVisible(true);
						System.out.println("yra veikianti klasse");
					}
					else if(filterWindow != null && settings != null) {
						try {
							settings = null;
							newTitles = DatabaseComm.getNewTitles();
							filterWindow = new FilterWindow(table, newTitles, projects, model);
							filterWindow.setVisible(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				
			}});
		

	}
	
	private boolean isSameTitles(ArrayList<String> oldTitles, ArrayList<String> newTitles) {
		for(String title: oldTitles) {
			System.out.println("OLD: " + title);
		}
		for(String title: newTitles) {
			System.out.println("NEW: " + title);
		}
		
		
	        for(String item: oldTitles)
	        {
	            if(!newTitles.contains(item)) {
	            		System.out.println("Returning false");
	            		return false;
	            }
	                
	        }
	        System.out.println("ALL ELEMENTS ARE SAME");
	        return true;
	}
	
}
