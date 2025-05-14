package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.interfaces.UtilisateurDAO;

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
