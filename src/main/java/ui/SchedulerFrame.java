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
        getContentPane().setBackground(new Color(0xF5F5F5));
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x4CAF50), 2, true),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        panel.setLayout(new BorderLayout(0, 30));
        panel.setPreferredSize(new Dimension(600, 250));

        JLabel welcomeLabel = new JLabel("Bienvenue " + scheduler.getNom(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0x4CAF50));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(1, 2, 30, 30));
        JButton btnDemandes = new JButton("Gérer les demandes de rendez-vous");
        JButton btnPlanning = new JButton("Organiser le planning");
        JButton[] buttons = {btnDemandes, btnPlanning};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setBackground(new Color(0x4CAF50));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(18, 30, 18, 30));
            btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        menuPanel.add(btnDemandes);
        menuPanel.add(btnPlanning);
        panel.add(menuPanel, BorderLayout.CENTER);

        add(panel, new GridBagConstraints());

        btnDemandes.addActionListener(e -> new DemandesRendezVousFrame(scheduler).setVisible(true));
        btnPlanning.addActionListener(e -> new OrganiserPlanningFrame(scheduler).setVisible(true));
    }
} 