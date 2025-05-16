package ui;

import javax.swing.*;
import java.awt.*;
import models.Docteur;

public class DossierPatientFrame extends JFrame {
    public DossierPatientFrame(Docteur docteur) {
        setTitle("Dossier du patient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Nom du patient : "));
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Rechercher");
        searchPanel.add(searchBtn);
        add(searchPanel, BorderLayout.NORTH);

        JTextArea dossierArea = new JTextArea();
        dossierArea.setEditable(false);
        add(new JScrollPane(dossierArea), BorderLayout.CENTER);

        JButton retourBtn = new JButton("Retour");
        add(retourBtn, BorderLayout.SOUTH);
    }
} 