package capgemini.webappdemo.repository;

import capgemini.webappdemo.domain.Name;


public interface NameRepository extends EntityRepository<Name> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
