package capgemini.webappdemo.service.Appointment;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.Admin.AdminRepository;
import capgemini.webappdemo.repository.Appointment.AppointmentRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AppointmentServiceImpl extends EntityServiceImpl<Appointment> implements AppointmentService {
	
	private AppointmentRepository Repository;
	@Autowired
	public void setRepository(AppointmentRepository Repository) {
		this.Repository = Repository;
		repository = Repository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		Repository.deleteAll();
	}

	@Override
	public List<User> getUsersOfAppointment(int id) {
		return Repository.getUsersOfAppointment(id);
	}

	@Override
	public boolean checkAppointmentExist(String name) {
		return Repository.checkAppointmentExist(name);
	}

	@Override
	public Appointment getApmByName(String name) {
		return Repository.getApmByName(name);
	}

	@Override
	public void updateAppointment(Appointment apm,boolean changeUsers) {
		Repository.updateAppointment(apm,changeUsers);
	}

	@Override
	public List<Appointment> getApmsByStatus(String status) {
		return Repository.getApmsByStatus(status);
	}
}
