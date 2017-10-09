package capgemini.webappdemo.repository.Coordinate;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.repository.EntityRepository;


public interface CoordinateRepository extends EntityRepository<Coordinate> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
