package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import identification.AuthenticationService;
import sql.config.DatabaseConfig;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField EmailField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public LoginFrame() throws IOException, SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Connection conn = DatabaseConfig.getConnection();
		AuthenticationService authService = new AuthenticationService(conn);
		
		JLabel WelcomeLabel = new JLabel("Clinique Al Andalous: Welcome");
		WelcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		WelcomeLabel.setBounds(32, 29, 598, 97);
		contentPane.add(WelcomeLabel);
		
		JLabel EmailLabel = new JLabel("Email");
		EmailLabel.setBounds(32, 170, 99, 13);
		contentPane.add(EmailLabel);
		
		JLabel PasswdLabel = new JLabel("Mot de passe");
		PasswdLabel.setBounds(32, 256, 99, 13);
		contentPane.add(PasswdLabel);
		
		EmailField = new JTextField();
		EmailField.setBounds(182, 157, 280, 39);
		contentPane.add(EmailField);
		EmailField.setColumns(10);
		
		JButton btnForgetPasswd = new JButton("Mot de passe oublié ");
		btnForgetPasswd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnForgetPasswd.setBounds(510, 166, 134, 21);
		contentPane.add(btnForgetPasswd);
		
		JButton btnLogin = new JButton("Se connecter");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = EmailField.getText();
				String password = new String(passwordField.getPassword());
				
				if (authService.authenticate(email, password)) {
					JOptionPane.showMessageDialog(null, "Authentification réussie !");
				} else {
					JOptionPane.showMessageDialog(null, "Échec de l'authentification. Vérifiez vos identifiants.");
				}
			}
		});
		btnLogin.setBounds(259, 354, 134, 21);
		contentPane.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(182, 243, 280, 39);
		contentPane.add(passwordField);
		
		JButton btnCreateAccount = new JButton("Créer un compte");
		btnCreateAccount.setBounds(510, 252, 134, 21);
		contentPane.add(btnCreateAccount);
	}
}
