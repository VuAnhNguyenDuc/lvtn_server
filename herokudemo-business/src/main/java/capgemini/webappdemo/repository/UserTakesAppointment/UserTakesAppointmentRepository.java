package capgemini.webappdemo.repository.UserTakesAppointment;

import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.repository.EntityRepository;


public interface UserTakesAppointmentRepository extends EntityRepository<UserTakesAppointment> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
