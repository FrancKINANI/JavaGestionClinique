package ui;

import javax.swing.*;
import java.awt.*;
import models.Admin;

public class AdminFrame extends JFrame {
    public AdminFrame(Admin admin) {
        setTitle("Espace Administrateur - Clinique Al Andalous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenue " + admin.getNom(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3, 20, 20));
        JButton btnUtilisateurs = new JButton("Gérer les utilisateurs");
        JButton btnSalles = new JButton("Gérer les salles");
        JButton btnRoles = new JButton("Gérer les rôles");
        menuPanel.add(btnUtilisateurs);
        menuPanel.add(btnSalles);
        menuPanel.add(btnRoles);
        add(menuPanel, BorderLayout.CENTER);

        btnUtilisateurs.addActionListener(e -> new GestionUtilisateursFrame().setVisible(true));
        btnSalles.addActionListener(e -> new GestionSallesFrame().setVisible(true));
        btnRoles.addActionListener(e -> new GestionRolesFrame().setVisible(true));
    }
} 