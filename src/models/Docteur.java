package models;

import java.util.List;

public class Docteur extends Personne {
    private String specialite;
    private String matricule;
    private List<RendezVous> rendezVousList;

    // Getters et Setters
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
   
	public List<RendezVous> getRendezVousList() {
		return rendezVousList;
	}

	public void setRendezVousList(List<RendezVous> rendezVousList) {
		this.rendezVousList = rendezVousList;
	}
}
