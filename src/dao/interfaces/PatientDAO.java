package dao.interfaces;

import java.time.LocalDate;
import java.util.List;

import models.Patient;

public interface PatientDAO {
	// Méthodes CRUD pour la gestion des patients
	public void addPatient(Patient patient);
	public void updatePatient(Patient patient);
	public void deletePatient(int id);
	public Patient getPatientById(int id);
	public List<Patient> getAllPatients();
	
	// Méthodes spécifiques à la gestion des patients
	public List<Patient> getPatientsByCriteria(String criteria);
	public List<Patient> getPatientsByDate(LocalDate date);
	
	// Méthode pour vérifier si un patient existe
	public boolean patientExists(int id);

}
