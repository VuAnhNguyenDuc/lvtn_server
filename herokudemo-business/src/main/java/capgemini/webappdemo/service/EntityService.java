package capgemini.webappdemo.service;

import java.util.List;

/**
 * Generic service for entities of a given type T.
 * A class implementing this will support basic CRUD functionality.
 * 
 * @author aoppeboe
 *
 * @param <T>
 */
public interface EntityService<T> {


	/**
	 * Use with care, as the list might get big.
	 * @return List of every entity<T> in the system.
	 */
	public List<T> getAll();

	/**
	 * @param id
	 * @return Entity<T> with the given id. Null if no such entity<T> exist.
	 */
	public T get(Long id);

	/**
	 * Save the entity<T> to persistent storage. 
	 * Will generate a new id for the entity<T>, 
	 * even if the entity<T> had been previously added.
	 * Do not use this method for updating the entity<T>.
	 * @param entity<T>
	 * @return True if entity was added.
	 */
	public boolean add(T entity);

	/**
	 * Update the entity<T>.
	 * If the entity<T> is not in persistent storage,
	 * a new id will be generated for the entity<T>,
	 * and it will be saved to persistent storage.
	 * TODO: Sure about that?
	 * @param entity<T>
	 */
	public void update(T entity);

	/**
	 * Remove the entity<T> from persistent storage.
	 * TODO: What if it doesn't exist?
	 * @param entity<T>
	 */
	public void remove(T entity);
	
}
