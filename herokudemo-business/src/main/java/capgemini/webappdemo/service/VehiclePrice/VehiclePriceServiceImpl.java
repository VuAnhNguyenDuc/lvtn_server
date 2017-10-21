package capgemini.webappdemo.service.VehiclePrice;


import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.domain.VehiclePrice;
import capgemini.webappdemo.repository.Vehicle.VehicleRepository;
import capgemini.webappdemo.repository.VehiclePrice.VehiclePriceRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class VehiclePriceServiceImpl extends EntityServiceImpl<VehiclePrice> implements VehiclePriceService {
	
	private VehiclePriceRepository Repository;
	@Autowired
	public void setNameRepository(VehiclePriceRepository Repository) {
		this.Repository = Repository;
		repository = Repository;
	}

	@Override
	public double getValue(String key) {
		return Repository.getValue(key);
	}
}
