package models;

public class Docteur extends Personne {
    private String specialite;
    private String matricule;

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
    
    @Override
    public String toString() {
		return "Docteur [specialite=" + specialite + ", matricule=" + matricule + "]";
    }
}
