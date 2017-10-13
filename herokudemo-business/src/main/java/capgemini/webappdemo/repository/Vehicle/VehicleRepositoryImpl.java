package capgemini.webappdemo.repository.Vehicle;


import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class VehicleRepositoryImpl extends EntityRepositoryImpl<Vehicle> implements VehicleRepository {
	
	public VehicleRepositoryImpl() {
		super(Vehicle.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Vehicle";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}

	@Override
	public boolean checkExist(String name) {
		Session session = getSession();

		String strQuery = "from Vehicle v where v.name = :name";
		Query query = session.createQuery(strQuery);
		query.setParameter("name",name);
		if(query.list().size() > 0){
			return true;
		}
		return false;
	}
}
