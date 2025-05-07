package models;
public class Patient extends Utilisateur {
    private String numeroSecu;
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
 
    	
    public String toString() {
		return "Patient [numeroSecu=" + numeroSecu + ", dossierMedical=" + dossierMedical + "]";
	}    
    
}
