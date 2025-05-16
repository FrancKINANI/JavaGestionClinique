package ui;

import javax.swing.*;
import java.awt.*;

public class GestionRolesFrame extends JFrame {
    public GestionRolesFrame() {
        setTitle("Gestion des rôles");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xF5F5F5));
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x4CAF50), 2, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        panel.setLayout(new BorderLayout(0, 20));
        panel.setPreferredSize(new Dimension(480, 280));

        JLabel title = new JLabel("Gestion des rôles", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0x4CAF50));
        panel.add(title, BorderLayout.NORTH);

        JTable table = new JTable(new Object[][]{}, new String[]{"Utilisateur", "Rôle"});
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setOpaque(false);
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton[] buttons = {btnAjouter, btnModifier, btnSupprimer};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            btn.setBackground(new Color(0x4CAF50));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        btnPanel.add(btnAjouter);
        btnPanel.add(btnModifier);
        btnPanel.add(btnSupprimer);
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel, new GridBagConstraints());
    }
} 