package capgemini.webappdemo.service.User;


import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Name;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.NameRepository;
import capgemini.webappdemo.repository.User.UserRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl extends EntityServiceImpl<User> implements UserService {
	
	private UserRepository Repository;
	@Autowired
	public void setNameRepository(UserRepository Repository) {
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
	public User checkLogin(String username, String password) {
		return Repository.checkLogin(username,password);
	}

	@Override
	public String changePassword(int id, String newPassword) {
		return Repository.changePassword(id,newPassword);
	}

	@Override
	public String getUserType(int id) {
		return Repository.getUserType(id);
	}

	@Override
	public List<Appointment> getActiveAppointments(int id) {
		return Repository.getActiveAppointments(id);
	}

	@Override
	public List<Appointment> getAllAppointments(int id) {
		return Repository.getAllAppointments(id);
	}
}
