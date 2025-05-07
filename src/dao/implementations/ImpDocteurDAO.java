package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.DocteurDAO;
import models.Docteur;
import identification.PasswordUtils;

class ImpDocteurDao implements DocteurDAO {
    private Connection connection;
    private String tableName = "docteurs";
    private String superTableName = "utilisateurs";
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sql;

    public ImpDocteurDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addDocteur(Docteur docteur) {
        sql = "INSERT INTO " + superTableName + " (nom, prenom, adresse, telephone, email, date_naissance, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, docteur.getNom());
            preparedStatement.setString(2, docteur.getPrenom());
            preparedStatement.setString(3, docteur.getAdresse());
            preparedStatement.setString(4, docteur.getTelephone());
            preparedStatement.setString(5, docteur.getEmail());
            preparedStatement.setObject(6, docteur.getDateNaissance());
            preparedStatement.setString(8, PasswordUtils.hashPassword(docteur.getPassword()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    sql = "INSERT INTO " + tableName + " (utilisateur_id, specialite, matricule) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, docteur.getSpecialite());
                    preparedStatement.setString(3, docteur.getMatricule());
                    preparedStatement.executeUpdate();
                    
                    // Insert into the role table
                    sql = "INSERT INTO roles (utilisateur_id, role) VALUES (?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, "DOCTEUR");
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
    public void updateDocteur(Docteur docteur) {
        sql = "UPDATE " + superTableName + " SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ?, date_naissance = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, docteur.getNom());
            preparedStatement.setString(2, docteur.getPrenom());
            preparedStatement.setString(3, docteur.getAdresse());
            preparedStatement.setString(4, docteur.getTelephone());
            preparedStatement.setString(5, docteur.getEmail());
            preparedStatement.setObject(6, docteur.getDateNaissance());
            preparedStatement.setInt(7, docteur.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    @Override
    public void deleteDocteur(int id) {
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
    public Docteur getDocteurById(int id) {
        Docteur docteur = null;
        sql = "SELECT * FROM " + superTableName + " WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                docteur = new Docteur();
                docteur.setId(resultSet.getInt("id"));
                docteur.setNom(resultSet.getString("nom"));
                docteur.setPrenom(resultSet.getString("prenom"));
                docteur.setAdresse(resultSet.getString("adresse"));
                docteur.setTelephone(resultSet.getString("telephone"));
                docteur.setEmail(resultSet.getString("email"));
                docteur.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
                // Récupérer les détails du docteur
                sql = "SELECT * FROM " + tableName + " WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet docteurResultSet = preparedStatement.executeQuery();
                if (docteurResultSet.next()) {
                    docteur.setSpecialite(docteurResultSet.getString("specialite"));
                    docteur.setMatricule(docteurResultSet.getString("matricule"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return docteur;
    }

    @Override
    public List<Docteur> getAllDocteurs() {
        List<Docteur> docteurs = new ArrayList<>();
        sql = "SELECT * FROM " + superTableName + " join " + tableName + " ON " + superTableName + ".id = " + tableName + ".utilisateur_id";
       try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Docteur docteur = new Docteur();
				docteur.setId(resultSet.getInt("id"));
				docteur.setNom(resultSet.getString("nom"));
				docteur.setPrenom(resultSet.getString("prenom"));
				docteur.setAdresse(resultSet.getString("adresse"));
				docteur.setTelephone(resultSet.getString("telephone"));
				docteur.setEmail(resultSet.getString("email"));
				docteur.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				docteur.setSpecialite(resultSet.getString("specialite"));
				docteur.setMatricule(resultSet.getString("matricule"));
				
				docteurs.add(docteur);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return docteurs;
    }
    
    @Override
    public List<Docteur> getDocteursBySpecialite(String specialite) {
        List<Docteur> docteurs = new ArrayList<>();
        sql = "SELECT * FROM " + superTableName + " join " + tableName + " ON " + superTableName + ".id = " + tableName + ".utilisateur_id WHERE" + tableName + ".specialite = ?";
        try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, specialite);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Docteur docteur = new Docteur();
				docteur.setId(resultSet.getInt("id"));
				docteur.setNom(resultSet.getString("nom"));
				docteur.setPrenom(resultSet.getString("prenom"));
				docteur.setAdresse(resultSet.getString("adresse"));
				docteur.setTelephone(resultSet.getString("telephone"));
				docteur.setEmail(resultSet.getString("email"));
				docteur.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
				docteur.setSpecialite(resultSet.getString("specialite"));
				docteur.setMatricule(resultSet.getString("matricule"));
				
				docteurs.add(docteur);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return docteurs;
    }

    @Override
    public List<Docteur> getDocteursByMatricule(String matricule) {
        List<Docteur> docteurs = new ArrayList<>();
        sql = "SELECT * FROM " + superTableName + " join " + tableName + " ON " + superTableName + ".id = " + tableName + ".utilisateur_id WHERE" + tableName + ".matricule = ?";
        try {
        		preparedStatement = connection.prepareStatement(sql);
        		preparedStatement.setString(1, matricule);
        		resultSet = preparedStatement.executeQuery();
        		while (resultSet.next()) {
					Docteur docteur = new Docteur();
					docteur.setId(resultSet.getInt("id"));
					docteur.setNom(resultSet.getString("nom"));
					docteur.setPrenom(resultSet.getString("prenom"));
					docteur.setAdresse(resultSet.getString("adresse"));
					docteur.setTelephone(resultSet.getString("telephone"));
					docteur.setEmail(resultSet.getString("email"));
					docteur.setDateNaissance(resultSet.getObject("date_naissance", LocalDateTime.class));
					docteur.setSpecialite(resultSet.getString("specialite"));
					docteur.setMatricule(resultSet.getString("matricule"));
						
					docteurs.add(docteur);
        							}
    	} catch (Exception e) {
				e.printStackTrace();
		} finally {
			closeResources();
		}
		return docteurs;
    }

    private void closeResources() {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
