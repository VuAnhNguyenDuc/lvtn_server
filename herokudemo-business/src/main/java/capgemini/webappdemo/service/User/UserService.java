package capgemini.webappdemo.service.User;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.EntityService;

import java.text.ParseException;
import java.util.List;

public interface UserService extends EntityService<User> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public User checkLogin(String username, String password);

	public String changePassword(int id, String newPassword);

	public String getUserType(int id);

	public List<UserAppointmentView> getActiveAppointments(int id);

	public List<UserAppointmentView> getCompletedAppointments(int id);

	public List<UserAppointmentView> getAllAppointments(int id);

	public boolean checkUserExist(String username);

	public UserAppointmentView getAppointment(int id);

	public List getDetailsOfUser(int id);

	public double getCostOfMonth(int month, int year,int id);
}
