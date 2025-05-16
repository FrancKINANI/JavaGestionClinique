package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.RendezVousDAO;
import models.RendezVous;

public class ImpRendezVousDAO implements RendezVousDAO {
	private Connection conn;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private String sql;

	public ImpRendezVousDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void addRendezVous(RendezVous rendezVous) {
		sql = "INSERT INTO rendez_vous (date_creation, statut, duree, commentaire, patient_id, timeSlot_id, salle_id, scheduler_id, docteur_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setTimestamp(1, rendezVous.getDateCreation());
			preparedStatement.setString(2, rendezVous.getStatut().toString());
			preparedStatement.setInt(3, rendezVous.getDuree());
			preparedStatement.setString(4, rendezVous.getCommentaire());
			preparedStatement.setInt(5, rendezVous.getPatient().getId());
			preparedStatement.setInt(6, rendezVous.getTimeSlot().getId());
			preparedStatement.setInt(7, rendezVous.getSalle().getId());
			preparedStatement.setInt(8, rendezVous.getScheduler().getId());
			preparedStatement.setInt(9, rendezVous.getDocteur().getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public void updateRendezVous(RendezVous rendezVous) {
		sql = "UPDATE rendez_vous SET date_creation = ?, statut = ?, duree = ?, commentaire = ?, patient_id = ?, timeSlot_id = ?, salle_id = ?, scheduler_id = ?, docteur_id = ? WHERE id = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setTimestamp(1, rendezVous.getDateCreation());
			preparedStatement.setString(2, rendezVous.getStatut().toString());
			preparedStatement.setInt(3, rendezVous.getDuree());
			preparedStatement.setString(4, rendezVous.getCommentaire());
			preparedStatement.setInt(5, rendezVous.getPatient().getId());
			preparedStatement.setInt(6, rendezVous.getTimeSlot().getId());
			preparedStatement.setInt(7, rendezVous.getSalle().getId());
			preparedStatement.setInt(8, rendezVous.getScheduler().getId());
			preparedStatement.setInt(9, rendezVous.getDocteur().getId());
			preparedStatement.setInt(10, rendezVous.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public void deleteRendezVous(int id) {
		sql = "DELETE FROM rendez_vous WHERE id = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public RendezVous getRendezVousById(int id) {
		sql = "SELECT * FROM rendez_vous WHERE id = ?";
		RendezVous rendezVous = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				rendezVous = new RendezVous();
				rendezVous.setId(resultSet.getInt("id"));
				rendezVous.setDateCreation(resultSet.getTimestamp("date_creation"));
				rendezVous.setStatut(resultSet.getString("statut"));
				rendezVous.setDuree(resultSet.getInt("duree"));
				rendezVous.setCommentaire(resultSet.getString("commentaire"));
				rendezVous.setPatient(new ImpPatientDAO(conn).getPatientById(resultSet.getInt("patient_id")));
				rendezVous.setTimeSlot(new ImpTimeSlotDAO(conn).getTimeSlotById(resultSet.getInt("timeSlot_id")));
				rendezVous.setSalle(new ImpSalleDAO(conn).getSalleById(resultSet.getInt("salle_id")));
				rendezVous.setScheduler(new ImpSchedulerDAO(conn).getScheduler(resultSet.getInt("scheduler_id")));
				rendezVous.setDocteur(new ImpDocteurDAO(conn).getDocteurById(resultSet.getInt("docteur_id")));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	@Override
	public List<RendezVous> getAllRendezVous() {
		sql = "SELECT * FROM rendez_vous";
		List<RendezVous> rendezVousList = new ArrayList<>();
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RendezVous rendezVous = new RendezVous();
				rendezVous.setId(resultSet.getInt("id"));
				rendezVous.setDateCreation(resultSet.getTimestamp("date_creation"));
				rendezVous.setStatut(resultSet.getString("statut"));
				rendezVous.setDuree(resultSet.getInt("duree"));
				rendezVous.setCommentaire(resultSet.getString("commentaire"));
				rendezVous.setPatient(new ImpPatientDAO(conn).getPatientById(resultSet.getInt("patient_id")));
				rendezVous.setTimeSlot(new ImpTimeSlotDAO(conn).getTimeSlotById(resultSet.getInt("timeSlot_id")));
				rendezVous.setSalle(new ImpSalleDAO(conn).getSalleById(resultSet.getInt("salle_id")));
				rendezVous.setScheduler(new ImpSchedulerDAO(conn).getScheduler(resultSet.getInt("scheduler_id")));
				rendezVous.setDocteur(new ImpDocteurDAO(conn).getDocteurById(resultSet.getInt("docteur_id")));
				
				rendezVousList.add(rendezVous);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return rendezVousList;
	}

	@Override
	public List<RendezVous> getRendezVousByDocteurId(int docteurId) {
		sql = "SELECT * FROM rendez_vous WHERE docteur_id = ?";
		List<RendezVous> rendezVousList = new ArrayList<>();
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, docteurId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RendezVous rendezVous = new RendezVous();
				rendezVous.setId(resultSet.getInt("id"));
				rendezVous.setDateCreation(resultSet.getTimestamp("date_creation"));
				rendezVous.setStatut(resultSet.getString("statut"));
				rendezVous.setDuree(resultSet.getInt("duree"));
				rendezVous.setCommentaire(resultSet.getString("commentaire"));
				rendezVous.setPatient(new ImpPatientDAO(conn).getPatientById(resultSet.getInt("patient_id")));
				rendezVous.setTimeSlot(new ImpTimeSlotDAO(conn).getTimeSlotById(resultSet.getInt("timeSlot_id")));
				rendezVous.setSalle(new ImpSalleDAO(conn).getSalleById(resultSet.getInt("salle_id")));
				rendezVous.setScheduler(new ImpSchedulerDAO(conn).getScheduler(resultSet.getInt("scheduler_id")));
				rendezVous.setDocteur(new ImpDocteurDAO(conn).getDocteurById(resultSet.getInt("docteur_id")));
				
				rendezVousList.add(rendezVous);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return rendezVousList;
	}

	@Override
	public List<RendezVous> getRendezVousByPatientId(int patientId) {
		sql = "SELECT * FROM rendez_vous WHERE patient_id = ?";
		List<RendezVous> rendezVousList = new ArrayList<>();
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, patientId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RendezVous rendezVous = new RendezVous();
				rendezVous.setId(resultSet.getInt("id"));
				rendezVous.setDateCreation(resultSet.getTimestamp("date_creation"));
				rendezVous.setStatut(resultSet.getString("statut"));
				rendezVous.setDuree(resultSet.getInt("duree"));
				rendezVous.setCommentaire(resultSet.getString("commentaire"));
				rendezVous.setPatient(new ImpPatientDAO(conn).getPatientById(resultSet.getInt("patient_id")));
				rendezVous.setTimeSlot(new ImpTimeSlotDAO(conn).getTimeSlotById(resultSet.getInt("time_slot_id")));
				rendezVous.setSalle(new ImpSalleDAO(conn).getSalleById(resultSet.getInt("salle_id")));
				rendezVous.setScheduler(new ImpSchedulerDAO(conn).getScheduler(resultSet.getInt("scheduler_id")));
				rendezVous.setDocteur(new ImpDocteurDAO(conn).getDocteurById(resultSet.getInt("docteur_id")));
				
				rendezVousList.add(rendezVous);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return rendezVousList;
	}

	private void closeResources() {
		try {
			if (preparedStatement != null) preparedStatement.close();
			if (resultSet != null) resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
