package identification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {
    private Connection connection;
    
    public AuthenticationService(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Vérifie si le mot de passe fourni correspond à celui stocké en base
     * @param email Email de l'utilisateur
     * @param plainPassword Mot de passe en clair saisi par l'utilisateur
     * @return true si le mot de passe est valide, false sinon
     */
    public boolean authenticate(String email, String plainPassword) {
        String sql = "SELECT mot_de_passe FROM utilisateurs WHERE email = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("mot_de_passe");
                String inputHash = PasswordUtils.hashPassword(plainPassword);
                System.out.println("Mot de passe stocké : " + storedHash);
                System.out.println("Mot de passe saisi : " + inputHash);
                return storedHash.equalsIgnoreCase(inputHash);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur d'authentification", e);
        }
    }
}