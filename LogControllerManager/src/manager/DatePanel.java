package manager;

import javax.swing.JPanel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class DatePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DatePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		JLabel lblDateFrom = new JLabel("Date from: ");
		GridBagConstraints gbc_lblDateFrom = new GridBagConstraints();
		gbc_lblDateFrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateFrom.gridx = 0;
		gbc_lblDateFrom.gridy = 0;
		add(lblDateFrom, gbc_lblDateFrom);
		
		JLabel lblDatEUntil = new JLabel("Date until: ");
		GridBagConstraints gbc_lblDatEUntil = new GridBagConstraints();
		gbc_lblDatEUntil.insets = new Insets(0, 0, 0, 5);
		gbc_lblDatEUntil.gridx = 0;
		gbc_lblDatEUntil.gridy = 1;
		add(lblDatEUntil, gbc_lblDatEUntil);
		
//		------------------------------------------
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		
		JDatePickerImpl datePickerFrom = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		GridBagConstraints gbc_datePickerFrom = new GridBagConstraints();
		gbc_datePickerFrom.fill = GridBagConstraints.HORIZONTAL;

		gbc_datePickerFrom.insets = new Insets(0, 0, 5, 5);
		gbc_datePickerFrom.gridx = 1;
		gbc_datePickerFrom.gridy = 0;
		this.add(datePickerFrom, gbc_datePickerFrom);
		datePickerFrom.getJFormattedTextField().setText("Select a date...");
		
		datePickerFrom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Day: " + model.getDay() + ", Month: " + model.getMonth() + ", Year: " + model.getYear());
			}
		});

//				------------------------------------------
		UtilDateModel model1 = new UtilDateModel();
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
		JDatePickerImpl datePickerUntil = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		GridBagConstraints gbc_datePickerUntil = new GridBagConstraints();
		gbc_datePickerUntil.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePickerUntil.insets = new Insets(0, 0, 0, 5);			
		gbc_datePickerUntil.gridx = 1;
		gbc_datePickerUntil.gridy = 1;
		datePickerUntil.getJFormattedTextField().setText("Select a date...");
		this.add(datePickerUntil, gbc_datePickerUntil);
	}

}
