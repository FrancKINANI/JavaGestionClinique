package ui;

import javax.swing.*;
import java.awt.*;
import models.Docteur;

public class SaisirCommentaireFrame extends JFrame {
    public SaisirCommentaireFrame(Docteur docteur) {
        setTitle("Saisir un commentaire de consultation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Patient : "));
        JComboBox<String> patientBox = new JComboBox<>(new String[]{"Patient 1", "Patient 2"}); // À remplacer par la liste réelle
        add(patientBox);

        add(new JLabel("Commentaire : "));
        JTextArea commentaireArea = new JTextArea(3, 20);
        add(new JScrollPane(commentaireArea));

        add(new JLabel(""));
        JButton validerBtn = new JButton("Valider");
        add(validerBtn);
    }
} 