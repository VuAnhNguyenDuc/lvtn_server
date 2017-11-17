package capgemini.webappdemo.service.SpecialPlace;

import capgemini.webappdemo.domain.SpecialPlace;
import capgemini.webappdemo.repository.SpecialPlace.SpecialPlaceRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class SpecialPlaceServiceImpl extends EntityServiceImpl<SpecialPlace> implements SpecialPlaceService {
	
	private SpecialPlaceRepository Repository;
	@Autowired
	public void setNameRepository(SpecialPlaceRepository Repository) {
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
	public SpecialPlace getPlaceByName(String name) {
		return Repository.getPlaceByName(name);
	}
}
