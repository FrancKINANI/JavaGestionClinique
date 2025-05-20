package ui;

import javax.swing.*;
import java.awt.*;
import models.Patient;

public class PatientFrame extends JFrame {
    public PatientFrame(Patient patient) {
        setTitle("Espace Patient " + patient.getNom() + " - Clinique Al Andalous");
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

        JLabel welcomeLabel = new JLabel("Bienvenue dans votre espace patient", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0x4CAF50));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(1, 3, 30, 30));
        JButton btnRdv = new JButton("Prendre un rendez-vous");
        JButton btnHistorique = new JButton("Historique des rendez-vous");
        JButton btnCommentaires = new JButton("Commentaires du médecin");
        JButton[] buttons = {btnRdv, btnHistorique, btnCommentaires};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setBackground(new Color(0x4CAF50));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        menuPanel.add(btnRdv);
        menuPanel.add(btnHistorique);
        menuPanel.add(btnCommentaires);
        panel.add(menuPanel, BorderLayout.CENTER);

        add(panel, new GridBagConstraints());

        btnRdv.addActionListener(e -> new PrendreRendezVousFrame(patient).setVisible(true));
        btnHistorique.addActionListener(e -> new HistoriqueRendezVousFrame(patient).setVisible(true));
        btnCommentaires.addActionListener(e -> new CommentairesMedecinFrame(patient).setVisible(true));
    }
} 