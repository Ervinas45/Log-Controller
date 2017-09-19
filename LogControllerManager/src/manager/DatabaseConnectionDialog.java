package manager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class DatabaseConnectionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField address;
	private JTextField port;
	private JTextField username;
	private JPasswordField password;
	public boolean answer;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			test dialog = new test();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public DatabaseConnectionDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblDatabaseAddress = new JLabel("Database address: ");
			GridBagConstraints gbc_lblDatabaseAddress = new GridBagConstraints();
			gbc_lblDatabaseAddress.anchor = GridBagConstraints.EAST;
			gbc_lblDatabaseAddress.insets = new Insets(0, 0, 5, 5);
			gbc_lblDatabaseAddress.gridx = 0;
			gbc_lblDatabaseAddress.gridy = 0;
			contentPanel.add(lblDatabaseAddress, gbc_lblDatabaseAddress);
		}
		{
			address = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 0;
			contentPanel.add(address, gbc_textField);
			address.setColumns(10);
		}
		{
			JLabel lblPort = new JLabel("Port:");
			GridBagConstraints gbc_lblPort = new GridBagConstraints();
			gbc_lblPort.anchor = GridBagConstraints.EAST;
			gbc_lblPort.insets = new Insets(0, 0, 5, 5);
			gbc_lblPort.gridx = 0;
			gbc_lblPort.gridy = 1;
			contentPanel.add(lblPort, gbc_lblPort);
		}
		{
			port = new JTextField();
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 5, 0);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 1;
			gbc_textField_1.gridy = 1;
			contentPanel.add(port, gbc_textField_1);
			port.setColumns(10);
		}
		{
			JLabel lblUsername = new JLabel("Username:");
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.anchor = GridBagConstraints.EAST;
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 0;
			gbc_lblUsername.gridy = 2;
			contentPanel.add(lblUsername, gbc_lblUsername);
		}
		{
			username = new JTextField();
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.insets = new Insets(0, 0, 5, 0);
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.gridx = 1;
			gbc_textField_2.gridy = 2;
			contentPanel.add(username, gbc_textField_2);
			username.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 3;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			password = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 1;
			gbc_passwordField.gridy = 3;
			contentPanel.add(password, gbc_passwordField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton loginButton = new JButton("Login");
				loginButton.setActionCommand("Login");
				buttonPane.add(loginButton);
				getRootPane().setDefaultButton(loginButton);
				
				loginButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						String dbAddress = address.getText();
						String dbPort = port.getText();
						String dbUsername = username.getText();
						char[] dbPassword = password.getPassword();
						
						if( dbAddress.isEmpty() || dbPort.isEmpty() || dbUsername.isEmpty() || dbPassword.length == 0) {
							JOptionPane.showMessageDialog(getParent(),
								    "All fields have to be filled!");
						}
						else {
							if(databaseValidation(dbAddress, dbPort, dbUsername, dbPassword)) {
								JOptionPane.showMessageDialog(getParent(),
									    "Connected!");
								answer = true;
								setVisible(false);
							}
							else {
								JOptionPane.showMessageDialog(getParent(),
									    "There was error connecting to the database!");
								answer = false;
							}
						}
						System.out.println(answer);
						
					}
					
				});
				
				
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						dispose();
						
					}
					
				});
			}
		}
	}

	public boolean databaseValidation(String address, String port, String username, char[] password) {
		
		String pw = String.valueOf(password);
		System.out.println(pw);
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port +  "/logctrl?user=" + username + "&password=" + pw );
		} catch (SQLException e) {
			System.out.println("Error connecting to database!");
			return false;
		}
		System.out.println("Connected");
		return true;
	
	}
}
