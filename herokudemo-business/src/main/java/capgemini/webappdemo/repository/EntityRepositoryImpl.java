package capgemini.webappdemo.repository;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
	public T get(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (T) session.get(clazz, id);
	}

	@Override
	public List<T> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(clazz);
		Collections.sort(criteria.list(), new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Object id1 = new Object();
				Object id2 = new Object();
				try {
					id1 = o1.getClass().getField("id");
					id2 = o2.getClass().getField("id");
					System.out.println(id1);
					System.out.println(id2);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				return (Integer) id1 < (Integer) id2? -1 : 1;
			}
		});
		return criteria.list();
	}

	@Override
	public void add(T entity) {
		Session session = getSession();
		try {
			session.save(entity);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(T entity) {
		Session session = getSession();
		try {
			session.merge(entity);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(T entity) {
		Session session = getSession();
		try {
			session.delete(entity);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
