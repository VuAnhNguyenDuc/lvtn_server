package capgemini.webappdemo.service.UserTakesAppointment;

import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.service.EntityService;

public interface UserTakesAppointmentService extends EntityService<UserTakesAppointment> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
