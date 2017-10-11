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

	public List<UserAppointmentView> getAppointmentsByMonth(int month, int year, int manager_id) throws ParseException;

	public List<UserAppointmentView> getAppointmentsByYear(int year,int manager_id) throws ParseException;


	
}
