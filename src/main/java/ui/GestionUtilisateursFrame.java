package ui;

import javax.swing.*;
import java.awt.*;
import dao.implementations.ImpUtilisateurDAO;
import sql.config.DatabaseConfig;
import models.Utilisateur;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;
import models.Patient;
import models.Docteur;
import models.Scheduler;
import models.Admin;
import dao.implementations.ImpPatientDAO;
import dao.implementations.ImpDocteurDAO;
import dao.implementations.ImpSchedulerDAO;

public class GestionUtilisateursFrame extends JFrame {
    public GestionUtilisateursFrame() {
        setTitle("Gestion des utilisateurs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Gestion des utilisateurs", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Table des utilisateurs
        DefaultTableModel model = new DefaultTableModel(new String[]{"Nom", "Prénom", "Email", "Rôle"}, 0);
        JTable table = new JTable(model);
        table.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Chargement des utilisateurs
        try {
            Connection conn = DatabaseConfig.getConnection();
            ImpUtilisateurDAO userDAO = new ImpUtilisateurDAO(conn);
            List<Utilisateur> users = userDAO.getAllUtilisateurs();
            for (Utilisateur u : users) {
                model.addRow(new Object[]{u.getNom(), u.getPrenom(), u.getEmail(), u.getRole() != null ? u.getRole().getRole().name() : ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        btnPanel.add(btnAjouter);
        btnPanel.add(btnModifier);
        btnPanel.add(btnSupprimer);
        add(btnPanel, BorderLayout.SOUTH);

        btnAjouter.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Ajouter un utilisateur", true);
            dialog.setSize(400, 400);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout());

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

            JComboBox<String> roleBox = new JComboBox<>(new String[]{"PATIENT", "DOCTEUR", "SCHEDULER", "ADMINISTRATEUR"});
            formPanel.add(new JLabel("Rôle : "));
            formPanel.add(roleBox);

            JTextField nomField = new JTextField();
            JTextField prenomField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField telField = new JTextField();
            JTextField adresseField = new JTextField();
            JPasswordField passField = new JPasswordField();
            formPanel.add(new JLabel("Nom : "));
            formPanel.add(nomField);
            formPanel.add(new JLabel("Prénom : "));
            formPanel.add(prenomField);
            formPanel.add(new JLabel("Email : "));
            formPanel.add(emailField);
            formPanel.add(new JLabel("Téléphone : "));
            formPanel.add(telField);
            formPanel.add(new JLabel("Adresse : "));
            formPanel.add(adresseField);
            formPanel.add(new JLabel("Mot de passe : "));
            formPanel.add(passField);

            JPanel specificPanel = new JPanel();
            specificPanel.setLayout(new BoxLayout(specificPanel, BoxLayout.Y_AXIS));
            formPanel.add(specificPanel);

            // Champs spécifiques selon le rôle
            JTextField secuField = new JTextField();
            JTextField dossierField = new JTextField();
            JTextField specialiteField = new JTextField();
            JTextField matriculeField = new JTextField();
            JTextField fonctionField = new JTextField();

            roleBox.addActionListener(ev -> {
                specificPanel.removeAll();
                String role = (String) roleBox.getSelectedItem();
                if ("PATIENT".equals(role)) {
                    specificPanel.add(new JLabel("Numéro Sécurité Sociale : "));
                    specificPanel.add(secuField);
                    specificPanel.add(new JLabel("Dossier médical : "));
                    specificPanel.add(dossierField);
                } else if ("DOCTEUR".equals(role)) {
                    specificPanel.add(new JLabel("Spécialité : "));
                    specificPanel.add(specialiteField);
                    specificPanel.add(new JLabel("Matricule : "));
                    specificPanel.add(matriculeField);
                } else if ("SCHEDULER".equals(role)) {
                    specificPanel.add(new JLabel("Fonction : "));
                    specificPanel.add(fonctionField);
                }
                specificPanel.revalidate();
                specificPanel.repaint();
            });
            // Initialiser les champs spécifiques
            roleBox.setSelectedIndex(0);

            dialog.add(formPanel, BorderLayout.CENTER);
            JButton validerBtn = new JButton("Valider");
            dialog.add(validerBtn, BorderLayout.SOUTH);

            validerBtn.addActionListener(ev -> {
                String role = (String) roleBox.getSelectedItem();
                try {
                    Connection conn = DatabaseConfig.getConnection();
                    if ("PATIENT".equals(role)) {
                        Patient p = new Patient();
                        p.setNom(nomField.getText());
                        p.setPrenom(prenomField.getText());
                        p.setEmail(emailField.getText());
                        p.setTelephone(telField.getText());
                        p.setAdresse(adresseField.getText());
                        p.setPassword(new String(passField.getPassword()));
                        p.setNumeroSecu(secuField.getText());
                        p.setDossierMedical(dossierField.getText());
                        new ImpPatientDAO(conn).addPatient(p);
                    } else if ("DOCTEUR".equals(role)) {
                        Docteur d = new Docteur();
                        d.setNom(nomField.getText());
                        d.setPrenom(prenomField.getText());
                        d.setEmail(emailField.getText());
                        d.setTelephone(telField.getText());
                        d.setAdresse(adresseField.getText());
                        d.setPassword(new String(passField.getPassword()));
                        d.setSpecialite(specialiteField.getText());
                        d.setMatricule(matriculeField.getText());
                        new ImpDocteurDAO(conn).addDocteur(d);
                    } else if ("SCHEDULER".equals(role)) {
                        Scheduler s = new Scheduler();
                        s.setNom(nomField.getText());
                        s.setPrenom(prenomField.getText());
                        s.setEmail(emailField.getText());
                        s.setTelephone(telField.getText());
                        s.setAdresse(adresseField.getText());
                        s.setPassword(new String(passField.getPassword()));
                        s.setFonction(fonctionField.getText());
                        new ImpSchedulerDAO(conn).addScheduler(s);
                    } else if ("ADMINISTRATEUR".equals(role)) {
                        Admin a = new Admin();
                        a.setNom(nomField.getText());
                        a.setPrenom(prenomField.getText());
                        a.setEmail(emailField.getText());
                        a.setTelephone(telField.getText());
                        a.setAdresse(adresseField.getText());
                        a.setPassword(new String(passField.getPassword()));
                        // Ajout via ImpUtilisateurDAO (à adapter si besoin)
                        // new ImpUtilisateurDAO(conn).addAdmin(a);
                        JOptionPane.showMessageDialog(dialog, "Ajout d'admin à implémenter.");
                    }
                    dialog.dispose();
                    // Rafraîchir la table
                    model.setRowCount(0);
                    List<Utilisateur> users = new ImpUtilisateurDAO(DatabaseConfig.getConnection()).getAllUtilisateurs();
                    for (Utilisateur u : users) {
                        model.addRow(new Object[]{u.getNom(), u.getPrenom(), u.getEmail(), u.getRole() != null ? u.getRole().getRole().name() : ""});
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.setVisible(true);
        });
    }
} 