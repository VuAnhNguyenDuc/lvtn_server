package capgemini.webappdemo.repository.User;


import capgemini.webappdemo.domain.Name;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class UserRepositoryImpl extends EntityRepositoryImpl<User> implements UserRepository {
	
	public UserRepositoryImpl() {
		super(User.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM User";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
