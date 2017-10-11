package capgemini.webappdemo.repository.Appointment;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserTakesAppointment.UserTakesAppointmentService;
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
			return null;
		} else{
			List<User> users = new ArrayList<User>();
			for(UserTakesAppointment uta : utas){
				User user = userService.get(uta.getEmployee_id());
				users.add(user);
			}
			return users;
		}
	}

	private Date getDate() throws ParseException {
		Date date = new Date();
		String strDate = dateFormat.format(date);
		Date result = dateFormat.parse(strDate);
		return result;
	}
}
