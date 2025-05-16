package ui;

import java.awt.Color;
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import models.Admin;
import models.Utilisateur;
import models.Patient;
import models.Docteur;
import models.Scheduler;

public class LoginUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField emailField;
    private JPasswordField passwordField;
    private Connection conn;
    private AuthenticationService authService;
    private ImpUtilisateurDAO userDAO;
    private ImpRoleDAO roleDAO;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginUI frame = new LoginUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginUI() throws IOException, SQLException {
        DatabaseConfig.initializeDatabase();
        setTitle("Login - Clinique Al Andalous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 550);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(100, 149, 237));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel welcomeLabel = new JLabel("Clinique Al Andalous: Welcome");
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(100, 30, 600, 50);
        contentPane.add(welcomeLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(100, 150, 120, 30);
        contentPane.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 18));
        emailField.setBounds(250, 150, 350, 40);
        contentPane.add(emailField);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(100, 220, 150, 30);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBounds(250, 220, 350, 40);
        contentPane.add(passwordField);

        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(60, 179, 113));
        loginButton.setBounds(250, 300, 180, 50);
        contentPane.add(loginButton);

        JButton createAccountButton = new JButton("Créer un compte");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 20));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setBackground(new Color(255, 140, 0));
        createAccountButton.setBounds(450, 300, 180, 50);
        contentPane.add(createAccountButton);

        JButton forgotPasswordButton = new JButton("Mot de passe oublié");
        forgotPasswordButton.setFont(new Font("Arial", Font.PLAIN, 18));
        forgotPasswordButton.setForeground(Color.WHITE);
        forgotPasswordButton.setBackground(new Color(220, 20, 60));
        forgotPasswordButton.setBounds(250, 370, 380, 40);
        contentPane.add(forgotPasswordButton);

        loginButton.addActionListener(e -> authenticateUser());
    }

    private void authenticateUser() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            conn = DatabaseConfig.getConnection();
            authService = new AuthenticationService(conn);
            userDAO = new ImpUtilisateurDAO(conn);
            ImpRoleDAO roleDAO = new ImpRoleDAO(conn);
            if (authService.authenticate(email, password)) {
                Utilisateur user = userDAO.getUtilisateurByEmail(email);
                if (user != null) {
                    String role = roleDAO.getRoleById(user.getId());
                    switch (role) {
                        case "PATIENT":
                            PatientFrame pf = new PatientFrame((models.Patient) user);
                            pf.setVisible(true);
                            this.dispose();
                            break;
                        case "DOCTEUR":
                            DocteurFrame df = new DocteurFrame((models.Docteur) user);
                            df.setVisible(true);
                            this.dispose();
                            break;
                        case "SCHEDULER":
                            SchedulerFrame sf = new SchedulerFrame((models.Scheduler) user);
                            sf.setVisible(true);
                            this.dispose();
                            break;
                        case "ADMIN":
                            AdminFrame af = new AdminFrame((models.Admin) user);
                            af.setVisible(true);
                            this.dispose();
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Rôle inconnu.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible de récupérer le rôle de l'utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Échec de l'authentification.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
