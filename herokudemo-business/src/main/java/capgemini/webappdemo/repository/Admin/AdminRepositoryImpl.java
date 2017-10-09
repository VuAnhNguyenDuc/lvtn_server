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
}
