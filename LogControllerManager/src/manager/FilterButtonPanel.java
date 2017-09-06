package manager;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilterButtonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public FilterButtonPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		JButton btnSave = new JButton("Save");
		add(btnSave);
		add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionListeners.closeWindow(SwingUtilities.getWindowAncestor(getParent()));
			}
		});
		
		
	}

}
