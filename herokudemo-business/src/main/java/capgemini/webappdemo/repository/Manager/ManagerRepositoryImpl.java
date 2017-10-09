package capgemini.webappdemo.repository.Manager;


import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ManagerRepositoryImpl extends EntityRepositoryImpl<Manager> implements ManagerRepository {
	
	public ManagerRepositoryImpl() {
		super(Manager.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Manager";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
