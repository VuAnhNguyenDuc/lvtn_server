package capgemini.webappdemo.repository.Coordinate;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class CoordinateRepositoryImpl extends EntityRepositoryImpl<Coordinate> implements CoordinateRepository {
	
	public CoordinateRepositoryImpl() {
		super(Coordinate.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Coordinate";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
