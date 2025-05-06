package models;
public class Patient extends Personne {
	public enum SexePatient { HOMME, FEMME, AUTRE };
    private String numeroSecu;
    private SexePatient sexe;
    private String dossierMedical;

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
    
    public SexePatient getSexe() {
		return sexe;
	}

    public void setSexe(SexePatient sexe) {
    	this.sexe = sexe;
    }
    	
    public String toString() {
		return "Patient [numeroSecu=" + numeroSecu + ", sexe=" + sexe + ", dossierMedical=" + dossierMedical + "]";
	}    
    
}
