package capgemini.webappdemo.repository.UserAppointmentView;


import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class UserAppointmentViewRepositoryImpl extends EntityRepositoryImpl<UserAppointmentView> implements UserAppointmentViewRepository {
	
	public UserAppointmentViewRepositoryImpl() {
		super(UserAppointmentView.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM UserAppointmentView";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
