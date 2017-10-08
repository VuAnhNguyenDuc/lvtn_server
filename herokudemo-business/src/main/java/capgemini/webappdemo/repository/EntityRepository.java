package capgemini.webappdemo.repository;

import java.util.List;

/**
 * Generic repositories for entities of a given type T.
 * A class implementing this will support basic CRUD functionality.
 * 
 * @author aoppeboe
 *
 * @param <T>
 */
public interface EntityRepository<T> {
	
	public List<T> getAll();
	public T get(Long id);
	public void add(T entity);
	public void update(T entity);
	public void remove(T entity);
	
}
