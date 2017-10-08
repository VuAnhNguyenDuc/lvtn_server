package capgemini.webappdemo.service;

import capgemini.webappdemo.domain.Name;

public interface NameService extends EntityService<Name> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
