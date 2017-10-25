package capgemini.webappdemo.service.Client;

import capgemini.webappdemo.domain.Client;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.EntityService;

public interface ClientService extends EntityService<Client> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public Client checkClientByName(String name);

}
