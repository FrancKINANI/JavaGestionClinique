package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.TimeSlotDAO;
import models.TimeSlot;

public class ImpTimeSlotDAO implements TimeSlotDAO {
	private Connection conn;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private String sql;

	public ImpTimeSlotDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void addTimeSlot(TimeSlot timeSlot) {
		sql = "INSERT INTO time_slots (debut, fin, statut) VALUES (?, ?, ?)";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setObject(1, timeSlot.getDebut());
			preparedStatement.setObject(2, timeSlot.getFin());
			preparedStatement.setString(3, timeSlot.getStatut().toString());

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public void updateTimeSlot(TimeSlot timeSlot) {
		sql = "UPDATE time_slots SET debut = ?, fin = ?, statut = ? WHERE id = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setObject(1, timeSlot.getDebut());
			preparedStatement.setObject(2, timeSlot.getFin());
			preparedStatement.setString(3, timeSlot.getStatut().toString());
			preparedStatement.setInt(4, timeSlot.getId());

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public void deleteTimeSlot(int id) {
		sql = "DELETE FROM time_slots WHERE id = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public TimeSlot getTimeSlotById(int id) {
		sql = "SELECT * FROM time_slots WHERE id = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setId(resultSet.getInt("id"));
				timeSlot.setDebut(resultSet.getObject("debut", LocalDateTime.class));
				timeSlot.setFin(resultSet.getObject("fin", LocalDateTime.class));
				timeSlot.setStatut(resultSet.getString("statut"));

				return timeSlot;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	@Override
	public List<TimeSlot> getAllTimeSlots() {
		sql = "SELECT * FROM time_slots";
		List<TimeSlot> timeSlots = new ArrayList<>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setId(resultSet.getInt("id"));
				timeSlot.setDebut(resultSet.getObject("debut", LocalDateTime.class));
				timeSlot.setFin(resultSet.getObject("fin", LocalDateTime.class));
				timeSlot.setStatut(resultSet.getString("statut"));

				timeSlots.add(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return timeSlots;
	}

	@Override
	public List<TimeSlot> getAvailableTimeSlots() {
		sql = "SELECT * FROM time_slots WHERE statut = 'DISPONIBLE'";
		List<TimeSlot> timeSlots = new ArrayList<>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setId(resultSet.getInt("id"));
				timeSlot.setDebut(resultSet.getObject("debut", LocalDateTime.class));
				timeSlot.setFin(resultSet.getObject("fin", LocalDateTime.class));
				timeSlot.setStatut(resultSet.getString("statut"));

				timeSlots.add(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return timeSlots;
	}

	@Override
	public List<TimeSlot> getAvailableTimeSlotsForDate(LocalDateTime date) {
		sql = "SELECT * FROM time_slots WHERE statut = 'DISPONIBLE' AND DATE(debut) = DATE(?)";
		List<TimeSlot> timeSlots = new ArrayList<>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setObject(1, date);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setId(resultSet.getInt("id"));
				timeSlot.setDebut(resultSet.getObject("debut", LocalDateTime.class));
				timeSlot.setFin(resultSet.getObject("fin", LocalDateTime.class));
				timeSlot.setStatut(resultSet.getString("statut"));

				timeSlots.add(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return timeSlots;
	}
	
	private void closeResources() {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
