package ui;

import javax.swing.*;
import java.awt.*;

public class GestionRolesFrame extends JFrame {
    public GestionRolesFrame() {
        setTitle("Gestion des rôles");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Gestion des rôles", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JTable table = new JTable(new Object[][]{}, new String[]{"Utilisateur", "Rôle"});
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        btnPanel.add(btnAjouter);
        btnPanel.add(btnModifier);
        btnPanel.add(btnSupprimer);
        add(btnPanel, BorderLayout.SOUTH);
    }
} 