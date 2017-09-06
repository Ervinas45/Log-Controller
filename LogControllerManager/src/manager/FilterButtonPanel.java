package manager;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilterButtonPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public FilterButtonPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnSave = new JButton("Save");
		add(btnSave);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Revert");
		add(btnNewButton);
		
	}

}
