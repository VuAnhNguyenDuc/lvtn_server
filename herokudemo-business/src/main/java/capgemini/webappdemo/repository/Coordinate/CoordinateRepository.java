package capgemini.webappdemo.repository.Coordinate;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.repository.EntityRepository;

import java.util.List;


public interface CoordinateRepository extends EntityRepository<Coordinate> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<Coordinate> getCoordsOfDetail(int detailid);
	
}
