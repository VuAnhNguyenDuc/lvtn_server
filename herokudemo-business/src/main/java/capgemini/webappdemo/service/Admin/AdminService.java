package capgemini.webappdemo.service.Admin;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.EntityService;

public interface AdminService extends EntityService<Admin> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
