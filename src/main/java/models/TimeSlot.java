package models;

import java.time.LocalDateTime;

public class TimeSlot {
    private int id;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private StatutTimeSlot statut;

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

    public StatutTimeSlot getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut.equals("disponible") ? StatutTimeSlot.DISPONIBLE : 
					  statut.equals("reserve") ? StatutTimeSlot.RESERVE : 
						  StatutTimeSlot.INDISPONIBLE;
    }

    public int getDuree() {
        return (int) java.time.Duration.between(debut, fin).toMinutes();
    }
    
    public int getHoraire() {
		return debut.getHour();
	}

    @Override
    public String toString() {
        String date = debut.toLocalDate().toString();
        String heure = debut.toLocalTime() + " - " + fin.toLocalTime();
        String statutStr = (statut != null) ? " (" + statut.toString() + ")" : "";
        return date + " | " + heure + statutStr;
    }
}

