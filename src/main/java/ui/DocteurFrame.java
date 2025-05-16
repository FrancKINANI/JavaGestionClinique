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
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenue Dr. " + docteur.getNom(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3, 20, 20));
        JButton btnRdvJour = new JButton("Rendez-vous du jour");
        JButton btnDossier = new JButton("Dossier des patients");
        JButton btnCommentaires = new JButton("Saisir un commentaire");
        menuPanel.add(btnRdvJour);
        menuPanel.add(btnDossier);
        menuPanel.add(btnCommentaires);
        add(menuPanel, BorderLayout.CENTER);

        btnRdvJour.addActionListener(e -> new RendezVousDuJourFrame(docteur).setVisible(true));
        btnDossier.addActionListener(e -> new DossierPatientFrame(docteur).setVisible(true));
        btnCommentaires.addActionListener(e -> new SaisirCommentaireFrame(docteur).setVisible(true));
    }
} 