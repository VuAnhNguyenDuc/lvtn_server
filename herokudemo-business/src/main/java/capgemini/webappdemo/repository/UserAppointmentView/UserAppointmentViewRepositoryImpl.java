package capgemini.webappdemo.repository.UserAppointmentView;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import capgemini.webappdemo.service.Appointment.AppointmentService;
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
public class UserAppointmentViewRepositoryImpl extends EntityRepositoryImpl<UserAppointmentView> implements UserAppointmentViewRepository {

	@Autowired
	private AppointmentService apmService;
	
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
	public List<Appointment> getAppointmentsByMonth(int month, int year, int id,boolean isCreated) throws ParseException {
		String start_date = "";
		String end_date = "";
		if(month < 10){
			start_date = "00:00 01-0"+month+"-"+year;
			end_date = "23:59 31-0"+month+"-"+year;
		} else{
			start_date = "00:00 01-"+month+"-"+year;
			end_date = "00:00 31-"+month+"-"+year;
		}
		return getAppointmentsByPeriod(start_date,end_date,id,isCreated);
	}

	@Override
	public List<Appointment> getAppointmentsByYear(int year, int id,boolean isCreated) throws ParseException {
		String start_date = "00:00 01-01-"+year;
		String end_date = "23:59 31-12-"+year;

		return getAppointmentsByPeriod(start_date,end_date,id,isCreated);
	}

	private List<Appointment> getAppointmentsByPeriod(String start_date,String end_date, int id,boolean isCreated) throws ParseException {
		Session session = getSession();
		String strQuery = "";
		if(!isCreated){
			strQuery = "from UserAppointmentView a where a.start_date >= :start and a.start_date <= :end and a.user_id = :id";
		} else{
			strQuery = "from Appointment a where a.start_date >= :start and a.start_date <= :end and a.manager_id = :id";
		}
		Query query = session.createQuery(strQuery);
		query.setParameter("start",getDate(start_date));
		query.setParameter("end",getDate(end_date));
		query.setParameter("id",id);
		if(query.list().size() > 0){
			if(!isCreated){
				List<UserAppointmentView> uavs = query.list();
				List<Appointment> apms = new ArrayList<Appointment>();
				for(UserAppointmentView uav:uavs){
					Appointment apm = apmService.get(uav.getAppointment_id());
					apms.add(apm);
				}
				return apms;
			}
			return query.list();
		} else{
			return null;
		}
	}

	private Date getDate(String src) throws ParseException {
		return dateFormat.parse(src);
	}

}
