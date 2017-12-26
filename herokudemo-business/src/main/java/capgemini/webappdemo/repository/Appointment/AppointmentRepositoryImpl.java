package capgemini.webappdemo.repository.Appointment;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserTakesAppointment.UserTakesAppointmentService;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class AppointmentRepositoryImpl extends EntityRepositoryImpl<Appointment> implements AppointmentRepository {

	@Autowired
	private UserTakesAppointmentService utaService;

	@Autowired
	private UserService userService;

	private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
	
	public AppointmentRepositoryImpl() {
		super(Appointment.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Appointment";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}

	@Override
	public List<User> getUsersOfAppointment(int id) {
		Session session = getSession();

		String strQuery = "from UserTakesAppointment uta where uta.appointment_id = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",id);
		List<UserTakesAppointment> utas = query.list();
		if(utas.size() == 0){
			return new ArrayList<User>();
		} else{
			List<User> users = new ArrayList<User>();
			for(UserTakesAppointment uta : utas){
				User user = userService.get(uta.getEmployee_id());
				users.add(user);
			}
			return users;
		}
	}

	@Override
	public boolean checkAppointmentExist(String name) {
		if(getApmByName(name)!=null){
			return true;
		}
		return false;
	}

	@Override
	public Appointment getApmByName(String name) {
		Session session = getSession();

		String strQuery = "from Appointment a where a.name = :name";
		Query query = session.createQuery(strQuery);
		query.setParameter("name",name);
		if(query.list().size() == 1){
			return (Appointment) query.list().get(0);
		}
		return null;
	}

	@Override
	public List<Appointment> getApmsByStatus(String status) {
		Session session = getSession();
		int statusInt = 0;
		if(status.equals("all")){
			return getAll();
		} else if(status.equals("active")){
			statusInt = 1;
		} else if(status.equals("finished")){
			statusInt = 0;
		} else if(status.equals("warning")){
			statusInt = -1;
		}
		String strQuery = "from Appointment a where a.status = :sta";
		Query query = session.createQuery(strQuery);
		query.setParameter("sta",statusInt);
		List<Appointment> result = query.list();
		if(result.size() >= 2){
			sort(result);
		}
		return result;
	}

	@Override
	public void updateAppointment(Appointment apm,boolean changeUsers) {
		try {
			Session session = getSession();
			session.merge(apm);

			if(changeUsers){
                List<User> usrs = apm.getUsers();

                String strQuery = "delete from UserTakesAppointment uta where uta.appointment_id =:apmid";
                Query query = session.createQuery(strQuery);
                query.setParameter("apmid",apm.getId());
                for(User usr : usrs){
                	strQuery = "from UserTakesAppointment uta where uta.appointment_id = :apmid and uta.user_id = :userid";
                	query = session.createQuery(strQuery);
                	query.setParameter("apmid", apm.getId());
                	query.setParameter("userid",usr.getId());
                	if(query.list().size() == 0){
						UserTakesAppointment uta = new UserTakesAppointment(apm.getId(),usr.getId());
						utaService.add(uta);
					}
                }
            }
		} catch (HibernateException e) {
			System.out.println("Exception : ");
			e.printStackTrace();
		}
	}

	private Date getDate() throws ParseException {
		Date date = new Date();
		String strDate = dateFormat.format(date);
		Date result = dateFormat.parse(strDate);
		return result;
	}
}
