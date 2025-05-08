package ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dao.implementations.ImpRoleDAO;
import dao.implementations.ImpUtilisateurDAO;
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
    private Connection conn; // Déclarer la connexion comme un attribut de classe
    private AuthenticationService authService;
    private ImpUtilisateurDAO userDAO;
    private ImpRoleDAO roleDAO;

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
        
        // Initialiser la connexion
        initializeConnection();
        
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
                // Votre code ici
            }
        });
        btnForgetPasswd.setBounds(510, 166, 134, 21);
        contentPane.add(btnForgetPasswd);
        
        JButton btnLogin = new JButton("Se connecter");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = EmailField.getText();
                String password = new String(passwordField.getPassword());
                
                // Check if the email and password fields are not empty
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                    return;
                }
                
                // Vérifier si la connexion est toujours valide
                try {
                    if (conn == null || conn.isClosed()) {
                        initializeConnection(); // Réinitialiser la connexion si nécessaire
                    }
                    
                    if (authService.authenticate(email, password)) {
                        JOptionPane.showMessageDialog(null, "Authentification réussie !");
                        
                        // Check user's role
                        int userId = userDAO.getIdByEmail(email);
                        if (userId == -1) {
                            JOptionPane.showMessageDialog(null, "Utilisateur non trouvé.");
                            return;
                        }
                        
                        // Get the user's role
                        String role = roleDAO.getRoleById(userId);
                        JOptionPane.showMessageDialog(null, "Votre rôle est : " + role);
                        
                        // Ouvrir l'interface appropriée selon le rôle
                        openAppropriateInterface(role);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Échec de l'authentification. Vérifiez vos identifiants.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données: " + ex.getMessage());
                    ex.printStackTrace();
                    try {
                        initializeConnection(); // Tentative de réinitialisation
                    } catch (SQLException | IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
        
        // Fermer la connexion lorsque la fenêtre se ferme
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
            }
        });
    }
    
    /**
     * Initialise la connexion et les services DAO
     */
    private void initializeConnection() throws SQLException, IOException {
        conn = DatabaseConfig.getConnection();
        authService = new AuthenticationService(conn);
        userDAO = new ImpUtilisateurDAO(conn);
        roleDAO = new ImpRoleDAO(conn);
    }
    
    /**
     * Ferme proprement la connexion
     */
    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Ouvre l'interface appropriée selon le rôle de l'utilisateur
     */
    private void openAppropriateInterface(String role) {
        // Implémenter selon vos besoins
        if ("admin".equalsIgnoreCase(role)) {
            // Ouvrir interface admin
        } else if ("medecin".equalsIgnoreCase(role)) {
            // Ouvrir interface médecin
        } else if ("patient".equalsIgnoreCase(role)) {
            // Ouvrir interface patient
        }
        // Fermer la fenêtre de login
        // this.dispose();
    }
}