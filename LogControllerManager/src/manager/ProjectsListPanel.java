package manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ProjectsListPanel extends JPanel{
	
	public DefaultListModel<String> projectsToFilter = new DefaultListModel<>();
	
	public ProjectsListPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(scrollPane, gbc);
		JList list = new JList(projectsToFilter);
		scrollPane.setViewportView(list);
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = list.getSelectedValue().toString();
				removeProject(selectedItem);
			}
		});
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.anchor = GridBagConstraints.NORTH;
		gbc_1.gridx = 0;
		gbc_1.gridy = 1;
		add(btnDelete, gbc_1);
	}
	
	public void addNewProject(String projectName) {
		this.projectsToFilter.addElement(projectName);
	}
	
	public void removeProject(String projectName) {
		this.projectsToFilter.removeElement(projectName);
	}
	
	public boolean checkIfProjectExists(String projectName) {
		if(this.projectsToFilter.contains(projectName) == true) {
			return true;
		}
		return false;
	}

}
