package dao.interfaces;

import java.util.List;

import models.Scheduler;

public interface SchedulerDAO {
	//CRUD
	public void addScheduler(Scheduler scheduler);
	public void deleteScheduler(int id);
	public void updateScheduler(Scheduler scheduler);
	public Scheduler getScheduler(int id);
	public List<Scheduler> getAllSchedulers();
	/**
	 * Récupère un scheduler par son id.
	 */
	public models.Scheduler getSchedulerById(int id);

}
