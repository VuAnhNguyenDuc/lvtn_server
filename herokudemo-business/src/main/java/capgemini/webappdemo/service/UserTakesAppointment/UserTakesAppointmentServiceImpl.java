package capgemini.webappdemo.service.UserTakesAppointment;


import capgemini.webappdemo.domain.UserTakesAppointment;
import capgemini.webappdemo.repository.UserTakesAppointment.UserTakesAppointmentRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserTakesAppointmentServiceImpl extends EntityServiceImpl<UserTakesAppointment> implements UserTakesAppointmentService {
	
	private UserTakesAppointmentRepository Repository;
	@Autowired
	public void setRepository(UserTakesAppointmentRepository Repository) {
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
}
