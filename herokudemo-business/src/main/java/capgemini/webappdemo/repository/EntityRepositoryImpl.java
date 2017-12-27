package capgemini.webappdemo.repository;

import java.util.*;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.domain.UserTakesAppointment;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
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
		List<T> result = criteria.list();
		sort(result);
		return result;
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

	@Override
	public void sort(List<T> entities) {
		Collections.sort(entities, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				int id1 = 0;
				int id2 = 0;
				try {
					if(!o1.getClass().equals(Employee.class) && !o1.getClass().equals(Manager.class) && !o1.getClass().equals(UserAppointmentView.class) && !o1.getClass().equals(UserTakesAppointment.class)){
						Field field = o1.getClass()
								.getDeclaredField("id");
						field.setAccessible(true);
						id1 = field.getInt(o1);
						field.setAccessible(false);

						field = o2.getClass().getDeclaredField("id");
						field.setAccessible(true);
						id2 = field.getInt(o2);
						field.setAccessible(false);
					} else{
						Field field = o1.getClass()
								.getDeclaredField("user_id");
						field.setAccessible(true);
						id1 = field.getInt(o1);
						field.setAccessible(false);

						field = o2.getClass().getDeclaredField("user_id");
						field.setAccessible(true);
						id2 = field.getInt(o2);
						field.setAccessible(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return id1 < id2? 1 : -1;
			}
		});
	}

}
