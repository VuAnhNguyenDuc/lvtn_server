package capgemini.webappdemo.repository.SpecialPlace;

import capgemini.webappdemo.domain.SpecialPlace;
import capgemini.webappdemo.repository.EntityRepository;

public interface SpecialPlaceRepository extends EntityRepository<SpecialPlace> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public SpecialPlace getPlaceByName(String name);
	
}
