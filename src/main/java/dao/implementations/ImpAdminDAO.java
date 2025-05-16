package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.AdminDAO;
import models.Admin;

public class ImpAdminDAO implements AdminDAO {
	private Connection connection;
	private String tableName = "utilisateurs";
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Statement statement;
	private String sql;
	
	
	private void closeResources() {
		try {
			if (preparedStatement != null) preparedStatement.close();
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void addAdmin(Admin admin) {
		sql = "INSERT INTO " + tableName + " (nom, prenom, adresse, telephone, email, date_naissance, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, admin.getNom());
			preparedStatement.setString(2, admin.getPrenom());
			preparedStatement.setString(3, admin.getAdresse());
			preparedStatement.setString(4, admin.getTelephone());
			preparedStatement.setString(5, admin.getEmail());
			preparedStatement.setObject(6, admin.getDateNaissance());
			preparedStatement.setString(7, admin.getPassword());

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows > 0) {
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int generatedId = resultSet.getInt(1);
					// Insert into the role table
					sql = "INSERT INTO roles (utilisateur_id, role) VALUES (?, ?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, generatedId);
					preparedStatement.setString(2, "ADMIN");
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
	public void deleteAdmin(int id) {
		sql = "DELETE FROM " + tableName + " WHERE id = ?";
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
	public void updateAdmin(Admin admin) {
		sql = "UPDATE " + tableName + " SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ?, date_naissance = ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, admin.getNom());
			preparedStatement.setString(2, admin.getPrenom());
			preparedStatement.setString(3, admin.getAdresse());
			preparedStatement.setString(4, admin.getTelephone());
			preparedStatement.setString(5, admin.getEmail());
			preparedStatement.setObject(6, admin.getDateNaissance());
			preparedStatement.setInt(7, admin.getId());

			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
	}


	@Override
	public List<Admin> getAllAdmins() {
		sql = "SELECT * FROM " + tableName + "join roles on utilisateurs.id = roles.utilisateur_id where roles.role = 'ADMIN'";
		List<Admin> admins = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Admin admin = new Admin();
				admin.setId(resultSet.getInt("id"));
				admin.setNom(resultSet.getString("nom"));
				admin.setPrenom(resultSet.getString("prenom"));
				admin.setAdresse(resultSet.getString("adresse"));
				admin.setTelephone(resultSet.getString("telephone"));
				admin.setEmail(resultSet.getString("email"));
				admin.setDateNaissance(resultSet.getObject("date_naissance", java.time.LocalDateTime.class));
				admin.setPassword(resultSet.getString("mot_de_passe"));
				admins.add(admin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}		
		return null;
	}


	@Override
	public Admin getAdminById(int id) {
		sql = "SELECT * FROM " + tableName + " WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Admin admin = new Admin();
				admin.setId(resultSet.getInt("id"));
				admin.setNom(resultSet.getString("nom"));
				admin.setPrenom(resultSet.getString("prenom"));
				admin.setAdresse(resultSet.getString("adresse"));
				admin.setTelephone(resultSet.getString("telephone"));
				admin.setEmail(resultSet.getString("email"));
				admin.setDateNaissance(resultSet.getObject("date_naissance", java.time.LocalDateTime.class));
				admin.setPassword(resultSet.getString("mot_de_passe"));
				return admin;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}
}
