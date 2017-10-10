package capgemini.webappdemo.repository.Employee;


import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;


@Repository
@Transactional
public class EmployeeRepositoryImpl extends EntityRepositoryImpl<Employee> implements EmployeeRepository {
	
	public EmployeeRepositoryImpl() {
		super(Employee.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Employee";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}

	@Override
	public List<Employee> getEmployeesByManagerId(int manager_id) {
		Session session = getSession();

		String strQuery = "from Employee e where e.manager_id = :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("id",manager_id);
		List<Employee> result = query.list();
		return  result;
	}
}
