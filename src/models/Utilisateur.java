package models;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Utilisateur {	
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private LocalDateTime dateNaissance;
    private Role role;
    private String password;
    private LocalDateTime derniereConnexion;
    private List<RendezVous> rendezVousList;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateTime dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDerniereConnexion() {
		return derniereConnexion;
	}

	public void setDerniereConnexion(LocalDateTime derniereConnexion) {
		this.derniereConnexion = derniereConnexion;
	}

	public List<RendezVous> getRendezVousList() {
		return rendezVousList;
	}

	public void setRendezVousList(List<RendezVous> rendezVousList) {
		this.rendezVousList = rendezVousList;
	}
}

