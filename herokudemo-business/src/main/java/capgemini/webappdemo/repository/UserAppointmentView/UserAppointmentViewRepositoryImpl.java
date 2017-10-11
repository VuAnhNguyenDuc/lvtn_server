package capgemini.webappdemo.repository.UserAppointmentView;

import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class UserAppointmentViewRepositoryImpl extends EntityRepositoryImpl<UserAppointmentView> implements UserAppointmentViewRepository {
	
	public UserAppointmentViewRepositoryImpl() {
		super(UserAppointmentView.class);
	}

	private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
	
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

	@Override
	public List<UserAppointmentView> getAppointmentsByMonth(int month, int year, int manager_id) throws ParseException {
		String start_date = "";
		String end_date = "";
		if(month < 10){
			start_date = "00:00 01-0"+month+"-"+year;
			end_date = "23:59 31-0"+month+"-"+year;
		} else{
			start_date = "00:00 01-"+month+"-"+year;
			end_date = "00:00 31-"+month+"-"+year;
		}
		return getAppointmentsByPeriod(start_date,end_date,manager_id);
	}

	@Override
	public List<UserAppointmentView> getAppointmentsByYear(int year, int manager_id) throws ParseException {
		String start_date = "00:00 01-01-"+year;
		String end_date = "23:59 31-12-"+year;

		return getAppointmentsByPeriod(start_date,end_date,manager_id);
	}

	private List<UserAppointmentView> getAppointmentsByPeriod(String start_date,String end_date, int manager_id) throws ParseException {
		Session session = getSession();
		String strQuery = "";
		if(manager_id == 0){
			strQuery = "from UserAppointmentView a where a.start_date >= :start and a.start_date <= :end";
		} else{
			strQuery = "from UserAppointmentView a where a.start_date >= :start and a.start_date <= :end and a.created_by = :managerid";
		}
		Query query = session.createQuery(strQuery);
		query.setParameter("start",getDate(start_date));
		query.setParameter("end",getDate(end_date));
		if(manager_id != 0){
			query.setParameter("managerid",manager_id);
		}
		if(query.list().size() > 0){
			return query.list();
		} else{
			return null;
		}
	}

	private Date getDate(String src) throws ParseException {
		return dateFormat.parse(src);
	}

}
