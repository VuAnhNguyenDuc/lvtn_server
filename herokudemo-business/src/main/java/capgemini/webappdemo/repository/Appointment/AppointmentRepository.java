package capgemini.webappdemo.repository.Appointment;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.repository.EntityRepository;

import java.text.ParseException;


public interface AppointmentRepository extends EntityRepository<Appointment> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}
