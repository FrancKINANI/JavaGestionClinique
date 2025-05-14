package ui;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.implementations.ImpPatientDAO;
import dao.implementations.ImpRendezVousDAO;
import models.Patient;
import models.RendezVous;
import sql.config.DatabaseConfig;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PatientFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ImpPatientDAO patientDAO;
    private ImpRendezVousDAO rendezVousDAO;
    private Patient patient;
    private Connection conn;
    private JTable tableRendezVous;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PatientFrame frame = new PatientFrame(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PatientFrame(int userId) throws SQLException, IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 712, 469);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel WelcomeLabel = new JLabel("Clinique Al Andalous");
        WelcomeLabel.setBounds(149, 20, 299, 48);
        contentPane.add(WelcomeLabel);
        initializeConnection();

        patient = patientDAO.getPatientById(userId);
        JLabel Welcome = new JLabel("Bienvenue " + patient.getPrenom() + " " + patient.getNom());
        Welcome.setBounds(111, 89, 326, 37);
        contentPane.add(Welcome);

        JLabel ListLabel = new JLabel("Liste des rendez-vous");
        ListLabel.setBounds(204, 215, 180, 13);
        contentPane.add(ListLabel);

        JButton btnReserver = new JButton("Réserver ");
        btnReserver.setBounds(471, 89, 85, 21);
        contentPane.add(btnReserver);

        new DefaultListModel<>();

        // Configuration de la zone de défilement
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 312, 678, 110);
        contentPane.add(scrollPane);
        
        tableRendezVous = new JTable();
        scrollPane.setViewportView(tableRendezVous);

        JButton btnListRendezVous = new JButton("Mes rendez-vous");
        btnListRendezVous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Récupérer les rendez-vous et mettre à jour le modèle
               List<RendezVous> rendezVousList = rendezVousDAO.getRendezVousByPatientId(patient.getId());
               // Utiliser le Jtable pour afficher les rendez-vous
               
                if (rendezVousList.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Aucun rendez-vous trouvé.");
				} else {
					// Mettre à jour le modèle de la JTable
					String[] columnNames = {"Date de création", "Statut", "Durée", "Commentaire", "Docteur", "Salle"};
					Object[][] data = new Object[rendezVousList.size()][6];
					for (int i = 0; i < rendezVousList.size(); i++) {
						RendezVous rdv = rendezVousList.get(i);
						data[i][0] = rdv.getDateCreation();
						data[i][1] = rdv.getStatut();
						data[i][2] = rdv.getDuree();
						data[i][3] = rdv.getCommentaire();
						data[i][4] = rdv.getDocteur().getNom() + " " + rdv.getDocteur().getPrenom();
						data[i][5] = rdv.getSalle().getNumero();
					}
					tableRendezVous.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
				}
            }
        });
        btnListRendezVous.setBounds(471, 181, 85, 21);
        contentPane.add(btnListRendezVous);

        // Fermer la connexion lorsque la fenêtre se ferme
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
            }
        });
    }

    private void initializeConnection() throws SQLException, IOException {
        conn = DatabaseConfig.getConnection();
        patientDAO = new ImpPatientDAO(conn);
        rendezVousDAO = new ImpRendezVousDAO(conn);
    }

    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
