package ui;

import javax.swing.*;
import java.awt.*;
import models.Docteur;

public class DocteurFrame extends JFrame {
    public DocteurFrame(Docteur docteur) {
        setTitle("Espace Docteur - Clinique Al Andalous");
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

        JLabel welcomeLabel = new JLabel("Bienvenue Dr. " + docteur.getNom(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0x4CAF50));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(1, 3, 30, 30));
        JButton btnRdvJour = new JButton("Rendez-vous du jour");
        JButton btnDossier = new JButton("Dossier des patients");
        JButton btnCommentaires = new JButton("Saisir un commentaire");
        JButton[] buttons = {btnRdvJour, btnDossier, btnCommentaires};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setBackground(new Color(0x4CAF50));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        menuPanel.add(btnRdvJour);
        menuPanel.add(btnDossier);
        menuPanel.add(btnCommentaires);
        panel.add(menuPanel, BorderLayout.CENTER);

        add(panel, new GridBagConstraints());

        btnRdvJour.addActionListener(e -> new RendezVousDuJourFrame(docteur).setVisible(true));
        btnDossier.addActionListener(e -> new DossierPatientFrame(docteur).setVisible(true));
        btnCommentaires.addActionListener(e -> new SaisirCommentaireFrame(docteur).setVisible(true));
    }
} 