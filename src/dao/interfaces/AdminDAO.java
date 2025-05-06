package dao.interfaces;

public interface AdminDAO {
	//Gestion des salles
	public void addSalle(int id, String nom, int capacite);
	public void deleteSalle(int id);
	public void updateSalle(int id, String nom, int capacite);
	public void getSalle(int id);
	public void getAllSalles();
	
	//Gestion des rapports

}
