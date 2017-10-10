package capgemini.webappdemo.service.Detail;


import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.repository.Coordinate.CoordinateRepository;
import capgemini.webappdemo.repository.Detail.DetailRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;


@Service
@Transactional
public class DetailServiceImpl extends EntityServiceImpl<Detail> implements DetailService {
	
	private DetailRepository Repository;
	@Autowired
	public void setRepository(DetailRepository Repository) {
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
	public void start(int id) throws ParseException {
		Repository.start(id);
	}

	@Override
	public void end(int id) throws ParseException {
		Repository.end(id);
	}

	@Override
	public void inputCost(int id, int price) {
		Repository.inputCost(id, price);
	}

	@Override
	public List<Detail> getDetailsOfAppointment(int appointment_id) {
		return Repository.getDetailsOfAppointment(appointment_id);
	}
}
