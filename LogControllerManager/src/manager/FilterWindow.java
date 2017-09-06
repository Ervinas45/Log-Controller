package manager;


import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;

public class FilterWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> titles;
	private ArrayList<String> projects;
	private JPanel contentPane;
	private FilterSettingCheckBoxPanel filterSettingCheckBoxPanel;
	private ProjectsListPanel projectsListPanel;
	private DatePanel datePanel;
	private FilterButtonPanel filterButtonPanel;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	public FilterWindow(ArrayList<String> titles, ArrayList<String> projects) {
		super("Filter settings");
		this.titles = titles;
		this.projects = projects;
		setBounds(150, 100, 828, 324);
		this.contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setVisible(true);
		contentPane.setLayout(new GridBagLayout());
		layoutElements(contentPane);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void layoutElements(JPanel panel) {
		this.projectsListPanel = new ProjectsListPanel();
		this.datePanel = new DatePanel();
		this.filterSettingCheckBoxPanel = new FilterSettingCheckBoxPanel(this.titles, this.projects);
		this.filterButtonPanel = new FilterButtonPanel();
		this.comboBox = new JComboBox(this.getProjects());
		this.layoutPanels();
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!projectsListPanel.checkIfProjectExists(comboBox.getSelectedItem().toString()) == true) {
					projectsListPanel.addNewProject(comboBox.getSelectedItem().toString()); 
				}	
			}
		});
	}
	
	private String[] getProjects() {
		String[] array = new String[this.projects.size()];
		for(int i = 0; i < array.length; i++) {
		    array[i] = this.projects.get(i).toString();
		}
		return array;
	}
	
	private void layoutPanels() {
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 5, 5);
		c.weightx = 1.0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 0;
		this.contentPane.add(comboBox, c);
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(0, 0, 0, 5);
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 1;
		this.contentPane.add(this.filterSettingCheckBoxPanel, c1);
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(0, 0, 0, 5);
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 1;
		c2.gridy = 1;
		this.contentPane.add(this.projectsListPanel, c2);		
		
		GridBagConstraints c3 = new GridBagConstraints();
		c3.insets = new Insets(0, 0, 0, 5);
		c3.fill = GridBagConstraints.HORIZONTAL;
		c3.gridx = 2;
		c3.gridy = 1;
		this.contentPane.add(this.datePanel, c3);		
		
		GridBagConstraints c4 = new GridBagConstraints();
		c4.insets = new Insets(0, 0, 0, 5);
		c4.fill = GridBagConstraints.HORIZONTAL;
		c4.gridx = 1;
		c4.gridy = 2;
		this.contentPane.add(this.filterButtonPanel, c4);	
		
	}
}
