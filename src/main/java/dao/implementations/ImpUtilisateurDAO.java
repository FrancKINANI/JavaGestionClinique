package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.Utilisateur;
import dao.interfaces.UtilisateurDAO;
import models.Role;
import models.EnumROLE;
import models.Admin;

public class ImpUtilisateurDAO implements UtilisateurDAO {
	private Connection connection;
	private String sql = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public ImpUtilisateurDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public int getIdByEmail(String email) {
		sql = "SELECT id FROM utilisateurs WHERE email = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return -1; // Return -1 if no user is found with the given email
	}

	@Override
	public String getPasswordByEmail(String email) {
		sql = "SELECT mot_de_passe FROM utilisateurs WHERE email = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("mot_de_passe");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}
	
	@Override
	public Utilisateur getUtilisateurByEmail(String email) {
		String sql = "SELECT * FROM utilisateurs WHERE email = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				// Récupérer le rôle
				ImpRoleDAO roleDAO = new ImpRoleDAO(connection);
				String roleStr = roleDAO.getRoleById(id);
				if (roleStr != null) {
					switch (roleStr) {
						case "PATIENT":
							ImpPatientDAO patientDAO = new ImpPatientDAO(connection);
							return patientDAO.getPatientById(id);
						case "DOCTEUR":
							ImpDocteurDAO docteurDAO = new ImpDocteurDAO(connection);
							return docteurDAO.getDocteurById(id);
						case "SCHEDULER":
							ImpSchedulerDAO schedulerDAO = new ImpSchedulerDAO(connection);
							return schedulerDAO.getSchedulerById(id);
						case "ADMIN":
						case "ADMINISTRATEUR":
							Admin admin = new Admin();
							admin.setId(id);
							admin.setNom(resultSet.getString("nom"));
							admin.setPrenom(resultSet.getString("prenom"));
							admin.setAdresse(resultSet.getString("adresse"));
							admin.setTelephone(resultSet.getString("telephone"));
							admin.setEmail(resultSet.getString("email"));
							admin.setPassword(resultSet.getString("mot_de_passe"));
							return admin;
						default:
							return null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}
	
	@Override
	public java.util.List<Utilisateur> getAllUtilisateurs() {
		java.util.List<Utilisateur> utilisateurs = new java.util.ArrayList<>();
		String sql = "SELECT u.*, r.role FROM utilisateurs u JOIN roles r ON u.id = r.utilisateur_id";
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String roleStr = resultSet.getString("role");
				int id = resultSet.getInt("id");
				Utilisateur user = null;
				switch (roleStr) {
					case "PATIENT":
						ImpPatientDAO patientDAO = new ImpPatientDAO(connection);
						user = patientDAO.getPatientById(id);
						break;
					case "DOCTEUR":
						ImpDocteurDAO docteurDAO = new ImpDocteurDAO(connection);
						user = docteurDAO.getDocteurById(id);
						break;
					case "SCHEDULER":
						ImpSchedulerDAO schedulerDAO = new ImpSchedulerDAO(connection);
						user = schedulerDAO.getSchedulerById(id);
						break;
					case "ADMINISTRATEUR":
						user = new Admin();
						user.setId(id);
						user.setNom(resultSet.getString("nom"));
						user.setPrenom(resultSet.getString("prenom"));
						user.setAdresse(resultSet.getString("adresse"));
						user.setTelephone(resultSet.getString("telephone"));
						user.setEmail(resultSet.getString("email"));
						user.setPassword(resultSet.getString("mot_de_passe"));
						break;
					default:
						continue;
				}
				if (user != null) {
					user.setRole(new Role(id, EnumROLE.fromString(roleStr)));
					utilisateurs.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return utilisateurs;
	}
	
	private void closeResources() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
