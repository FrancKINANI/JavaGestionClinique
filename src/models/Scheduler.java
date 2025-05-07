package models;

import java.util.ArrayList;
import java.util.List;

public class Scheduler extends Utilisateur {
    private String fonction;

    // Getters et Setters
    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public boolean modifierRendezVous(RendezVous rdv, TimeSlot nouveauCreneau) {
        // Logique pour modifier un rendez-vous
        return true;
    }

    public boolean annulerRendezVous(RendezVous rdv) {
        // Logique pour annuler un rendez-vous
        return true;
    }

    public List<RendezVous> consulterPlanning() {
        // Logique pour consulter le planning
        return new ArrayList<>();
    }
}
