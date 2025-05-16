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
        getContentPane().setBackground(new Color(0xF5F5F5));
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x4CAF50), 2, true),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        panel.setLayout(new BorderLayout(0, 30));
        panel.setPreferredSize(new Dimension(600, 300));

        JLabel welcomeLabel = new JLabel("Bienvenue " + admin.getNom(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0x4CAF50));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(1, 3, 30, 30));
        JButton btnUtilisateurs = new JButton("Gérer les utilisateurs");
        JButton btnSalles = new JButton("Gérer les salles");
        JButton btnRoles = new JButton("Gérer les rôles");
        JButton[] buttons = {btnUtilisateurs, btnSalles, btnRoles};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setBackground(new Color(0x4CAF50));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        menuPanel.add(btnUtilisateurs);
        menuPanel.add(btnSalles);
        menuPanel.add(btnRoles);
        panel.add(menuPanel, BorderLayout.CENTER);

        add(panel, new GridBagConstraints());

        btnUtilisateurs.addActionListener(e -> new GestionUtilisateursFrame().setVisible(true));
        btnSalles.addActionListener(e -> new GestionSallesFrame().setVisible(true));
        btnRoles.addActionListener(e -> new GestionRolesFrame().setVisible(true));
    }
} 