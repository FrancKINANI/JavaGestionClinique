package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import identification.AuthenticationService;
import sql.config.DatabaseConfig;

public class Main {
	
    public static void main(String[] args) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            AuthenticationService authService = new AuthenticationService(conn);
            
            try (
			Scanner scanner = new Scanner(System.in)) {
				System.out.print("Email: ");
				String email = scanner.nextLine();
				System.out.print("Mot de passe: ");
				String passwordInput = scanner.nextLine();
				
				if (authService.authenticate(email, passwordInput)) {
				    System.out.println("Authentification réussie !");
				} else {
				    System.out.println("Email ou mot de passe incorrect.");
				}
			}
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}