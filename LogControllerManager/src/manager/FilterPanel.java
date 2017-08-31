package manager;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;

public class FilterPanel extends JPanel {

	ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
	
	/**
	 * Create the panel.
	 */
	public FilterPanel(ArrayList titles, ArrayList projects) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);
		

		
		String[] array = new String[projects.size()];
		for(int i = 0; i < array.length; i++) {
		    array[i] = projects.get(i).toString();
		}
		
		GridBagConstraints c = new GridBagConstraints();
		JComboBox comboBox = new JComboBox(array);
		Dimension d = comboBox.getPreferredSize();
		c.gridx = 0;
		c.gridy = 0;
		comboBox.setPreferredSize(new Dimension(135, d.height));
		add(comboBox,c);
		
		for(int i = 0; i < titles.size(); i++ ) {
			if(i >= 10) {
				for(int x = 10; i < titles.size(); i++ ) {
					int gridTemp = 0;
					JCheckBox chckbxNewCheckBox = new JCheckBox(titles.get(x).toString());
					chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
					checkBoxes.add(chckbxNewCheckBox);
					
					c.gridx = 1;
					c.gridy = gridTemp;
					c.weightx = 1.;
				    c.fill = GridBagConstraints.HORIZONTAL;
					add(chckbxNewCheckBox, c);
					gridTemp++;
				}
			}
			else {
				JCheckBox chckbxNewCheckBox = new JCheckBox(titles.get(i).toString());
				chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
				checkBoxes.add(chckbxNewCheckBox);
			
				c.gridx = 0;
				c.gridy = i+1;
				c.weightx = 1.;
			    c.fill = GridBagConstraints.HORIZONTAL;
				add(chckbxNewCheckBox, c);
			}
		}

		

		

		

		
		
		
	}

}
