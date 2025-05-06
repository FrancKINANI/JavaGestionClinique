package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.PatientDAO;
import models.Patient;

public class ImpPatientDAO implements PatientDAO {
	// Implémentation des méthodes de l'interface PatientDAO
	private static final String tableName = "patients";
	private static final String superTableName = "personnes";
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String sql = null;
	
	
	@Override
	public void addPatient(Patient patient) {
		sql = "INSERT INTO " + superTableName + " (nom, prenom, adresse, telephone, email, date_naissance, type_personne, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, patient.getNom());
            preparedStatement.setString(2, patient.getPrenom());
            preparedStatement.setString(3, patient.getAdresse());
            preparedStatement.setString(4, patient.getTelephone());
            preparedStatement.setString(5, patient.getEmail());
            preparedStatement.setObject(6, patient.getDateNaissance());
            preparedStatement.setString(7, "PATIENT");
            preparedStatement.setString(8, patient.getPassword());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
            	resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int generatedId = resultSet.getInt(1);
					sql = "INSERT INTO " + tableName + " (id, numero_secu, sexe, dossier_medical) VALUES (?, ?, ?, ?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, generatedId);
					preparedStatement.setString(2, patient.getNumeroSecu());
					preparedStatement.setString(3, patient.getSexe().toString());
					preparedStatement.setString(4, patient.getDossierMedical());
					preparedStatement.executeUpdate();
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
	}

	@Override
	public void updatePatient(Patient patient) {
		sql = "UPDATE " + superTableName + " SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ?, date_naissance = ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, patient.getNom());
			preparedStatement.setString(2, patient.getPrenom());
			preparedStatement.setString(3, patient.getAdresse());
			preparedStatement.setString(4, patient.getTelephone());
			preparedStatement.setString(5, patient.getEmail());
			preparedStatement.setObject(6, patient.getDateNaissance());
			preparedStatement.setInt(7, patient.getId());

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows > 0) {
				sql = "UPDATE " + tableName + " SET numero_secu = ?, sexe = ?, dossier_medical = ? WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, patient.getNumeroSecu());
				preparedStatement.setString(2, patient.getSexe().toString());
				preparedStatement.setString(3, patient.getDossierMedical());
				preparedStatement.setInt(4, patient.getId());
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
	}

	@Override
	public void deletePatient(int id) {
		sql = "DELETE FROM " + tableName + " WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
	}

	@Override
	public Patient getPatientById(int id) {
		sql = "SELECT * FROM " + superTableName + " WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Patient patient = new Patient();
				patient.setId(resultSet.getInt("id"));
				patient.setNom(resultSet.getString("nom"));
				patient.setPrenom(resultSet.getString("prenom"));
				patient.setAdresse(resultSet.getString("adresse"));
				patient.setTelephone(resultSet.getString("telephone"));
				patient.setEmail(resultSet.getString("email"));
				patient.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				patient.setPassword(resultSet.getString("mot_de_passe"));

				sql = "SELECT * FROM " + tableName + " WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					patient.setNumeroSecu(resultSet.getString("numero_secu"));
					patient.setSexe(Patient.SexePatient.valueOf(resultSet.getString("sexe")));
					patient.setDossierMedical(resultSet.getString("dossier_medical"));
				}
				return patient;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public List<Patient> getAllPatients() {
		sql = "SELECT * FROM " + superTableName;
		List<Patient> patients = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Patient patient = new Patient();
				patient.setId(resultSet.getInt("id"));
				patient.setNom(resultSet.getString("nom"));
				patient.setPrenom(resultSet.getString("prenom"));
				patient.setAdresse(resultSet.getString("adresse"));
				patient.setTelephone(resultSet.getString("telephone"));
				patient.setEmail(resultSet.getString("email"));
				patient.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				patient.setPassword(resultSet.getString("mot_de_passe"));

				sql = "SELECT * FROM " + tableName + " WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, patient.getId());
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					patient.setNumeroSecu(rs.getString("numero_secu"));
					patient.setSexe(Patient.SexePatient.valueOf(rs.getString("sexe")));
					patient.setDossierMedical(rs.getString("dossier_medical"));
					patients.add(patient);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Patient> getPatientsByCriteria(String criteria) {
		sql = "SELECT * FROM " + superTableName + " WHERE nom LIKE ? OR prenom LIKE ?";
		List<Patient> patients = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "%" + criteria + "%");
			preparedStatement.setString(2, "%" + criteria + "%");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Patient patient = new Patient();
				patient.setId(resultSet.getInt("id"));
				patient.setNom(resultSet.getString("nom"));
				patient.setPrenom(resultSet.getString("prenom"));
				patient.setAdresse(resultSet.getString("adresse"));
				patient.setTelephone(resultSet.getString("telephone"));
				patient.setEmail(resultSet.getString("email"));
				patient.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				patient.setPassword(resultSet.getString("mot_de_passe"));

				sql = "SELECT * FROM " + tableName + " WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, patient.getId());
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					patient.setNumeroSecu(rs.getString("numero_secu"));
					patient.setSexe(Patient.SexePatient.valueOf(rs.getString("sexe")));
					patient.setDossierMedical(rs.getString("dossier_medical"));
					patients.add(patient);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
		return patients;
	}

	@Override
	public List<Patient> getPatientsByDate(LocalDate date) {
		sql = "SELECT * FROM " + superTableName + " WHERE date_naissance = ?";
		List<Patient> patients = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, date);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Patient patient = new Patient();
				patient.setId(resultSet.getInt("id"));
				patient.setNom(resultSet.getString("nom"));
				patient.setPrenom(resultSet.getString("prenom"));
				patient.setAdresse(resultSet.getString("adresse"));
				patient.setTelephone(resultSet.getString("telephone"));
				patient.setEmail(resultSet.getString("email"));
				patient.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				patient.setPassword(resultSet.getString("mot_de_passe"));

				sql = "SELECT * FROM " + tableName + " WHERE id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, patient.getId());
				ResultSet rs = preparedStatement.executeQuery();
				if (rs.next()) {
					patient.setNumeroSecu(rs.getString("numero_secu"));
					patient.setSexe(Patient.SexePatient.valueOf(rs.getString("sexe")));
					patient.setDossierMedical(rs.getString("dossier_medical"));
					patients.add(patient);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public boolean patientExists(int id) {
		sql = "SELECT COUNT(*) FROM " + superTableName + " WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(preparedStatement, resultSet);
		}
		return false;
	}
	
	private void closeResources(PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
