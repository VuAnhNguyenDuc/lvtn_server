package capgemini.webappdemo.repository.User;


import capgemini.webappdemo.domain.Name;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

	@Override
	public User checkLogin(String username, String password) {
		Session session = getSession();

		String strQuery = "FROM User u WHERE u.username = :usn and u.password = :psw";
		Query query = session.createQuery(strQuery);
		query.setParameter("usn",username);
		query.setParameter("psw",password);

		List<User> results = query.list();
		if(results != null && results.size() > 0){
			return results.get(0);
		} else{
			return null;
		}
	}
}
