package dao.interfaces;

import java.util.List;

import models.Docteur;

public interface DocteurDAO {
	// Méthodes CRUD pour la gestion des docteurs
	public void addDocteur(Docteur docteur);
	public void updateDocteur(Docteur docteur);
	public void deleteDocteur(int id);
	public Docteur getDocteurById(int id);
	public List<Docteur> getAllDocteurs();
	
	// Méthodes spécifiques à la gestion des docteurs
	public List<Docteur> getDocteursBySpecialite(String specialite);
	public List<Docteur> getDocteursByMatricule(String matricule);
}
