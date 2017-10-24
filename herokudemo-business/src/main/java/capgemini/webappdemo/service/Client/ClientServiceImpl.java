package capgemini.webappdemo.service.Client;


import capgemini.webappdemo.domain.Client;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.repository.Client.ClientRepository;
import capgemini.webappdemo.repository.Vehicle.VehicleRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ClientServiceImpl extends EntityServiceImpl<Client> implements ClientService {
	
	private ClientRepository Repository;
	@Autowired
	public void setNameRepository(ClientRepository Repository) {
		this.Repository = Repository;
		repository = Repository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		Repository.deleteAll();
	}

}
