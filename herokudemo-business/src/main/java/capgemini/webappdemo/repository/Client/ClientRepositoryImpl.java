package capgemini.webappdemo.repository.Client;


import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Client;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ClientRepositoryImpl extends EntityRepositoryImpl<Client> implements ClientRepository {
	
	public ClientRepositoryImpl() {
		super(Admin.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Client";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}

	@Override
	public Client checkClientByName(String name) {
		Session session = getSession();

		String strQuery = "from Client c where c.name = :name";
		Query query = session.createQuery(strQuery);
		query.setParameter("name",name);
		if(query.list().size() > 0){
			return (Client) query.list().get(0);
		}
		return null;
	}
}
