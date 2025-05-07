package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.AdminDAO;
import models.Salle;

public class ImpAdminDAO implements AdminDAO {
	private Connection connection;
	private String tableName = "salles";
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Statement statement;
	private String sql;
	
	//Gestion des salles
	public void addSalle(Salle salle) {
		sql = "INSERT INTO " + tableName + " (numero, etage, capacite) VALUES (?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, salle.getNumero());
			preparedStatement.setInt(2, salle.getEtage());
			preparedStatement.setInt(3, salle.getCapacite());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	public void deleteSalle(int id) {
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

	public void updateSalle(Salle salle) {
		sql = "UPDATE " + tableName + " SET numero = ?, etage = ?, capacite = ? WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, salle.getNumero());
			preparedStatement.setInt(2, salle.getEtage());
			preparedStatement.setInt(3, salle.getCapacite());
			preparedStatement.setInt(4, salle.getId());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	public Salle getSalle(int id) {
		sql = "SELECT * FROM " + tableName + " WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Salle salle = new Salle();
				salle.setId(resultSet.getInt("id"));
				salle.setNumero(resultSet.getInt("numero"));
				salle.setEtage(resultSet.getInt("etage"));
				salle.setCapacite(resultSet.getInt("capacite"));
				return salle;
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

	public List<Salle> getAllSalles() {
		sql = "SELECT * FROM " + tableName;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			List<Salle> salles = new ArrayList<>();
			while (resultSet.next()) {
				Salle salle = new Salle();
				salle.setId(resultSet.getInt("id"));
				salle.setNumero(resultSet.getInt("numero"));
				salle.setEtage(resultSet.getInt("etage"));
				salle.setCapacite(resultSet.getInt("capacite"));
				salles.add(salle);
			}
			return salles;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
		
	}

	public void generateRapport() {
		// TODO Auto-generated method stub
		
	}
	
	private void closeResources() {
		try {
			if (preparedStatement != null) preparedStatement.close();
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
