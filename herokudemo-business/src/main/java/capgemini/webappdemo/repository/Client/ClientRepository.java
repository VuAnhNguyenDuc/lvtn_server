package capgemini.webappdemo.repository.Client;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Client;
import capgemini.webappdemo.repository.EntityRepository;


public interface ClientRepository extends EntityRepository<Client> {

	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public Client checkClientByName(String name);
}
