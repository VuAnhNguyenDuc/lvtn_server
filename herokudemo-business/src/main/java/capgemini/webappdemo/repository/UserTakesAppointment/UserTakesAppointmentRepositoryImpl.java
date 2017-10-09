package capgemini.webappdemo.repository.UserTakesAppointment;


import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class UserTakesAppointmentRepositoryImpl extends EntityRepositoryImpl<UserTakesAppointment> implements UserTakesAppointmentRepository {
	
	public UserTakesAppointmentRepositoryImpl() {
		super(UserTakesAppointment.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM UserTakesAppointment";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
