package capgemini.webappdemo.service.Coordinate;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.service.EntityService;
import org.json.simple.JSONArray;

import java.util.List;

public interface CoordinateService extends EntityService<Coordinate> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<Coordinate> getCoordsOfDetail(int detailid);

	public JSONArray parseCoords(List<Coordinate> coords);
}
