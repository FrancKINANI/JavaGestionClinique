package models;

import java.util.List;

public class Role {
	private int id;
	private EnumROLE role;
	private List<Utilisateur> utilisateurs;
	
	public Role(int id, EnumROLE role) {
		this.id = id;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public EnumROLE getRole() {
		return role;
	}
	
	public void setRole(EnumROLE role) {
		this.role = role;
	}

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

}
