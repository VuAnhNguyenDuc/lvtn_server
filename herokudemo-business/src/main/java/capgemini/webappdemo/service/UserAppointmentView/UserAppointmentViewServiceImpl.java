package capgemini.webappdemo.service.UserAppointmentView;


import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.User.UserRepository;
import capgemini.webappdemo.repository.UserAppointmentView.UserAppointmentViewRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserAppointmentViewServiceImpl extends EntityServiceImpl<UserAppointmentView> implements UserAppointmentViewService {
	
	private UserAppointmentViewRepository Repository;
	@Autowired
	public void setNameRepository(UserAppointmentViewRepository Repository) {
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
