package capgemini.webappdemo.service.Vehicle;

import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.EntityService;

import java.util.List;

public interface VehicleService extends EntityService<Vehicle> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public boolean checkExist(String name);

	public List<Vehicle> getActiveVehicles();
}
