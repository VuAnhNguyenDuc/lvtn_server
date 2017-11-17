package capgemini.webappdemo.service.SpecialPlace;

import capgemini.webappdemo.domain.SpecialPlace;
import capgemini.webappdemo.service.EntityService;

import java.util.List;

public interface SpecialPlaceService extends EntityService<SpecialPlace> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public SpecialPlace getPlaceByName(String name);
}
