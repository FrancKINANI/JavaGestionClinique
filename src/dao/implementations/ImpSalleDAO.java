package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.SalleDAO;
import models.Salle;

public class ImpSalleDAO implements SalleDAO {
	private Connection conn;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private String sql;
	
	public ImpSalleDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void addSalle(Salle salle) {
		sql = "INSERT INTO salles (numero, etage, capacite) VALUES (?, ?, ?)";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, salle.getNumero());
			preparedStatement.setInt(2, salle.getEtage());
			preparedStatement.setInt(3, salle.getCapacite());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
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

	@Override
	public void updateSalle(Salle salle) {
		sql = "UPDATE salles SET numero = ?, etage = ?, capacite = ? WHERE id = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, salle.getNumero());
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

	@Override
	public void deleteSalle(int id) {
		sql = "DELETE FROM salles WHERE id = ?";
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
	public Salle getSalleById(int id) {
		sql	= "SELECT * FROM salles WHERE id = ?";
		Salle salle = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				salle = new Salle();
				salle.setId(resultSet.getInt("id"));
				salle.setNumero(resultSet.getInt("numero"));
				salle.setEtage(resultSet.getInt("etage"));
				salle.setCapacite(resultSet.getInt("capacite"));
			}
			return salle;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return null;
	}

	@Override
	public List<Salle> getAllSalles() {
		sql = "SELECT * FROM salles";
		List<Salle> salles = new ArrayList<>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
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
}
