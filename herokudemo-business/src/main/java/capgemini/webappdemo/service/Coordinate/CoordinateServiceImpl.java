package capgemini.webappdemo.service.Coordinate;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.repository.Admin.AdminRepository;
import capgemini.webappdemo.repository.Coordinate.CoordinateRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CoordinateServiceImpl extends EntityServiceImpl<Coordinate> implements CoordinateService {
	
	private CoordinateRepository Repository;
	@Autowired
	public void setRepository(CoordinateRepository Repository) {
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
	public List<Coordinate> getCoordsOfDetail(int detailid) {
		return Repository.getCoordsOfDetail(detailid);
	}

}
