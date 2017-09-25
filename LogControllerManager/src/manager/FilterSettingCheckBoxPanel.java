package manager;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.SwingConstants;

public class FilterSettingCheckBoxPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
	ArrayList<String> checkedItemList = new ArrayList<String>();
	ArrayList<String> newTitles = new ArrayList<String>();
	/**
	 * Create the panel.
	 */
	public FilterSettingCheckBoxPanel(ArrayList<String> titles, ArrayList<String> projects) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		checkedItemList.add("Name");
		checkedItemList.add("Event_id");
		checkedItemList.add("Date");
		GridBagConstraints c = new GridBagConstraints();
		int temp = 0;
		
		for(int i = 3 ; i < titles.size() ; i++) {
			if(i > 7) {
				JCheckBox checkBox = new JCheckBox(titles.get(i).toString());
				checkedItemList.add(titles.get(i));
				checkBox.setSelected(true);
				checkBox.addItemListener(new ItemListener() {
					@SuppressWarnings("static-access")
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange() == e.DESELECTED) {
							checkedItemList.remove(checkBox.getText());
						}
						else if (e.getStateChange() == e.SELECTED) {
							checkedItemList.add(checkBox.getText());
						}	
					}
				});
				
				checkBox.setHorizontalAlignment(SwingConstants.LEFT);
				checkBoxes.add(checkBox);
				c.gridx = 1;
				c.gridy = temp;
				c.fill = GridBagConstraints.HORIZONTAL; 
				add(checkBox, c);
				temp++;
			}
			else {
				JCheckBox checkBox = new JCheckBox(titles.get(i).toString());
				checkedItemList.add(titles.get(i));
				checkBox.setSelected(true);
				checkBox.addItemListener(new ItemListener() {
					
					@SuppressWarnings("static-access")
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange() == e.DESELECTED) {
							checkedItemList.remove(checkBox.getText());
						}
						else if (e.getStateChange() == e.SELECTED) {
							checkedItemList.add(checkBox.getText());
						}
					}
				});
				
				checkBox.setHorizontalAlignment(SwingConstants.LEFT);
				checkBoxes.add(checkBox);
				c.gridx = 0;
				c.gridy = i;
				c.fill = GridBagConstraints.HORIZONTAL; 
				add(checkBox, c);
			}
		}		
	}
	
	public ArrayList<String> getCheckedItemList() {
		return this.checkedItemList;
	}
	
	public void setNewTitleNames(ArrayList<String> titles) {
		this.newTitles = titles;
	}


}
