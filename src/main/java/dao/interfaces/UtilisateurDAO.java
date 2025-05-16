package dao.interfaces;

import models.Utilisateur;

public interface UtilisateurDAO {
	/**
	 * This method is used to get the email of a user by their ID.
	 * 
	 * @param id The ID of the user whose email is to be fetched.
	 * @return The email of the user as a String.
	 */
	public int getIdByEmail(String email);

	/**
	 * This method is used to get the password of a user by their ID.
	 * 
	 * @param id The ID of the user whose password is to be fetched.
	 * @return The password of the user as a String.
	 */
	public String getPasswordByEmail(String email);

	/**
	 * Récupère un utilisateur complet par son email.
	 * @param email Email de l'utilisateur
	 * @return L'objet Utilisateur correspondant, ou null si non trouvé
	 */
	public Utilisateur getUtilisateurByEmail(String email);

	/**
	 * Récupère la liste de tous les utilisateurs.
	 * @return Liste des utilisateurs
	 */
	public java.util.List<Utilisateur> getAllUtilisateurs();
}
