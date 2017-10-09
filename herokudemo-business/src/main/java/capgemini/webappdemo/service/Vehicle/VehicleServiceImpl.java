package capgemini.webappdemo.service.Vehicle;


import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.repository.User.UserRepository;
import capgemini.webappdemo.repository.Vehicle.VehicleRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class VehicleServiceImpl extends EntityServiceImpl<Vehicle> implements VehicleService {
	
	private VehicleRepository userRepository;
	@Autowired
	public void setNameRepository(VehicleRepository userRepository) {
		this.userRepository = userRepository;
		repository = userRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}
}
