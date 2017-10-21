package capgemini.webappdemo.service.VehiclePrice;

import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.domain.VehiclePrice;
import capgemini.webappdemo.service.EntityService;

public interface VehiclePriceService extends EntityService<VehiclePrice> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public double getValue(String key);
}
