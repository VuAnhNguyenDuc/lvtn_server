package capgemini.webappdemo.repository.Vehicle;


import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
	public boolean checkExist(String name, int id) {
		Session session = getSession();

		String strQuery = "from Vehicle v where v.name = :name and v.id != :id";
		Query query = session.createQuery(strQuery);
		query.setParameter("name",name);
		query.setParameter("id",id);
		if(query.list().size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<Vehicle> getActiveVehicles() {
		Session session = getSession();

		String strQuery = "from Vehicle v where v.status = 1";
		Query query = session.createQuery(strQuery);
		return query.list();
	}

	@Override
	public Vehicle getVehicleByName(String name) {
		Session session = getSession();

		String strQuery = "from Vehicle v where v.name = :name";
		Query query = session.createQuery(strQuery);
		query.setParameter("name",name);
		if(query.list().size() > 0){
			return (Vehicle) query.list().get(0);
		} else{
			return null;
		}
	}
}
