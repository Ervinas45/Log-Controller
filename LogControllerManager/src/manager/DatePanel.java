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
import javax.swing.JButton;

public class DatePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String dateFrom;
	public String dateUntil;

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
		gbc_lblDatEUntil.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatEUntil.gridx = 0;
		gbc_lblDatEUntil.gridy = 1;
		add(lblDatEUntil, gbc_lblDatEUntil);
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		
		JDatePickerImpl datePickerFrom = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		GridBagConstraints gbc_datePickerFrom = new GridBagConstraints();
		gbc_datePickerFrom.fill = GridBagConstraints.HORIZONTAL;

		gbc_datePickerFrom.insets = new Insets(0, 0, 5, 0);
		gbc_datePickerFrom.gridx = 1;
		gbc_datePickerFrom.gridy = 0;
		this.add(datePickerFrom, gbc_datePickerFrom);
		datePickerFrom.getJFormattedTextField().setText("Select a date...");
		
		datePickerFrom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int year = model.getYear();
				int month = model.getMonth();
				int day = model.getDay();
				if(year > 0 && month > 0 && day > 0){
					dateFrom = "";
					dateFrom = model.getYear() + "-" + (model.getMonth() + 1) + "-" + model.getDay() + " 00:00:00";
				}
			}
		});

		UtilDateModel model1 = new UtilDateModel();
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
		JDatePickerImpl datePickerUntil = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		GridBagConstraints gbc_datePickerUntil = new GridBagConstraints();
		gbc_datePickerUntil.insets = new Insets(0, 0, 5, 0);
		gbc_datePickerUntil.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePickerUntil.gridx = 1;
		gbc_datePickerUntil.gridy = 1;
		datePickerUntil.getJFormattedTextField().setText("Select a date...");
		datePickerUntil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int year = model1.getYear();
				int month = model1.getMonth();
				int day = model1.getDay();
				if(year > 0 && month > 0 && day > 0){
					dateUntil = "";
					dateUntil = model1.getYear() + "-" + (model1.getMonth() + 1) + "-" + model1.getDay() + " 23:59:59";
				}
			}
		});
		this.add(datePickerUntil, gbc_datePickerUntil);
		
		JButton btnReset = new JButton("Reset");
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.fill = GridBagConstraints.FIRST_LINE_START;
		gbc_btnReset.insets = new Insets(0, 0, 5, 0);
		gbc_btnReset.gridx = 1;
		gbc_btnReset.gridy = 2;
		add(btnReset, gbc_btnReset);
		
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dateFrom = null;
				dateUntil = null;
				datePickerFrom.getJFormattedTextField().setText("Select a date...");
				datePickerUntil.getJFormattedTextField().setText("Select a date...");
			}
		});
	}
	
	public String getDateFrom() {
		return this.dateFrom;
	}
	
	public String getDateUntil() {
		return this.dateUntil;
	}

}
