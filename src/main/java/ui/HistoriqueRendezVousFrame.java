package ui;

import javax.swing.*;
import java.awt.*;
import dao.implementations.ImpRendezVousDAO;
import sql.config.DatabaseConfig;
import models.RendezVous;
import models.Patient;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

public class HistoriqueRendezVousFrame extends JFrame {
    public HistoriqueRendezVousFrame(Patient patient) {
        setTitle("Historique des rendez-vous");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Historique de vos rendez-vous", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Heure", "Médecin", "Statut"}, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        try {
            Connection conn = DatabaseConfig.getConnection();
            List<RendezVous> rdvs = new ImpRendezVousDAO(conn).getRendezVousByPatientId(patient.getId());
            for (RendezVous r : rdvs) {
                String date = r.getTimeSlot() != null ? r.getTimeSlot().getDebut().toLocalDate().toString() : "";
                String heure = r.getTimeSlot() != null ? r.getTimeSlot().getDebut().toLocalTime().toString() : "";
                String medecin = r.getDocteur() != null ? r.getDocteur().getNom() + " " + r.getDocteur().getPrenom() : "";
                String statut = r.getStatut() != null ? r.getStatut().toString() : "";
                model.addRow(new Object[]{date, heure, medecin, statut});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'historique : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JButton retourBtn = new JButton("Retour");
        add(retourBtn, BorderLayout.SOUTH);
    }
} 