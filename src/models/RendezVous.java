package models;

import java.security.Timestamp;

public class RendezVous {
    private int id;
    private Timestamp  dateCreation;
    private StatutRendezVous statut;
    private int duree;
    private String commentaire;
    private Scheduler scheduler;
    private Patient patient;
    private TimeSlot timeSlot;
    private Salle salle;
    private Docteur docteur;

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


	public Timestamp getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Docteur getDocteur() {
		return docteur;
	}

	public void setDocteur(Docteur docteur) {
		this.docteur = docteur;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}
