package ui;

<<<<<<< HEAD
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
=======
import dao.implementations.ImpRoleDAO;
import dao.implementations.ImpUtilisateurDAO;
import identification.AuthenticationService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import sql.config.DatabaseConfig;

public class LoginUI extends javax.swing.JFrame {
    // Class-level variables
>>>>>>> 682628f10b4231d1259ea5f51b647b5f995d5cc3
    private Connection conn;
    private AuthenticationService authService;
    private ImpUtilisateurDAO userDAO;
    private ImpRoleDAO roleDAO;

<<<<<<< HEAD
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginUI frame = new LoginUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
=======
    /**
     * Creates new form LoginUI
     */
    public LoginUI() {
        initComponents();
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
>>>>>>> 682628f10b4231d1259ea5f51b647b5f995d5cc3
            }
        });
    }

<<<<<<< HEAD
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
=======
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        emailTextField = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Login");
        jLabel2.setText("Email");
        jLabel3.setText("Password");

        btnLogin.setText("Se connecter");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(251, 251, 251)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordField)
                            .addComponent(emailTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(btnLogin)))
                .addContainerGap(239, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(btnLogin)
                .addGap(74, 74, 74))
        );

        pack();
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String email = emailTextField.getText();
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
                if (role == null) {
                    JOptionPane.showMessageDialog(null, "Rôle non trouvé.");
                    return;
                }
                
                // Ouvrir l'interface appropriée selon le rôle
                openAppropriateInterface(role, userId);
                
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
            e1.printStackTrace();
        }
    }                                        

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }
    
    private void initializeConnection() throws SQLException, IOException {
        conn = DatabaseConfig.getConnection();
        authService = new AuthenticationService(conn);
        userDAO = new ImpUtilisateurDAO(conn);
        roleDAO = new ImpRoleDAO(conn);
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
    
    private void openAppropriateInterface(String role, int userId) throws SQLException, IOException {
        // Implémenter selon vos besoins
        if ("admin".equalsIgnoreCase(role)) {
            // Ouvrir interface admin
        } else if ("medecin".equalsIgnoreCase(role)) {
            // Ouvrir interface médecin
        } else if ("patient".equalsIgnoreCase(role)) {
            // Ouvrir interface patient
        }
        // Fermer la fenêtre de login
        this.dispose();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JTextField emailTextField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration                   
>>>>>>> 682628f10b4231d1259ea5f51b647b5f995d5cc3
}
