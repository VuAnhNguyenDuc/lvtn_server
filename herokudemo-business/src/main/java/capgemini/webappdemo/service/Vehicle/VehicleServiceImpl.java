package capgemini.webappdemo.service.Vehicle;


import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.repository.User.UserRepository;
import capgemini.webappdemo.repository.Vehicle.VehicleRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class VehicleServiceImpl extends EntityServiceImpl<Vehicle> implements VehicleService {
	
	private VehicleRepository Repository;
	@Autowired
	public void setNameRepository(VehicleRepository Repository) {
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

	@Override
	public boolean checkExist(String name, int id) {
		return Repository.checkExist(name,id);
	}

	@Override
	public List<Vehicle> getActiveVehicles() {
		return Repository.getActiveVehicles();
	}

	@Override
	public Vehicle getVehicleByName(String name) {
		return Repository.getVehicleByName(name);
	}
}
