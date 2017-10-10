package capgemini.webappdemo.repository.User;


import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class UserRepositoryImpl extends EntityRepositoryImpl<User> implements UserRepository {

	@Autowired
	private AppointmentService appointmentService;
	
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

	@Override
	public String changePassword(int id, String newPassword) {
		Session session = getSession();
		User user = get(id);
		if(user!=null){
			user.setPassword(newPassword);
			update(user);
			return "password updated successfully";
		} else{
			return "cannot find this user";
		}
	}

	@Override
	public String getUserType(int id) {
		Session session = getSession();
		String strQuery = "from Employee e where e.user_id = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);
		if(query.list() != null){
			return "Employee";
		} else{
			strQuery = "from Manager m where m.user_id = :id";
			query = session.createQuery(strQuery);
			if(query.list() != null){
				return "Manager";
			} else{
				return "this user does not exist";
			}
		}
	}

	@Override
	public List<Appointment> getActiveAppointments(int id) {
		List<Appointment> all = getAllAppointments(id);
		List<Appointment> result = new ArrayList<Appointment>();
		for(Appointment ap : all){
			if(ap.getStatus() == 1){
				result.add(ap);
			}
		}
		return result;
	}

	@Override
	public List<Appointment> getAllAppointments(int id) {
		Session session = getSession();
		String strQuery = "from UserTakesAppointment uta where uta.user_id = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);
		List<UserTakesAppointment> utaList = query.list();
		if(utaList != null && utaList.size() > 0){
			List<Appointment> result = new ArrayList<Appointment>();
			for(UserTakesAppointment uta : utaList){
				Appointment ap = appointmentService.get(uta.getAppointment_id());
				result.add(ap);
			}
			return result;
		}
		return new ArrayList<Appointment>();
	}
}
