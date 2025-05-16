package ui;

import javax.swing.*;
import java.awt.*;
import models.Scheduler;

public class SchedulerFrame extends JFrame {
    public SchedulerFrame(Scheduler scheduler) {
        setTitle("Espace Scheduler - Clinique Al Andalous");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenue " + scheduler.getNom(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 2, 20, 20));
        JButton btnDemandes = new JButton("Gérer les demandes de rendez-vous");
        JButton btnPlanning = new JButton("Organiser le planning");
        menuPanel.add(btnDemandes);
        menuPanel.add(btnPlanning);
        add(menuPanel, BorderLayout.CENTER);

        btnDemandes.addActionListener(e -> new DemandesRendezVousFrame(scheduler).setVisible(true));
        btnPlanning.addActionListener(e -> new OrganiserPlanningFrame(scheduler).setVisible(true));
    }
} 