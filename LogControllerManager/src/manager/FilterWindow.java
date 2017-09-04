package manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
		setBounds(100, 100, 629, 343);
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

		contentPane.add(fscbp, BorderLayout.WEST);

		contentPane.add(pls, BorderLayout.CENTER);
		
		JButton btnNewButton_3 = new JButton("DATE PANEL");
		contentPane.add(btnNewButton_3, BorderLayout.EAST);
		
		
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
