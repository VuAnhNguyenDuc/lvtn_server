package capgemini.webappdemo.repository.Admin;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class AdminRepositoryImpl extends EntityRepositoryImpl<Admin> implements AdminRepository {
	
	public AdminRepositoryImpl() {
		super(Admin.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Admin";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}

	@Override
	public Admin login(String username, String password) {
		Session session = getSession();

		String strQuery = "from Admin a where a.username = :usn and a.password = :pwd";
		Query query = session.createQuery(strQuery);
		query.setParameter("usn",username);
		query.setParameter("pwd",password);
		if(query.list() != null){
			Admin result = (Admin) query.list().get(0);
			return result;
		} else{
			return null;
		}
	}
}
