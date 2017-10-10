package capgemini.webappdemo.repository.Appointment;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Repository
@Transactional
public class AppointmentRepositoryImpl extends EntityRepositoryImpl<Appointment> implements AppointmentRepository {

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

	private Date getDate() throws ParseException {
		Date date = new Date();
		String strDate = dateFormat.format(date);
		Date result = dateFormat.parse(strDate);
		return result;
	}
}
