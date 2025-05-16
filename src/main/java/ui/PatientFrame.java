package ui;

import javax.swing.*;
import java.awt.*;
import models.Patient;

public class PatientFrame extends JFrame {
    public PatientFrame(Patient patient) {
        setTitle("Espace Patient - Clinique Al Andalous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenue dans votre espace patient", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3, 20, 20));
        JButton btnRdv = new JButton("Prendre un rendez-vous");
        JButton btnHistorique = new JButton("Historique des rendez-vous");
        JButton btnCommentaires = new JButton("Commentaires du médecin");
        menuPanel.add(btnRdv);
        menuPanel.add(btnHistorique);
        menuPanel.add(btnCommentaires);
        add(menuPanel, BorderLayout.CENTER);

        btnRdv.addActionListener(e -> new PrendreRendezVousFrame(patient).setVisible(true));
        btnHistorique.addActionListener(e -> new HistoriqueRendezVousFrame(patient).setVisible(true));
        btnCommentaires.addActionListener(e -> new CommentairesMedecinFrame(patient).setVisible(true));
    }
} 