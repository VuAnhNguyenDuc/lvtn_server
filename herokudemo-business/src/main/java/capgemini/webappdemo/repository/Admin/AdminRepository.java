package capgemini.webappdemo.repository.Admin;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.EntityRepository;


public interface AdminRepository extends EntityRepository<Admin> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
