package capgemini.webappdemo.repository.SpecialPlace;

import capgemini.webappdemo.domain.SpecialPlace;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class SpecialPlaceRepositoryImpl extends EntityRepositoryImpl<SpecialPlace> implements SpecialPlaceRepository {
	
	public SpecialPlaceRepositoryImpl() {
		super(SpecialPlace.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM SpecialPlace";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}


	@Override
	public SpecialPlace getPlaceByName(String name) {
		Session session = getSession();

		String strQuery = "from SpecialPlace v where v.name = :name";
		Query query = session.createQuery(strQuery);
		query.setParameter("name",name);
		if(query.list().size() > 0){
			return (SpecialPlace) query.list().get(0);
		} else{
			return null;
		}
	}
}
