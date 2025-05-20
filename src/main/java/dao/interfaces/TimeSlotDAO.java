package dao.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import models.TimeSlot;

public interface TimeSlotDAO {
	public void addTimeSlot(TimeSlot timeSlot);
	public void updateTimeSlot(TimeSlot timeSlot);
	public void deleteTimeSlot(int id);
	public TimeSlot getTimeSlotById(int id);
	public List<TimeSlot> getAllTimeSlots();
	public List<TimeSlot> getAvailableTimeSlots();
	public List<TimeSlot> getAvailableTimeSlotsForDate(LocalDateTime date);
}
