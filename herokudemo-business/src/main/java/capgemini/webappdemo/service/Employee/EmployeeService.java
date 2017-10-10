package capgemini.webappdemo.service.Employee;

import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.service.EntityService;

import java.util.List;

public interface EmployeeService extends EntityService<Employee> {
	
	/**
	 * Our app has a need for a fast method for deleting names
	 */
	public void deleteAll();

	public List<Employee> getEmployeesByManagerId(int manager_id);
}
