package models;

import java.time.LocalDateTime;

public class TimeSlot {
	private enum Statut { disponible, reserve, indisponible };
    private int id;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private Statut statut;

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

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut.equals("disponible") ? Statut.disponible : 
					  statut.equals("reserve") ? Statut.reserve : 
					  Statut.indisponible;
    }

    public int getDuree() {
        return (int) java.time.Duration.between(debut, fin).toMinutes();
    }
}

