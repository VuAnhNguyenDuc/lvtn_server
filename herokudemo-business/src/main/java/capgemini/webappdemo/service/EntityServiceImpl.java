package capgemini.webappdemo.service;

import java.util.List;

import capgemini.webappdemo.repository.EntityRepository;



public abstract class EntityServiceImpl<T> implements EntityService<T> {
	
	protected EntityRepository<T> repository;

	@Override
	public List<T> getAll() {
		return repository.getAll();
	}

	@Override
	public T get(Long id) {
		return repository.get(id);
	}

	@Override
	public boolean add(T entity) {
		repository.add(entity);
		return true;
	}

	@Override
	public void update(T entity) {
		repository.update(entity);
	}

	@Override
	public void remove(T entity) {
		repository.remove(entity);
	}
	
}
