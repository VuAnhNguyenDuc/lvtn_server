package capgemini.webappdemo.repository.Vehicle;

import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.repository.EntityRepository;


public interface VehicleRepository extends EntityRepository<Vehicle> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
