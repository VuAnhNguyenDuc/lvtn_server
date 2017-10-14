package capgemini.webappdemo.repository.User;


import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class UserRepositoryImpl extends EntityRepositoryImpl<User> implements UserRepository {
	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private DetailService detailService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserAppointmentViewService uavService;

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");

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
		if(query.list() != null && query.list().size() > 0){
			return "Employee";
		} else{
			strQuery = "from Manager m where m.user_id = :id";
			query = session.createQuery(strQuery);
			query.setParameter("id",id);
			if(query.list() != null && query.list().size() > 0){
				return "Manager";
			} else{
				return "this user does not exist";
			}
		}
	}

	@Override
	public List<UserAppointmentView> getActiveAppointments(int id) {
		List<UserAppointmentView> all = getAllAppointments(id);
		List<UserAppointmentView> result = new ArrayList<UserAppointmentView>();
		for(UserAppointmentView ap : all){
			if(ap.getStatus() == 1){
				result.add(ap);
			}
		}
		return result;
	}

	@Override
	public List<UserAppointmentView> getAllAppointments(int id) {
		Session session = getSession();

		String strQuery = "from UserAppointmentView uav where uav.user_id = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);

		List<UserAppointmentView> all = query.list();
		for(UserAppointmentView ap : all){
			getUAVInfo(ap);
		}
		return all;
	}

	@Override
	public boolean checkUserExist(String username) {
		Session session = getSession();

		String strQuery = "from User u where u.username = :name";
		Query query = session.createQuery(strQuery);
		query.setParameter("name",username);
		if(query.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public UserAppointmentView getAppointment(int id) {
		Session session = getSession();

		String strQuery = "from UserAppointmentView uav where uav.appointment_id = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);
		UserAppointmentView uav = (query.list().size() > 0)? (UserAppointmentView) query.list().get(0) : null;
		if(uav == null){
			return null;
		} else {
			getUAVInfo(uav);
			return uav;
		}
	}

	private void getUAVInfo(UserAppointmentView uav){
		Date date = uav.getStart_date();
		uav.setStart_date_str(convertDateToString(date));
		uav.setManagerName(userService.get(uav.getCreate_by()).getUsername());
		if(uav.getEnd_date() != null){
			uav.setEnd_date_str(convertDateToString(uav.getEnd_date()));
		}
		//uav.setCreate_by(0);
	}

	public String convertDateToString(Date date){
        return dateFormat.format(date);
    }
}
