package capgemini.webappdemo.repository.Manager;

import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.repository.EntityRepository;


public interface ManagerRepository extends EntityRepository<Manager> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public void assignAppointmentToUser(int apmID, int userID);
	
}
