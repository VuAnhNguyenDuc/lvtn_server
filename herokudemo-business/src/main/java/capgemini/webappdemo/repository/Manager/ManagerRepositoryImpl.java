package capgemini.webappdemo.repository.Manager;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import capgemini.webappdemo.service.UserTakesAppointment.UserTakesAppointmentService;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class ManagerRepositoryImpl extends EntityRepositoryImpl<Manager> implements ManagerRepository {
	@Autowired
	private UserTakesAppointmentService utaService;
	
	public ManagerRepositoryImpl() {
		super(Manager.class);
	}

	/*private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");*/
	
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

		String strQuery = "from Appointment a where a.manager_id = :mngID";
		Query query = session.createQuery(strQuery);
		query.setParameter("mngID",mngID);
		return query.list().size();
	}

	/*@Override
	public List<Appointment> getCreatedApmByMonth(int month, int year, int id) {
		String start = "";
		String end = "";
		if(month < 10){
			start = "00:00 01-0"+month+"-"+year;
			end = "23:59 31-0"+month+"-"+year;
		} else{
			start = "00:00 01-"+month+"-"+year;
			end = "00:00 31-"+month+"-"+year;
		}
		return null;
	}

	@Override
	public List<Appointment> getCreatedApmByYear(int year, int id) {
		return null;
	}*/

	/*private List<Appointment> listCreatedAppointments(String start_date, String end_date, int id) throws ParseException {
		Session session = getSession();
		String strQuery = "from Appointment a where a.start_date >= :start and a.end_date <= :end and a.manager_id = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("start",getDate(start_date));
		query.setParameter("end",getDate(end_date));
		query.setParameter("id",id);
		return query.list();
	}

	private Date getDate(String src) throws ParseException {
		return dateFormat.parse(src);
	}*/
}
