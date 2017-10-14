package capgemini.webappdemo.service.Appointment;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.EntityService;

import java.util.List;

public interface AppointmentService extends EntityService<Appointment> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<User> getUsersOfAppointment(int id);

	public void updateAppointment(Appointment apm,boolean changeUsers);
}
