package dao.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import models.Docteur;
import models.RendezVous;
import models.TimeSlot;

public interface DocteurDAO {
	// Méthodes CRUD pour la gestion des docteurs
	public void addDocteur(Docteur docteur);
	public void updateDocteur(Docteur docteur);
	public void deleteDocteur(int id);
	public Docteur getDocteurById(int id);
	public List<Docteur> getAllDocteurs();
	
	// Méthodes spécifiques à la gestion des docteurs
	public List<Docteur> getDocteursBySpecialite(String specialite);
	public List<Docteur> getDocteursByNom(String nom);
	public List<Docteur> getDocteursByMatricule(String matricule);
	
	// Méthodes pour gérer les disponibilités et les commentaires
	 public List<TimeSlot> definirDisponibilites(LocalDateTime date, int duree);
	 public void ajouterCommentaire(RendezVous rdv, String commentaire);
	
	// Méthodes pour gérer les rendez-vous
	 public List<RendezVous> getRendezVousByDocteur(int id);
	

}
