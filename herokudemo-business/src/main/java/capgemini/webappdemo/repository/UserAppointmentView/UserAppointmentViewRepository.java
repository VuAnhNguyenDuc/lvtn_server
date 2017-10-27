package capgemini.webappdemo.repository.UserAppointmentView;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.EntityRepository;

import java.text.ParseException;
import java.util.List;


public interface UserAppointmentViewRepository extends EntityRepository<UserAppointmentView> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<Appointment> getAppointmentsByMonth(int month, int year, int id, boolean isCreated) throws ParseException;

	public List<Appointment> getAppointmentsByYear(int year,int id, boolean isCreated) throws ParseException;


	
}
