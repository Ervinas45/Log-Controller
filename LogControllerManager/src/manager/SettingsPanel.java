package manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class SettingsPanel extends JFrame {

	private JPanel contentPane;
	HashMap<String, Map<String, String>> projectsInfo;
	private JTextField txtKey;
	private JTextField txtIp;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private JTable table;
	private JButton btnCancel;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public SettingsPanel(HashMap<String, Map<String, String>> projectsAndIp) {
		
		
		setBounds(100, 100, 393, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setVisible(true);
		this.projectsInfo = projectsAndIp;
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
//		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
//		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
//		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
//		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("index");
		model.addColumn("value");
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("Save");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
//		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.weightx = 0.5;
		gbc_btnNewButton.gridwidth = 1;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 8;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
//		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 8;
		contentPane.add(btnCancel, gbc_btnCancel);
		
		
		
		//-----------------------
		this.putElementsToComboBox(this.projectsInfo);
		//-----------------------
		
		
		
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = 0;
				table.setModel(model);
				model.setRowCount(0);
				HashMap<Integer, String> hmap;
				txtKey.setText(projectsInfo.get(comboBox.getSelectedItem().toString()).get("project_key"));
				txtIp.setText(projectsInfo.get(comboBox.getSelectedItem().toString()).get("ip"));
				try {
					id = DatabaseComm.getProjectId(comboBox.getSelectedItem().toString());
					hmap = DatabaseComm.getTitles(id);					
					for ( Integer key : hmap.keySet() ) {
						model.addRow(new Object[] { key, hmap.get(key)});
					}	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}	
		});
	}
	
	private void putElementsToComboBox(HashMap<String, Map<String, String>> projectsInfo) {
		
		for ( String key : projectsInfo.keySet() ) {
			this.comboBox.addItem(key);
		}
		
		
	}

}
