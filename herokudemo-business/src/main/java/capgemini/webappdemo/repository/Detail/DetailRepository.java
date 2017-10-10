package capgemini.webappdemo.repository.Detail;

import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.repository.EntityRepository;

import java.text.ParseException;
import java.util.List;


public interface DetailRepository extends EntityRepository<Detail> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public void start(int id) throws ParseException;

	public void end(int id) throws ParseException;

	public void inputCost(int id, int price);
	
}
