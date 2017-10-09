package capgemini.webappdemo.service.Coordinate;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.service.EntityService;

public interface CoordinateService extends EntityService<Coordinate> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
