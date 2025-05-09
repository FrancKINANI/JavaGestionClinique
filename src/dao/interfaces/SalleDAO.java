package dao.interfaces;

import java.util.List;

import models.Salle;

public interface SalleDAO {
	public void addSalle(Salle salle);
	public void updateSalle(Salle salle);
	public void deleteSalle(int id);
	public Salle getSalleById(int id);
	public List<Salle> getAllSalles();
}
