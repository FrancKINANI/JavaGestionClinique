package models;

import java.time.LocalDateTime;

public class TimeSlot {
    private int id;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private String statut;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public void setDebut(LocalDateTime debut) {
        this.debut = debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getDuree() {
        return (int) java.time.Duration.between(debut, fin).toMinutes();
    }

    public boolean estDisponible() {
        return true && statut.equals("disponible");
    }

    public boolean reserver() {
        // Logique pour réserver le créneau
        return true && statut.equals("disponible");
    }

    public boolean liberer() {
        // Logique pour libérer le créneau
        return true && statut.equals("réservé");
    }
}

