package dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import dao.interfaces.SchedulerDAO;
import models.Scheduler;

public class ImpSchedulerDAO implements SchedulerDAO {
	private Connection connection;
	private static final String tableName = "schedulers";
	private static final String superTableName = "personnes";
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Statement statement;
	private String sql;
	
	@Override
	public void addScheduler(Scheduler scheduler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteScheduler(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateScheduler(Scheduler scheduler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Scheduler getScheduler(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Scheduler> getAllSchedulers() {
		// TODO Auto-generated method stub
		return null;
	}

}
