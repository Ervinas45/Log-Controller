package manager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 * Main settings frame class
 * 
 * @author Ervinas
 *
 */
public class SettingsPanel extends JFrame {

	private JPanel contentPane;
	HashMap<String, Map<String, String>> projectsInfo;
	HashMap<Integer, String> newValues = new HashMap<Integer, String>();
	HashMap<Integer, String> hmap = new HashMap<Integer, String>();
	private JTextField txtKey;
	private JTextField txtIp;
	private JComboBox comboBox;
	private JButton btnSave;
	ArrayList<String> oldTitles = new ArrayList<String>();
	private Object[] row;
	private Map<Integer, Map<String, String>> events = new HashMap<Integer, Map<String, String>>();
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<String> projects = new ArrayList<String>();
	private JTable table;
	private JTable mainTable;
	private DefaultTableModel tableModel;
	private boolean state = false;
	public boolean isSaveButtonPressed = false;
	private JButton btnCancel;
	private JScrollPane scrollPane;
	int id;

	/**
	 * Create the frame.
	 */
	public SettingsPanel(HashMap<String, Map<String, String>> projectsAndIp,DefaultTableModel tableModel, Object[] row, Map<Integer, Map<String, String>> events, ArrayList<String> titles, JTable mainTable) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 393, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setVisible(true);
		this.state = true;
		this.projectsInfo = projectsAndIp;
		this.row = row;
		this.events = events;
		this.titles = titles;
		this.projects = projects;
		this.mainTable = mainTable;
		this.tableModel = tableModel;
		this.oldTitles = getTitles();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);

		JLabel lblProject = new JLabel("Project: ");
		GridBagConstraints gbc_lblProject = new GridBagConstraints();
		gbc_lblProject.anchor = GridBagConstraints.EAST;
		gbc_lblProject.insets = new Insets(0, 0, 5, 5);
		gbc_lblProject.gridx = 0;
		gbc_lblProject.gridy = 0;
		contentPane.add(lblProject, gbc_lblProject);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.weightx = 1.0;
		gbc_comboBox.gridwidth = 1;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
		
		JLabel lblProjectKey = new JLabel("Project key:");
		GridBagConstraints gbc_lblProjectKey = new GridBagConstraints();
		gbc_lblProjectKey.anchor = GridBagConstraints.EAST;
		gbc_lblProjectKey.insets = new Insets(0, 0, 5, 5);
		gbc_lblProjectKey.gridx = 0;
		gbc_lblProjectKey.gridy = 1;
		contentPane.add(lblProjectKey, gbc_lblProjectKey);
		
		txtKey = new JTextField();
		txtKey.setText("key");
		GridBagConstraints gbc_txtKey = new GridBagConstraints();
		gbc_txtKey.insets = new Insets(0, 0, 5, 0);
		gbc_txtKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtKey.gridx = 1;
		gbc_txtKey.gridy = 1;
		contentPane.add(txtKey, gbc_txtKey);
		txtKey.setColumns(10);
		
		JLabel lblIpAddress = new JLabel("Ip address:");
		GridBagConstraints gbc_lblIpAddress = new GridBagConstraints();
		gbc_lblIpAddress.anchor = GridBagConstraints.EAST;
		gbc_lblIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblIpAddress.gridx = 0;
		gbc_lblIpAddress.gridy = 2;
		contentPane.add(lblIpAddress, gbc_lblIpAddress);
		
		txtIp = new JTextField();
		txtIp.setText("ip");
		GridBagConstraints gbc_txtIp = new GridBagConstraints();
		gbc_txtIp.insets = new Insets(0, 0, 5, 0);
		gbc_txtIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIp.gridx = 1;
		gbc_txtIp.gridy = 2;
		contentPane.add(txtIp, gbc_txtIp);
		txtIp.setColumns(10);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		DefaultTableModel model = new DefaultTableModel(){
	        @Override
	        public boolean isCellEditable(int row, int column)
	        {
	            return column == 0 ? false : true;
	        } 
	    };
	    
		model.addColumn("index");
		model.addColumn("value");
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		btnSave = new JButton("Save");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.weightx = 0.5;
		gbc_btnNewButton.gridwidth = 1;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 8;
		contentPane.add(btnSave, gbc_btnNewButton);
		
		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 8;
		contentPane.add(btnCancel, gbc_btnCancel);

		this.putElementsToComboBox(this.projectsInfo);

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isSaveButtonPressed = true;
				try {
					DatabaseComm.changeTitleNames(newValues, id);
					oldTitles = getTitles();
					ActionListeners.refreshTable(tableModel, getTitles(), projects, events, row, mainTable);
					dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}		
			}
		});
	
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isSaveButtonPressed = false;
				dispose();
			}
		});
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				id = 0;
				table.setModel(model);
				newValues.clear();
				model.setRowCount(0);
				txtKey.setText(projectsInfo.get(comboBox.getSelectedItem().toString()).get("project_key"));
				txtIp.setText(projectsInfo.get(comboBox.getSelectedItem().toString()).get("ip"));
				try {
					id = DatabaseComm.getProjectId(comboBox.getSelectedItem().toString());
					hmap = DatabaseComm.getTitles(id);			
					int row = 0;
					for ( Integer key : hmap.keySet() ) {
						model.addRow(new Object[] { key, hmap.get(key)});
						model.isCellEditable(row, 0);
						row =+ 1;	
					}	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}	
		});
			
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
		        	if(event.getValueIsAdjusting() == true) {
		        		String cell = table.getValueAt(table.getSelectedRow(), 1).toString();
		        		int cellColumnIndex = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
		            if(!newValues.containsKey(cellColumnIndex)) {
		            		newValues.put(cellColumnIndex, cell);
		            }
		        	}
	        }
	    });
		
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == e.UPDATE) {
					int row = e.getFirstRow();
	                int column = e.getColumn();
	                	TableModel model = (TableModel)e.getSource();
	                	int cellIndex = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
	                	String cellNewValue = String.valueOf( table.getValueAt(row, column) );
	                	if(newValues.containsKey(cellIndex)) {
	                		newValues.replace(cellIndex, cellNewValue);
	                 }  
				}
			}
         });
	}

	/**
	 * Puts all elements to ComboBox to see visual
	 * 
	 * @param projectsInfo Full project info HashMap
	 */
	private void putElementsToComboBox(HashMap<String, Map<String, String>> projectsInfo) {
		for ( String key : projectsInfo.keySet() ) {
			this.comboBox.addItem(key);
		}
	}
	
	private ArrayList<String> getTitles(){
		return this.titles;
	}
	public ArrayList<String> getOldTitles(){
		return this.oldTitles;
	}
	
	/**
	 * Returns a state of this frame
	 */
	public boolean isActive() {
		return this.state;
	}
}
