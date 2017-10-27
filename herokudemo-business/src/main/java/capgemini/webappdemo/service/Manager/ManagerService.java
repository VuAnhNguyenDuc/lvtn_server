package capgemini.webappdemo.service.Manager;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.service.EntityService;

import java.text.ParseException;
import java.util.List;

public interface ManagerService extends EntityService<Manager> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public void assignAppointmentToUser(int apmID, int userID);

	public int getCreatedAppointments(int mngID);

	public List<Appointment> getCreatedApmByMonth(int month, int year, int id);

	public List<Appointment> getCreatedApmByYear(int year, int id);
}
