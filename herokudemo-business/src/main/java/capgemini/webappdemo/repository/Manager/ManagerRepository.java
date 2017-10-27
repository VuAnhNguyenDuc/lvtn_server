package capgemini.webappdemo.repository.Manager;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.EntityRepository;

import java.text.ParseException;
import java.util.List;


public interface ManagerRepository extends EntityRepository<Manager> {

	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public void assignAppointmentToUser(int apmID, int userID);

	public int getCreatedAppointments(int mngID);
}
