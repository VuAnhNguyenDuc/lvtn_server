package capgemini.webappdemo.repository.Employee;


import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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
}
