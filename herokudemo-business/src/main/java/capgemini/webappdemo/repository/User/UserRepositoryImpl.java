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

    private DateFormat dateWithSec = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

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
		Session session = getSession();

		String strQuery = "from UserAppointmentView uav where uav.user_id = :id and uav.status = 1";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);
		List<UserAppointmentView> result = query.list();
		if(result.size() > 0){
			for(UserAppointmentView uav : result){
				getUAVInfo(uav);
			}
		}
		return result;
	}

	@Override
	public List<UserAppointmentView> getCompletedAppointments(int id) {
		Session session = getSession();

		String strQuery = "from UserAppointmentView uav where uav.user_id = :id and uav.status != 1";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);

		List<UserAppointmentView> all = query.list();
		for(UserAppointmentView ap : all){
			getUAVInfo(ap);
		}
		return all;
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

	@Override
	public List<Detail> getDetailsOfUser(int id) {
		Session session = getSession();

		String strQuery = "from Detail dt where dt.user_created = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);
		return query.list();
	}

	@Override
	public double getCostOfMonth(int month, int year, int id) {
		Session session = getSession();

		String start_date = "";
		String end_date = "";
		if(month < 10){
			start_date = "00:00:00 01-0"+month+"-"+year;
			end_date = "23:59:00 31-0"+month+"-"+year;
		} else{
			start_date = "00:00:00 01-"+month+"-"+year;
			end_date = "00:00:00 31-"+month+"-"+year;
		}

		String strQuery = "from Detail dt where dt.user_created = :id and dt.start_time >= :start and dt.start_time < :end";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);
		query.setParameter("start",dateWithSec.format(start_date));
		query.setParameter("end",dateWithSec.format(end_date));
		double total = 0;
		List<Detail> dts = query.list();
		if(dts.size() > 0){
			for (Detail dt : dts){
				total += dt.getInput_cost();
			}
		}
		return total;
	}

	private void getUAVInfo(UserAppointmentView uav){
		Date date = uav.getStart_date();
		uav.setStart_date_str(convertDateToString(date));
		uav.setManagerName(userService.get(uav.getCreate_by()).getUsername());
		//uav.setCreate_by(0);
	}

	public String convertDateToString(Date date){
        return dateFormat.format(date);
    }
}
