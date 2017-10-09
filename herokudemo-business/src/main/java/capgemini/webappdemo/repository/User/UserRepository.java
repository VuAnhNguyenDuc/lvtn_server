package capgemini.webappdemo.repository.User;

import capgemini.webappdemo.domain.Name;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.EntityRepository;


public interface UserRepository extends EntityRepository<User> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
