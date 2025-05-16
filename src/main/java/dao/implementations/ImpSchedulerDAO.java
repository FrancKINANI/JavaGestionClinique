package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.SchedulerDAO;
import identification.PasswordUtils;
import models.Scheduler;

public class ImpSchedulerDAO implements SchedulerDAO {
	private Connection connection;
	private static final String tableName = "schedulers";
	private static final String superTableName = "utilisateurs";
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Statement statement;
	private String sql;
	
	public ImpSchedulerDAO(Connection conn) {
		this.connection = conn;
	}

	@Override
	public void addScheduler(Scheduler scheduler) {
		sql = "INSERT INTO " + superTableName + " (nom, prenom, adresse, telephone, email, date_naissance, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, scheduler.getNom());
			preparedStatement.setString(2, scheduler.getPrenom());
			preparedStatement.setString(3, scheduler.getAdresse());
			preparedStatement.setString(4, scheduler.getTelephone());
			preparedStatement.setString(5, scheduler.getEmail());
			preparedStatement.setObject(6, scheduler.getDateNaissance());
			preparedStatement.setString(8, PasswordUtils.hashPassword(scheduler.getPassword()));

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows > 0) {
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int generatedId = resultSet.getInt(1);
					sql = "INSERT INTO " + tableName + " (utilisateur_id, fonction) VALUES (?, ?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, generatedId);
					preparedStatement.setString(2, scheduler.getFonction());
					preparedStatement.executeUpdate();
					
					// Insert into the role table
					sql = "INSERT INTO roles (utilisateur_id, role) VALUES (?, ?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, generatedId);
					preparedStatement.setString(2, "SCHEDULER");
					preparedStatement.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public void deleteScheduler(int id) {
		sql = "DELETE FROM " + tableName + " WHERE utilisateur_id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public void updateScheduler(Scheduler scheduler) {
		sql = "UPDATE " + superTableName + " SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ?, date_naissance = ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, scheduler.getNom());
			preparedStatement.setString(2, scheduler.getPrenom());
			preparedStatement.setString(3, scheduler.getAdresse());
			preparedStatement.setString(4, scheduler.getTelephone());
			preparedStatement.setString(5, scheduler.getEmail());
			preparedStatement.setObject(6, scheduler.getDateNaissance());
			preparedStatement.setInt(7, scheduler.getId());

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows > 0) {
				sql = "UPDATE " + tableName + " SET fonction = ? WHERE utilisateur_id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, scheduler.getId());
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}

	@Override
	public Scheduler getScheduler(int id) {
		sql = "SELECT * FROM " + superTableName + " WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Scheduler scheduler = new Scheduler();
				scheduler.setId(resultSet.getInt("id"));
				scheduler.setNom(resultSet.getString("nom"));
				scheduler.setPrenom(resultSet.getString("prenom"));
				scheduler.setAdresse(resultSet.getString("adresse"));
				scheduler.setTelephone(resultSet.getString("telephone"));
				scheduler.setEmail(resultSet.getString("email"));
				scheduler.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				
				sql = "SELECT * FROM " + tableName + " WHERE utilisateur_id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					scheduler.setFonction(resultSet.getString("fonction"));
				}
				return scheduler;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	@Override
	public List<Scheduler> getAllSchedulers() {
		sql = "SELECT * FROM " + superTableName;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			List<Scheduler> schedulers = new ArrayList<>();
			while (resultSet.next()) {
				Scheduler scheduler = new Scheduler();
				scheduler.setId(resultSet.getInt("id"));
				scheduler.setNom(resultSet.getString("nom"));
				scheduler.setPrenom(resultSet.getString("prenom"));
				scheduler.setAdresse(resultSet.getString("adresse"));
				scheduler.setTelephone(resultSet.getString("telephone"));
				scheduler.setEmail(resultSet.getString("email"));
				scheduler.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				
				sql = "SELECT * FROM " + tableName + " WHERE utilisateur_id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, scheduler.getId());
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					scheduler.setFonction(resultSet.getString("fonction"));
				}
				
				schedulers.add(scheduler);
			}
			return schedulers;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}
	
	private void closeResources() {
		try {
			if (preparedStatement != null) preparedStatement.close();
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
    @Override
    public Scheduler getSchedulerById(int id) {
		sql = "SELECT * FROM " + superTableName + " WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Scheduler scheduler = new Scheduler();
				scheduler.setId(resultSet.getInt("id"));
				scheduler.setNom(resultSet.getString("nom"));
				scheduler.setPrenom(resultSet.getString("prenom"));
				scheduler.setAdresse(resultSet.getString("adresse"));
				scheduler.setTelephone(resultSet.getString("telephone"));
				scheduler.setEmail(resultSet.getString("email"));
				scheduler.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
                                return scheduler;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
                return null;
    }

=======
>>>>>>> 682628f10b4231d1259ea5f51b647b5f995d5cc3
}
