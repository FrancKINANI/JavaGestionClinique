package ui;

import dao.implementations.ImpRoleDAO;
import dao.implementations.ImpUtilisateurDAO;
import identification.AuthenticationService;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import sql.config.DatabaseConfig;
import java.awt.*;
import javax.swing.*;

public class LoginUI extends javax.swing.JFrame {
    // Class-level variables
    private Connection conn;
    private AuthenticationService authService;
    private ImpUtilisateurDAO userDAO;
    private ImpRoleDAO roleDAO;
    private javax.swing.JButton btnLogin;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JPasswordField passwordField;

    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginUI().setVisible(true);
        });
    }

    /**
     * Creates new form LoginUI
     */
    public LoginUI() {
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
            }
        });
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x4CAF50), 2, true),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(520, 260));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 17);

        JLabel jLabel1 = new JLabel("Connexion");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        jLabel1.setForeground(new Color(0x4CAF50));

        JLabel jLabel2 = new JLabel("Email :");
        jLabel2.setFont(labelFont);

        JLabel jLabel3 = new JLabel("Mot de passe :");
        jLabel3.setFont(labelFont);

        emailTextField = new javax.swing.JTextField();
        emailTextField.setFont(fieldFont);
        emailTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCCCCCC), 1, true),
            BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));
        emailTextField.setPreferredSize(new Dimension(260, 36));

        passwordField = new javax.swing.JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCCCCCC), 1, true),
            BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));
        passwordField.setPreferredSize(new Dimension(260, 36));

        btnLogin = new javax.swing.JButton("Se connecter");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogin.setBackground(new Color(0x4CAF50));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(this::btnLoginActionPerformed);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(jLabel1, gbc);

        gbc.gridwidth = 1; gbc.gridy++; gbc.gridx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(jLabel2, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(emailTextField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(jLabel3, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(passwordField, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        getContentPane().setBackground(new Color(0xF5F5F5));
        setLayout(new GridBagLayout());
        add(panel, new GridBagConstraints());

        setTitle("Connexion - Gestion Clinique");
        setSize(700, 420);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String email = emailTextField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            conn = DatabaseConfig.getConnection();
            authService = new AuthenticationService(conn);
            userDAO = new ImpUtilisateurDAO(conn);
            roleDAO = new ImpRoleDAO(conn);
            
            if (authService.authenticate(email, password)) {
                models.Utilisateur user = userDAO.getUtilisateurByEmail(email);
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
    
    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
