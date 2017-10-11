package capgemini.webappdemo.service.UserAppointmentView;

import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.service.EntityService;

import java.text.ParseException;
import java.util.List;

public interface UserAppointmentViewService extends EntityService<UserAppointmentView> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<UserAppointmentView> getAppointmentsByMonth(int month, int year, int manager_id) throws ParseException;

	public List<UserAppointmentView> getAppointmentsByYear(int year,int manager_id) throws ParseException;
}
