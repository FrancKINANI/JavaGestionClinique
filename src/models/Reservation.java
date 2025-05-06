package models;
import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private LocalDateTime dateCreation;
    private String statut;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public boolean annuler() {
        // Logique pour annuler la réservation
        return true;
    }

    public boolean confirmer() {
        // Logique pour confirmer la réservation
        return true;
    }
}

