package capgemini.webappdemo.service.Manager;

import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.service.EntityService;

public interface ManagerService extends EntityService<Manager> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
