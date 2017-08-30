package manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.TextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Log Controller Manager");
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel lblLogControllerManagerlabel = new JLabel("Log Controller Manager");
		lblLogControllerManagerlabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogControllerManagerlabel.setBounds(205, 6, 152, 16);
		getContentPane().add(lblLogControllerManagerlabel);
		
		// -----------------------------
		
		JPanel projectPanel = new JPanel();
		projectPanel.setBorder(new TitledBorder(null, "Projects", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		projectPanel.setBounds(31, 51, 140, 117);
		getContentPane().add(projectPanel);
		
		GridBagLayout gbl_projectPanel = new GridBagLayout();
		gbl_projectPanel.columnWidths = new int[]{128, 0};
		gbl_projectPanel.rowHeights = new int[]{23, 23, 23, 0};
		gbl_projectPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_projectPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		projectPanel.setLayout(gbl_projectPanel);
		
		JCheckBox chckbxProjectname = new JCheckBox("ProjectName1");
		GridBagConstraints gbc_chckbxProjectname = new GridBagConstraints();
		gbc_chckbxProjectname.anchor = GridBagConstraints.NORTH;
		gbc_chckbxProjectname.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxProjectname.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxProjectname.gridx = 0;
		gbc_chckbxProjectname.gridy = 0;
		projectPanel.add(chckbxProjectname, gbc_chckbxProjectname);
		
		JCheckBox checkBox = new JCheckBox("ProjectName2");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.anchor = GridBagConstraints.NORTH;
		gbc_checkBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox.gridx = 0;
		gbc_checkBox.gridy = 1;
		projectPanel.add(checkBox, gbc_checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("ProjectName3");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.anchor = GridBagConstraints.NORTH;
		gbc_checkBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox_1.gridx = 0;
		gbc_checkBox_1.gridy = 2;
		projectPanel.add(checkBox_1, gbc_checkBox_1);
		
		JLabel lblSelectProjectTo = new JLabel("select a project");
		lblSelectProjectTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectProjectTo.setBounds(31, 164, 140, 16);
		getContentPane().add(lblSelectProjectTo);
		
		// -----------------------------
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fields", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(407, 51, 140, 222);
		getContentPane().add(panel);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{128, 0};
		gbl_panel.rowHeights = new int[]{23, 23, 23, 23, 23, 23, 23, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JCheckBox chckbxIp = new JCheckBox("IP");
		GridBagConstraints gbc_chckbxIp = new GridBagConstraints();
		gbc_chckbxIp.anchor = GridBagConstraints.NORTH;
		gbc_chckbxIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxIp.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxIp.gridx = 0;
		gbc_chckbxIp.gridy = 0;
		panel.add(chckbxIp, gbc_chckbxIp);
		
		JCheckBox checkBox_2 = new JCheckBox("IP");
		GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
		gbc_checkBox_2.anchor = GridBagConstraints.NORTH;
		gbc_checkBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_2.gridx = 0;
		gbc_checkBox_2.gridy = 1;
		panel.add(checkBox_2, gbc_checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("IP");
		GridBagConstraints gbc_checkBox_3 = new GridBagConstraints();
		gbc_checkBox_3.anchor = GridBagConstraints.NORTH;
		gbc_checkBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox_3.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_3.gridx = 0;
		gbc_checkBox_3.gridy = 2;
		panel.add(checkBox_3, gbc_checkBox_3);
		
		JCheckBox checkBox_4 = new JCheckBox("IP");
		GridBagConstraints gbc_checkBox_4 = new GridBagConstraints();
		gbc_checkBox_4.anchor = GridBagConstraints.NORTH;
		gbc_checkBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox_4.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_4.gridx = 0;
		gbc_checkBox_4.gridy = 3;
		panel.add(checkBox_4, gbc_checkBox_4);
		
		JCheckBox checkBox_5 = new JCheckBox("IP");
		GridBagConstraints gbc_checkBox_5 = new GridBagConstraints();
		gbc_checkBox_5.anchor = GridBagConstraints.NORTH;
		gbc_checkBox_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox_5.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_5.gridx = 0;
		gbc_checkBox_5.gridy = 4;
		panel.add(checkBox_5, gbc_checkBox_5);
		
		JCheckBox checkBox_6 = new JCheckBox("IP");
		GridBagConstraints gbc_checkBox_6 = new GridBagConstraints();
		gbc_checkBox_6.anchor = GridBagConstraints.NORTH;
		gbc_checkBox_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox_6.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox_6.gridx = 0;
		gbc_checkBox_6.gridy = 5;
		panel.add(checkBox_6, gbc_checkBox_6);
		
		JCheckBox checkBox_7 = new JCheckBox("IP");
		GridBagConstraints gbc_checkBox_7 = new GridBagConstraints();
		gbc_checkBox_7.anchor = GridBagConstraints.NORTH;
		gbc_checkBox_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBox_7.gridx = 0;
		gbc_checkBox_7.gridy = 6;
		panel.add(checkBox_7, gbc_checkBox_7);
		
		JLabel lblSelectFields = new JLabel("select fields");
		lblSelectFields.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectFields.setBounds(407, 270, 140, 16);
		getContentPane().add(lblSelectFields);
		
		// -----------------------------
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//BUTTON ACTION 
				
			}
		});
		
		btnFilter.setBounds(230, 283, 117, 29);
		getContentPane().add(btnFilter);
		
		JLabel lblLogControllerManager = new JLabel("<html>Log Controller Manager  is letting user to filter their result by checking<br> <b>project</b>, <b>date</b> and <b>fields</b>. It will represent the results by selected parameters.<br> Log Controller Manager needs a connection to the database. <br>Pressing the \"Filter\" button will show the results. <br>User has to check atleast one parameter from each column <br>or program will not filter.<html>");
		lblLogControllerManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogControllerManager.setBounds(31, 344, 547, 109);
		getContentPane().add(lblLogControllerManager);
		
		
		
	}
}
