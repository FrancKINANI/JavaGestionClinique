package dao.interfaces;

import java.util.List;

import models.Admin;

public interface AdminDAO {
	//CRUD
	public void addAdmin(Admin admin);
	public void deleteAdmin(int id);
	public void updateAdmin(Admin admin);
	public List<Admin> getAllAdmins();
	public Admin getAdminById(int id);
}
