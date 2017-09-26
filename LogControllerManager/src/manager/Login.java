package manager;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Point;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * Login window frame
 * @author Ervinas
 *
 */
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private JButton login;
	private JButton cancel;
	

	/**
	 * Launch the application.
	 * 
	 * @param args arguments from console (not used)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 150);
		centerFrame();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints g = new GridBagConstraints();
		
		g.gridx = 0;
		g.gridy = 0;
		contentPane.add(new JLabel("Username: "), g);
		
		this.username = new JTextField(10);
		g.gridx = 1;
		g.gridy = 0;
		contentPane.add(username, g);
		
		g.gridx = 0;
		g.gridy = 1;
		contentPane.add(new JLabel("Password: "), g);
		
		this.password = new JPasswordField(10);
		g.gridx = 1;
		g.gridy = 1;
		contentPane.add(password, g);
		
		this.login = new JButton("Login");
		g.gridx = 0;
		g.gridy = 2;
		contentPane.add(login, g);
		
		this.cancel = new JButton("Cancel");
		g.gridx = 1;
		g.gridy = 2;
		contentPane.add(cancel, g);
	}
	
	/**
	 * Centering frame method
	 */
    private void centerFrame() {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;    
        setLocation(dx, dy);
    }
}
