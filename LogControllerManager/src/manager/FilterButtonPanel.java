package manager;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilterButtonPanel extends JPanel {
	
	public JButton btnSave;
	public JButton reset;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public FilterButtonPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		reset = new JButton("Reset");
		JButton btnCancel = new JButton("Cancel");
		btnSave = new JButton("Save");
		add(btnSave);
		add(btnCancel);
		add(reset);
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ActionListeners.closeWindow(SwingUtilities.getWindowAncestor(getParent()));
			}
		});
	}
}
