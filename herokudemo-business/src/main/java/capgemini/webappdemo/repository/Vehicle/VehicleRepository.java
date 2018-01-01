package capgemini.webappdemo.repository.Vehicle;

import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.repository.EntityRepository;

import java.util.List;


public interface VehicleRepository extends EntityRepository<Vehicle> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public boolean checkExist(String name, int id);

	public List<Vehicle> getActiveVehicles();

	public Vehicle getVehicleByName(String name);
	
}
