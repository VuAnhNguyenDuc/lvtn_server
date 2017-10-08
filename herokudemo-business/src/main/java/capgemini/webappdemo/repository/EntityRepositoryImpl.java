package capgemini.webappdemo.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Generic implementation of some CRUD functionality, and utility method for fetching data.
 * A class extending this will have get, add, update and remove implemented already, and
 * will have access to the sessions via getSession().
 * This might be overkill, but it avoids repeating similar code and documentation in every repository implementation.
 * 
 * @author aoppeboe
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
@Transactional
public class EntityRepositoryImpl<T> implements EntityRepository<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class clazz;
	public EntityRepositoryImpl(Class clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	protected List<T> getAll(Criteria criteria) {
		List<T> entities = criteria.list();
		return entities;
	}

	protected T get(Criteria criteria) {
		List<T> entities = getAll(criteria);
		if (entities.isEmpty()) return null;
		return entities.get(0);
	}

	protected Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
	
	@Override
	public T get(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (T) session.get(clazz, id);
	}

	@Override
	public List<T> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(clazz);
		return criteria.list();
	}

	@Override
	public void add(T entity) {
		Session session = getSession();
		session.save(entity);
	}

	@Override
	public void update(T entity) {
		Session session = getSession();
		session.merge(entity);
	}

	@Override
	public void remove(T entity) {
		Session session = getSession();
		session.delete(entity);
	}
}
