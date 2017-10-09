package capgemini.webappdemo.service.UserAppointmentView;

import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.service.EntityService;

public interface UserAppointmentViewService extends EntityService<UserAppointmentView> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
