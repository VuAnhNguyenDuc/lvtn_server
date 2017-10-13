package capgemini.webappdemo.service.UserAppointmentView;


import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.repository.User.UserRepository;
import capgemini.webappdemo.repository.UserAppointmentView.UserAppointmentViewRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;


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

	@Override
	public List<UserAppointmentView> getAppointmentsByMonth(int month, int year, int id, boolean isCreated) throws ParseException {
		return Repository.getAppointmentsByMonth(month, year, id, isCreated);
	}

	@Override
	public List<UserAppointmentView> getAppointmentsByYear(int year, int id, boolean isCreated) throws ParseException {
		return Repository.getAppointmentsByYear(year, id, isCreated);
	}


}
