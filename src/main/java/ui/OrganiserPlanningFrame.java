package ui;

import javax.swing.*;
import java.awt.*;
import dao.implementations.ImpTimeSlotDAO;
import sql.config.DatabaseConfig;
import models.TimeSlot;
import models.Scheduler;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

public class OrganiserPlanningFrame extends JFrame {
    public OrganiserPlanningFrame(Scheduler scheduler) {
        setTitle("Organiser le planning");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Organisation du planning", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Heure début", "Heure fin", "Statut", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable(model);

        // Custom editor for action column
        table.getColumn("Action").setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"DISPONIBLE", "RESERVE", "INDISPONIBLE"})) {
            @Override
            public Object getCellEditorValue() {
                String newStatut = (String) super.getCellEditorValue();
                int row = table.getSelectedRow();
                if (row >= 0) {
                    try {
                        Connection conn = DatabaseConfig.getConnection();
                        List<TimeSlot> slots = new ImpTimeSlotDAO(conn).getAllTimeSlots();
                        TimeSlot slot = slots.get(row);
                        slot.setStatut(newStatut);
                        new ImpTimeSlotDAO(conn).updateTimeSlot(slot);
                        refreshTable(model);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(OrganiserPlanningFrame.this, "Erreur lors de la mise à jour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
                return newStatut;
            }
        });

        JButton retourBtn = new JButton("Retour");
        add(retourBtn, BorderLayout.SOUTH);
    }

    private void refreshTable(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConfig.getConnection();
            List<TimeSlot> slots = new ImpTimeSlotDAO(conn).getAllTimeSlots();
            for (TimeSlot t : slots) {
                String date = t.getDebut() != null ? t.getDebut().toLocalDate().toString() : "";
                String heureDebut = t.getDebut() != null ? t.getDebut().toLocalTime().toString() : "";
                String heureFin = t.getFin() != null ? t.getFin().toLocalTime().toString() : "";
                String statut = t.getStatut() != null ? t.getStatut().toString() : "";
                model.addRow(new Object[]{date, heureDebut, heureFin, statut, statut});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des créneaux : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
} 