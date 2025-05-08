package models;

import java.util.List;

public class Patient extends Utilisateur {
    private String numeroSecu;
    private String dossierMedical;
    private List<RendezVous> rendezVousList;

    // Getters et Setters
    public String getNumeroSecu() {
        return numeroSecu;
    }

    public void setNumeroSecu(String numeroSecu) {
        this.numeroSecu = numeroSecu;
    }

    public String getDossierMedical() {
        return dossierMedical;
    }

    public void setDossierMedical(String dossierMedical) {
        this.dossierMedical = dossierMedical;
    }
 
    
    public List<RendezVous> getRendezVousList() {
		return rendezVousList;
	}

	public void setRendezVousList(List<RendezVous> rendezVousList) {
		this.rendezVousList = rendezVousList;
	}

	@Override
	public String toString() {
		return "Patient{" +
				"numeroSecu='" + numeroSecu + '\'' +
				", dossierMedical='" + dossierMedical + '\'' +
				", rendezVousList=" + rendezVousList +
				"} " + super.toString();
	}
}
