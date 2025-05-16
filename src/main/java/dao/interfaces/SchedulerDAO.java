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
<<<<<<< HEAD
	/**
	 * Récupère un scheduler par son id.
	 */
	public models.Scheduler getSchedulerById(int id);
=======
>>>>>>> 682628f10b4231d1259ea5f51b647b5f995d5cc3

}
