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
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		GridBagConstraints c = new GridBagConstraints();
		
		int temp = 0;
		
		for(int i = 0 ; i < titles.size() ; i++) {
			if(i > 7) {
				JCheckBox checkBox = new JCheckBox(titles.get(i).toString());
				checkBox.setHorizontalAlignment(SwingConstants.LEFT);
				checkBoxes.add(checkBox);
				//c.insets = new Insets(0, 0, 5, 0);
				c.gridx = 1;
				c.gridy = temp;
				c.fill = GridBagConstraints.HORIZONTAL; 
				add(checkBox, c);
				temp++;
			}
			else {
				JCheckBox checkBox = new JCheckBox(titles.get(i).toString());
				checkBox.setHorizontalAlignment(SwingConstants.LEFT);
				checkBoxes.add(checkBox);
				//c.insets = new Insets(0, 0, 5, 0);
				c.gridx = 0;
				c.gridy = i;
				c.fill = GridBagConstraints.HORIZONTAL; 
				
				add(checkBox, c);
			}
		}
		
		
		


		

		

		

		
		
		
	}

}
