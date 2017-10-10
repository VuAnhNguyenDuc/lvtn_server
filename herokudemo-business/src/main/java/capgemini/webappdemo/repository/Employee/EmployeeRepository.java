package capgemini.webappdemo.repository.Employee;

import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.repository.EntityRepository;

import java.util.List;


public interface EmployeeRepository extends EntityRepository<Employee> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<Employee> getEmployeesByManagerId(int manager_id);
	
}
