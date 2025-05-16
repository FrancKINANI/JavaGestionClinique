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
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Médecin : "));
        JComboBox<Docteur> medecinBox = new JComboBox<>();
        add(medecinBox);

        add(new JLabel("Créneau : "));
        JComboBox<TimeSlot> creneauBox = new JComboBox<>();
        add(creneauBox);

        add(new JLabel("Commentaire : "));
        JTextField commentaireField = new JTextField();
        add(commentaireField);

        add(new JLabel(""));
        JButton validerBtn = new JButton("Valider");
        add(validerBtn);

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