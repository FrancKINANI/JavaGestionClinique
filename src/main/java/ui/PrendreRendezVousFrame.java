package ui;

import javax.swing.*;
import java.awt.*;
import dao.implementations.ImpDocteurDAO;
import dao.implementations.ImpTimeSlotDAO;
import dao.implementations.ImpRendezVousDAO;
import sql.config.DatabaseConfig;
import models.Docteur;
import models.TimeSlot;
import models.RendezVous;
import models.Patient;
import java.sql.Connection;
import java.util.List;

public class PrendreRendezVousFrame extends JFrame {
    public PrendreRendezVousFrame(Patient patient) {
        setTitle("Prendre un rendez-vous");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xF5F5F5));
        setLayout(new GridBagLayout());

        // Panel central style carte
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x4CAF50), 2, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        panel.setLayout(new GridBagLayout());

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        JLabel medecinLabel = new JLabel("Médecin :");
        medecinLabel.setFont(labelFont);
        JComboBox<Docteur> medecinBox = new JComboBox<>();
        medecinBox.setFont(fieldFont);
        medecinBox.setPreferredSize(new Dimension(220, 28));

        JLabel creneauLabel = new JLabel("Créneau :");
        creneauLabel.setFont(labelFont);
        JComboBox<TimeSlot> creneauBox = new JComboBox<>();
        creneauBox.setFont(fieldFont);
        creneauBox.setPreferredSize(new Dimension(220, 28));

        JLabel commentaireLabel = new JLabel("Commentaire :");
        commentaireLabel.setFont(labelFont);
        JTextField commentaireField = new JTextField();
        commentaireField.setFont(fieldFont);
        commentaireField.setPreferredSize(new Dimension(220, 28));
        commentaireField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCCCCCC), 1, true),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JButton validerBtn = new JButton("Valider");
        validerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        validerBtn.setBackground(new Color(0x4CAF50));
        validerBtn.setForeground(Color.WHITE);
        validerBtn.setFocusPainted(false);
        validerBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        validerBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(medecinLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(medecinBox, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(creneauLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(creneauBox, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(commentaireLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(commentaireField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(validerBtn, gbc);

        add(panel, new GridBagConstraints());

        // Chargement des médecins et créneaux
        try {
            Connection conn = DatabaseConfig.getConnection();
            List<Docteur> medecins = new ImpDocteurDAO(conn).getAllDocteurs();
            for (Docteur d : medecins) medecinBox.addItem(d);
            List<TimeSlot> creneaux = new ImpTimeSlotDAO(conn).getAllTimeSlots();
            for (TimeSlot t : creneaux) if ("DISPONIBLE".equalsIgnoreCase(t.getStatut().toString())) creneauBox.addItem(t);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement médecins/créneaux : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        validerBtn.addActionListener(e -> {
            Docteur docteur = (Docteur) medecinBox.getSelectedItem();
            TimeSlot creneau = (TimeSlot) creneauBox.getSelectedItem();
            String commentaire = commentaireField.getText();
            if (docteur == null || creneau == null) {
                JOptionPane.showMessageDialog(this, "Veuillez choisir un médecin et un créneau.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Connection conn = DatabaseConfig.getConnection();
                RendezVous rdv = new RendezVous();
                rdv.setPatient(patient);
                rdv.setDocteur(docteur);
                rdv.setTimeSlot(creneau);
                rdv.setCommentaire(commentaire);
                rdv.setStatut("PLANIFIE");
                rdv.setDuree(30); // valeur par défaut
                new ImpRendezVousDAO(conn).addRendezVous(rdv);
                JOptionPane.showMessageDialog(this, "Rendez-vous pris avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la prise de rendez-vous : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
} 