package models;

import java.security.Timestamp;
import java.time.LocalDateTime;

public class RendezVous {
    private int id;
    private Timestamp  dateCreation;
    private StatutRendezVous statut;
    private String commentaire;
    private int duree;
    private int patientId;
    private int timeSlotId;
    private int salleId;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StatutRendezVous getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut.equals("PLANIFIE") ? StatutRendezVous.PLANIFIE : 
						statut.equals("TERMINE") ? StatutRendezVous.TERMINE : 
						statut.equals("ANNULE") ? StatutRendezVous.ANNULE : 
						statut.equals("REPORTE") ? StatutRendezVous.REPORTE :
						statut.equals("CONFIRME") ? StatutRendezVous.CONFIRME :
						statut.equals("EN_COURS") ? StatutRendezVous.EN_COURS : null;
        
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public boolean annuler() {
        // Logique pour annuler le rendez-vous
        return true;
    }

    public boolean reporter(LocalDateTime nouvelleDate) {
        // Logique pour reporter le rendez-vous
        return true;
    }

    public boolean terminer(String commentaire) {
        // Logique pour terminer le rendez-vous
        return true;
    }

	public int getTimeSlotId() {
		return timeSlotId;
	}

	public void setTimeSlotId(int timeSlotId) {
		this.timeSlotId = timeSlotId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getSalleId() {
		return salleId;
	}

	public void setSalleId(int salleId) {
		this.salleId = salleId;
	}

	public Timestamp getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}
}
