package capgemini.webappdemo.service.Detail;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.service.EntityService;

public interface DetailService extends EntityService<Detail> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
