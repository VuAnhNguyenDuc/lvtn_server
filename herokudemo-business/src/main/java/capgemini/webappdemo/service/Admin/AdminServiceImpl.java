package capgemini.webappdemo.service.Admin;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.repository.Admin.AdminRepository;
import capgemini.webappdemo.repository.User.UserRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AdminServiceImpl extends EntityServiceImpl<Admin> implements AdminService {
	
	private AdminRepository adminRepository;
	@Autowired
	public void setRepository(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
		repository = adminRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		adminRepository.deleteAll();
	}

	@Override
	public Admin login(String username, String password) {
		return adminRepository.login(username,password);
	}
}
