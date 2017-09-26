package manager;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
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

/**
 * Main class which is a starting class.
 * 
 * @author Ervinas
 *
 */
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
	private JMenuBar bar;
	private boolean isReseted = false;
	private boolean isConnected = false;
	private JButton connectToDatabaseBtn;
	private JButton disconnect;
	private DatabaseConnectionDialog dialog;
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.setSize(ActionListeners.screenSize.width,ActionListeners.screenSize.height);
		this.setResizable(true);
		this.filterListView = new ProjectsListPanel();
		this.model = new DefaultTableModel();
		this.contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		this.table = new JTable(model);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		this.contentPane.add(scrollPane, BorderLayout.CENTER);
		this.importButtons(this.menuBar);
			connectToDatabaseBtn = new JButton("Connect to database");
			bar = new JMenuBar();
			bar.add(connectToDatabaseBtn);
			this.setJMenuBar(bar);
			connectToDatabaseBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog = new DatabaseConnectionDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setModal(true);
					dialog.setVisible(true);
					if(dialog.answer == true) {
						getJMenuBar().setVisible(false);
						setJMenuBar(menuBar);
						getJMenuBar().setVisible(true);
						try {
							DatabaseComm.getColumnNamesToPanel(model, titles);
							projects = DatabaseComm.AddLogsToArrayReturnProjectNames(events);
							DatabaseComm.fillDataToPanel(model, events, titles, row);
							DatabaseComm.resizeColumnWidth(table); 
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
	}
	
	
	private void changeJMenu() {
		this.getJMenuBar().setVisible(false);
		this.importButtons(this.menuBar);
		this.setJMenuBar(this.menuBar);
		this.getJMenuBar().setVisible(true);
	}
	
	/**
	 * Button panel importing and actionListeners method
	 * 
	 * @param menuBar JMenuBar element
	 */
	private void importButtons(JMenuBar menuBar){
		this.filter = new JButton("Filter");
		this.reset = new JButton("Fully reset");
		this.refresh = new JButton("Refresh");
		this.settingsButton = new JButton("Settings");
		this.disconnect = new JButton("Disconnect");
		menuBar.add(this.filter);
		menuBar.add(this.settingsButton);
		menuBar.add(this.refresh);
		menuBar.add(this.disconnect);
		this.disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				JOptionPane.showMessageDialog(getParent(),
					    "Disconnected!");
				dialog.disconnect();
				model = new DefaultTableModel();
				table.setModel(model);
				model.setRowCount(0);
				projects.clear();
				titles.clear();
				events.clear();
				getJMenuBar().setVisible(false);
				setJMenuBar(bar);
				getJMenuBar().setVisible(true);
			}
		});
		
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
							try {
								projects = ActionListeners.refreshTable(model, newTitles, projects, events, row, table);
								isFilterOpen = false;
								filterWindow = null;
							} catch (SQLException e1) {
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
						projects = ActionListeners.refreshTable(model, newTitles, projects, events, row, table);
					} catch (SQLException e1) {
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
							filterWindow = new FilterWindow(table, titles, filterWindow.projects, model, events, row);
						}
					}
					if(filterWindow == null){
						filterWindow = new FilterWindow(table, titles, projects, model, events, row);
						filterWindow.setVisible(true);

					}
					else if(filterWindow != null && settings == null){
						filterWindow.setVisible(true);
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
