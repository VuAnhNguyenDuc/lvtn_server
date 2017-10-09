package capgemini.webappdemo.repository.Detail;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.repository.EntityRepository;


public interface DetailRepository extends EntityRepository<Detail> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
