package capgemini.webappdemo.service.Image;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.service.EntityService;

public interface ImageService extends EntityService<Image> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
