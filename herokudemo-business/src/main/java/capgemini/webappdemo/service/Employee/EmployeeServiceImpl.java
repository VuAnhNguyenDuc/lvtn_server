package capgemini.webappdemo.service.Employee;


import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.repository.Detail.DetailRepository;
import capgemini.webappdemo.repository.Employee.EmployeeRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class EmployeeServiceImpl extends EntityServiceImpl<Employee> implements EmployeeService {
	
	private EmployeeRepository Repository;
	@Autowired
	public void setRepository(EmployeeRepository Repository) {
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
	public List<Employee> getEmployeesByManagerId(int manager_id) {
		return Repository.getEmployeesByManagerId(manager_id);
	}
}
