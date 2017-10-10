package capgemini.webappdemo.service.Appointment;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.service.EntityService;

public interface AppointmentService extends EntityService<Appointment> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();
}