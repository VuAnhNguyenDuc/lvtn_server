package capgemini.webappdemo.repository;


import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import capgemini.webappdemo.domain.Name;


@Repository
@Transactional
public class NameRepositoryImpl extends EntityRepositoryImpl<Name> implements NameRepository {
	
	public NameRepositoryImpl() {
		super(Name.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Name";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
