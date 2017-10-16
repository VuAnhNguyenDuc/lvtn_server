package capgemini.webappdemo.service.Manager;


import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.repository.Image.ImageRepository;
import capgemini.webappdemo.repository.Manager.ManagerRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ManagerServiceImpl extends EntityServiceImpl<Manager> implements ManagerService {
	
	private ManagerRepository Repository;
	@Autowired
	public void setRepository(ManagerRepository Repository) {
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
	public void assignAppointmentToUser(int apmID, int userID) {
		Repository.assignAppointmentToUser(apmID,userID);
	}

	@Override
	public int getCreatedAppointments(int mngID) {
		return Repository.getCreatedAppointments(mngID);
	}
}
