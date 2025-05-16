package ui;

import javax.swing.*;
import java.awt.*;
import models.Docteur;

public class RendezVousDuJourFrame extends JFrame {
    public RendezVousDuJourFrame(Docteur docteur) {
        setTitle("Rendez-vous du jour");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Vos rendez-vous du jour", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JTable table = new JTable(new Object[][]{}, new String[]{"Heure", "Patient", "Motif", "Statut"});
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton retourBtn = new JButton("Retour");
        add(retourBtn, BorderLayout.SOUTH);
    }
} 