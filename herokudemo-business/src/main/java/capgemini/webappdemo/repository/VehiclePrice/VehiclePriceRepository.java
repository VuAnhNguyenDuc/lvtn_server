package capgemini.webappdemo.repository.VehiclePrice;

import capgemini.webappdemo.domain.VehiclePrice;
import capgemini.webappdemo.repository.EntityRepository;


public interface VehiclePriceRepository extends EntityRepository<VehiclePrice> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public double getValue(String key);
	
}
