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

public class FilterWindow extends JFrame {

	private ArrayList<String> titles;
	private ArrayList<String> projects;
	private JPanel contentPane;
	private FilterSettingCheckBoxPanel fscbp;
	private ProjectsListPanel pls;
	private GridBagConstraints c;
	private JButton btnCancel;
	private JButton btnSave;

	/**
	 * Create the frame.
	 */
	public FilterWindow(ArrayList titles, ArrayList projects) {
		super("Filter settings");
		this.titles = titles;
		this.projects = projects;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setVisible(true);
		
		
		contentPane.setLayout(new BorderLayout(0, 0));
		layoutElements(contentPane);
		

		
	}
	
	private void layoutElements(JPanel panel) {
		
		this.fscbp = new FilterSettingCheckBoxPanel(this.titles, this.projects);
		this.pls = new ProjectsListPanel();
		this.c = new GridBagConstraints();
		JPanel externalPanel = new JPanel();
		
		
		JComboBox comboBox = new JComboBox(this.getProjects());
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!pls.checkIfProjectExists(comboBox.getSelectedItem().toString()) == true) {
					pls.addNewProject(comboBox.getSelectedItem().toString()); 
				}	
			}
		});
		panel.add(comboBox, BorderLayout.NORTH);

		panel.add(fscbp, BorderLayout.WEST);

		panel.add(pls, BorderLayout.CENTER);
		
		UtilDateModel model = new UtilDateModel();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
			
		
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.gridx = 0;
		gbc_datePicker.gridy = 2;
		pls.add(datePicker, gbc_datePicker);
		datePicker.getJFormattedTextField().setText("Select a date...");
		
		

		
		
		
		panel.add(externalPanel, BorderLayout.SOUTH);
		externalPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnSave = new JButton("Save");
		externalPanel.add(btnSave);
		
		btnCancel = new JButton("Cancel");
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
