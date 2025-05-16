package ui;

import dao.implementations.ImpRendezVousDAO;
import java.awt.BorderLayout;
import java.awt.Font;
import models.RendezVous;
import models.Patient;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import sql.config.DatabaseConfig;

public class CommentairesMedecinFrame extends JFrame {

    public CommentairesMedecinFrame(Patient patient) {
        setTitle("Commentaires du médecin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Commentaires de vos médecins", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Médecin", "Commentaire"}, 0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        try {
            Connection conn = DatabaseConfig.getConnection();
            List<RendezVous> rdvs = new ImpRendezVousDAO(conn).getRendezVousByPatientId(patient.getId());
            for (RendezVous r : rdvs) {
                String date = r.getTimeSlot() != null ? r.getTimeSlot().getDebut().toLocalDate().toString() : "";
                String medecin = r.getDocteur() != null ? r.getDocteur().getNom() + " " + r.getDocteur().getPrenom() : "";
                String commentaire = r.getCommentaire() != null ? r.getCommentaire() : "";
                model.addRow(new Object[]{date, medecin, commentaire});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des commentaires : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JButton retourBtn = new JButton("Retour");
        add(retourBtn, BorderLayout.SOUTH);
    }
} 