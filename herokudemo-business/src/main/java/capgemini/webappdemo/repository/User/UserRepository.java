package capgemini.webappdemo.repository.User;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Name;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.EntityRepository;

import java.util.List;


public interface UserRepository extends EntityRepository<User> {
	
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
	
}
