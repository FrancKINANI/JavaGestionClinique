package dao.interfaces;

import java.util.List;

import models.RendezVous;

public interface RendezVousDAO {
	public void addRendezVous(RendezVous rendezVous);
	public void updateRendezVous(RendezVous rendezVous);
	public void deleteRendezVous(int id);
	public RendezVous getRendezVousById(int id);
	public List<RendezVous> getAllRendezVous();
	public List<RendezVous> getRendezVousByPatientId(int patientId);
	public List<RendezVous> getRendezVousByDocteurId(int docteurId);
	
}
