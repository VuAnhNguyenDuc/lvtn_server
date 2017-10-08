package capgemini.webappdemo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import capgemini.webappdemo.domain.Name;
import capgemini.webappdemo.repository.NameRepository;



@Service
@Transactional
public class NameServiceImpl extends EntityServiceImpl<Name> implements NameService {
	
	private NameRepository nameRepository;
	@Autowired
	public void setNameRepository(NameRepository nameRepository) {
		this.nameRepository = nameRepository;
		repository = nameRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		nameRepository.deleteAll();
	}
}
