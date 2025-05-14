package models;

import java.util.List;

public class Scheduler extends Utilisateur {
    private String fonction;
    private List<RendezVous> rendezVousList;

    // Getters et Setters
    public String getFonction() {
        return fonction;
    }
    
    public List<RendezVous> getRendezVousList() {
    		return rendezVousList;
    }
    
    public void setRendezVousList(List<RendezVous> rendezVousList) {
			this.rendezVousList = rendezVousList;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	@Override
	public String toString() {
		return "Scheduler{" +
				"fonction='" + fonction + '\'' +
				", rendezVousList=" + rendezVousList +
				"} " + super.toString();
	}
}
