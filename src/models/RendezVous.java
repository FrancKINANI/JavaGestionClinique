package models;

import java.time.LocalDateTime;

public class RendezVous {
    private int id;
    private LocalDateTime date;
    private StatutRendezVous statut;
    private String commentaire;
    private int duree;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
}
