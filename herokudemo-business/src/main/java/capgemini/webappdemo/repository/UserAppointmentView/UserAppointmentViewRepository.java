package capgemini.webappdemo.repository.UserAppointmentView;

import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.EntityRepository;


public interface UserAppointmentViewRepository extends EntityRepository<UserAppointmentView> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
