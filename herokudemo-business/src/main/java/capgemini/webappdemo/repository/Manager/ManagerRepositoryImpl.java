package capgemini.webappdemo.repository.Manager;

import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import capgemini.webappdemo.service.UserTakesAppointment.UserTakesAppointmentService;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ManagerRepositoryImpl extends EntityRepositoryImpl<Manager> implements ManagerRepository {
	@Autowired
	private UserTakesAppointmentService utaService;
	
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

	@Override
	public void assignAppointmentToUser(int apmID, int userID) {
		utaService.add(new UserTakesAppointment(apmID,userID));
	}

	@Override
	public int getCreatedAppointments(int mngID) {
		Session session = getSession();

		String strQuery = "from UserAppointmentView uav where uav.create_by = :mngID";
		Query query = session.createQuery(strQuery);
		query.setParameter("mngID",mngID);
		return query.list().size();
	}
}
