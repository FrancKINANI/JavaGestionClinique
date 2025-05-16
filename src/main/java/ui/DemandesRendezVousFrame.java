package ui;

import javax.swing.*;
import java.awt.*;
import dao.implementations.ImpRendezVousDAO;
import models.RendezVous;
import models.Scheduler;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CellEditorListener;
import java.util.EventObject;
import sql.config.DatabaseConfig;

public class DemandesRendezVousFrame extends JFrame {
    public DemandesRendezVousFrame(Scheduler scheduler) {
        setTitle("Demandes de rendez-vous");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Demandes de rendez-vous à traiter", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Date", "Heure", "Patient", "Médecin", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Custom renderer/editor for action buttons
        table.getColumn("Action").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                JButton btnValider = new JButton("Valider");
                JButton btnRefuser = new JButton("Refuser");
                panel.add(btnValider);
                panel.add(btnRefuser);
                return panel;
            }
        });
        table.getColumn("Action").setCellEditor(new TableCellEditor() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JButton btnValider = new JButton("Valider");
            JButton btnRefuser = new JButton("Refuser");
            int editingRow = -1;
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                editingRow = row;
                panel.removeAll();
                panel.add(btnValider);
                panel.add(btnRefuser);
                return panel;
            }
            public Object getCellEditorValue() { return null; }
            public boolean isCellEditable(EventObject e) { return true; }
            public boolean shouldSelectCell(EventObject e) { return true; }
            public boolean stopCellEditing() { return true; }
            public void cancelCellEditing() {}
            public void addCellEditorListener(CellEditorListener l) {}
            public void removeCellEditorListener(CellEditorListener l) {}
            {
                btnValider.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleAction(editingRow, true);
                    }
                });
                btnRefuser.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleAction(editingRow, false);
                    }
                });
            }
            private void handleAction(int row, boolean valider) {
                try {
                    Connection conn = DatabaseConfig.getConnection();
                    List<RendezVous> rdvs = new ImpRendezVousDAO(conn).getAllRendezVous();
                    int rdvIndex = 0;
                    for (RendezVous r : rdvs) {
                        if ("PLANIFIE".equalsIgnoreCase(r.getStatut().toString())) {
                            if (rdvIndex == row) {
                                r.setStatut(valider ? "CONFIRME" : "ANNULE");
                                new ImpRendezVousDAO(conn).updateRendezVous(r);
                                JOptionPane.showMessageDialog(DemandesRendezVousFrame.this, valider ? "Rendez-vous validé !" : "Rendez-vous refusé !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                refreshTable(model);
                                break;
                            }
                            rdvIndex++;
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DemandesRendezVousFrame.this, "Erreur lors de la mise à jour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        refreshTable(model);

        JButton retourBtn = new JButton("Retour");
        add(retourBtn, BorderLayout.SOUTH);
    }

    private void refreshTable(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConfig.getConnection();
            List<RendezVous> rdvs = new ImpRendezVousDAO(conn).getAllRendezVous();
            for (RendezVous r : rdvs) {
                if ("PLANIFIE".equalsIgnoreCase(r.getStatut().toString())) {
                    String date = r.getTimeSlot() != null ? r.getTimeSlot().getDebut().toLocalDate().toString() : "";
                    String heure = r.getTimeSlot() != null ? r.getTimeSlot().getDebut().toLocalTime().toString() : "";
                    String patient = r.getPatient() != null ? r.getPatient().getNom() + " " + r.getPatient().getPrenom() : "";
                    String medecin = r.getDocteur() != null ? r.getDocteur().getNom() + " " + r.getDocteur().getPrenom() : "";
                    model.addRow(new Object[]{date, heure, patient, medecin, ""});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du rafraîchissement : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
} 