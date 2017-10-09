package capgemini.webappdemo.repository.Image;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.repository.EntityRepository;


public interface ImageRepository extends EntityRepository<Image> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
	
}
