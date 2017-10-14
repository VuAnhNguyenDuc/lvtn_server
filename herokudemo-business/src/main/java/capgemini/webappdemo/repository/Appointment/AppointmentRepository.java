package capgemini.webappdemo.repository.Appointment;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.EntityRepository;

import java.text.ParseException;
import java.util.List;


public interface AppointmentRepository extends EntityRepository<Appointment> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<User> getUsersOfAppointment(int id);

	public void updateAppointment(Appointment apm,boolean changeUsers);
}
