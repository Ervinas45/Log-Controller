package manager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;

import net.sourceforge.jdatepicker.JDatePanel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import net.sourceforge.jdatepicker.util.JDatePickerUtil;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.Insets;

public class FilterWindow extends JFrame {

	private ArrayList<String> titles;
	private ArrayList<String> projects;
	private JPanel contentPane;
	private FilterSettingCheckBoxPanel filterSettingCheckBoxPanel;
	private ProjectsListPanel projectsListPanel;
	private DatePanel datePanel;
	private FilterButtonPanel filterButtonPanel;
	private JButton btnCancel;
	private JButton btnSave;

	/**
	 * Create the frame.
	 */
	public FilterWindow(ArrayList titles, ArrayList projects) {
		super("Filter settings");
		this.titles = titles;
		this.projects = projects;
		setBounds(150, 100, 828, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setVisible(true);
		contentPane.setLayout(new GridBagLayout());
		layoutElements(contentPane);
		
		
		

	}
	
	private void layoutElements(JPanel panel) {
		this.projectsListPanel = new ProjectsListPanel();
		this.datePanel = new DatePanel();
		this.filterSettingCheckBoxPanel = new FilterSettingCheckBoxPanel(this.titles, this.projects);
		this.filterButtonPanel = new FilterButtonPanel();
		JPanel externalPanel = new JPanel();
		JComboBox comboBox = new JComboBox(this.getProjects());
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!projectsListPanel.checkIfProjectExists(comboBox.getSelectedItem().toString()) == true) {
					projectsListPanel.addNewProject(comboBox.getSelectedItem().toString()); 
				}	
			}
		});
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 5, 5);
		c.weightx = 1.0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(comboBox, c);
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(0, 0, 0, 5);
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 1;
		panel.add(this.filterSettingCheckBoxPanel, c1);
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(0, 0, 0, 5);
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 1;
		c2.gridy = 1;
		panel.add(this.projectsListPanel, c2);		
		
		GridBagConstraints c3 = new GridBagConstraints();
		c3.insets = new Insets(0, 0, 0, 5);
		c3.fill = GridBagConstraints.HORIZONTAL;
		c3.gridx = 2;
		c3.gridy = 1;
		panel.add(this.datePanel, c3);		
		
		GridBagConstraints c4 = new GridBagConstraints();
		c4.insets = new Insets(0, 0, 0, 5);
		c4.fill = GridBagConstraints.HORIZONTAL;
		c4.gridx = 1;
		c4.gridy = 2;
		panel.add(this.filterButtonPanel, c4);	
		
		
		
		
		
//		panel.add(comboBox, BorderLayout.NORTH);
//		panel.add(this.filterSettingCheckBoxPanel, BorderLayout.WEST);
//		panel.add(this.projectsListPanel, BorderLayout.CENTER);
//		panel.add(this.datePanel,BorderLayout.EAST);
//		
//		panel.add(externalPanel, BorderLayout.SOUTH);
//		externalPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		btnSave = new JButton("Save");
//		externalPanel.add(btnSave);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
			}
			
		});
		
		externalPanel.add(btnCancel);
	}
	
	private String[] getProjects() {
		String[] array = new String[this.projects.size()];
		for(int i = 0; i < array.length; i++) {
		    array[i] = this.projects.get(i).toString();
		}
		return array;
	}
}
