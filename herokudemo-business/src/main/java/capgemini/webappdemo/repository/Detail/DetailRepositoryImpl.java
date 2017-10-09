package capgemini.webappdemo.repository.Detail;


import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class DetailRepositoryImpl extends EntityRepositoryImpl<Detail> implements DetailRepository {
	
	public DetailRepositoryImpl() {
		super(Detail.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Detail";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
