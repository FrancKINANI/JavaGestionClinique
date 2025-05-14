package dao.interfaces;

public interface RoleDAO {
	/**
	 * This method is used to get the role of a user by their email.
	 * 
	 * @param email The email of the user whose role is to be fetched.
	 * @return The role of the user as a String.
	 */
	public String getRoleById(int id);
}
