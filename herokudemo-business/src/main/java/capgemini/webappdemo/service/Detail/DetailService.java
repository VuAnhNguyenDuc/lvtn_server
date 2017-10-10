package capgemini.webappdemo.service.Detail;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.service.EntityService;

import java.text.ParseException;
import java.util.List;

public interface DetailService extends EntityService<Detail> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public void start(int id) throws ParseException;

	public void end(int id) throws ParseException;

	public void inputCost(int id, int price);

	public List<Detail> getDetailsOfAppointment(int appointment_id);
}
