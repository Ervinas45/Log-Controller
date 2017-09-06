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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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
import javax.swing.JMenuBar;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class UserLogManagerMainWindow extends JFrame {

	private DefaultTableModel model;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private ProjectsListPanel filterListView;
	private Object[] row;
	
	private JButton login;
	private JButton register;
	private JButton filter;
	private JButton refresh;
	
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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width,screenSize.height);
		setResizable(true);
		this.filterListView = new ProjectsListPanel();
		model = new DefaultTableModel();
		contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		table_1 = new JTable(model);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table_1);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		//------------------------------------------------
		importButtons(menuBar);
		DatabaseComm.getColumnNamesToPanel(model, titles);
		this.projects = DatabaseComm.AddLogsToArrayReturnProjectNames(this.events);
		DatabaseComm.fillDataToPanel(model, events, titles, row);
		DatabaseComm.resizeColumnWidth(table_1); 
		//------------------------------------------------	
	}
	
	private void importButtons(JMenuBar menuBar){
		this.filter = new JButton("Filter");
		this.login = new JButton("Login");
		this.register = new JButton("Register");
		this.refresh = new JButton("Refresh");
		menuBar.add(this.filter);
		menuBar.add(this.refresh);
		this.refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					projects = ActionListeners.refreshTable(model, titles, projects, events, row, table_1);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}	
		});
		
		this.filter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionListeners.filterButtonPressed(titles,projects);
			}
		});
	}


}
