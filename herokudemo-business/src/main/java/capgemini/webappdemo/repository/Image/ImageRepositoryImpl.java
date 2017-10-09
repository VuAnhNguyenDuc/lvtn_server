package capgemini.webappdemo.repository.Image;


import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ImageRepositoryImpl extends EntityRepositoryImpl<Image> implements ImageRepository {
	
	public ImageRepositoryImpl() {
		super(Image.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(){
		Session session = getSession();
		
		String stringQuery = "DELETE FROM Image";
		Query query = session.createQuery(stringQuery);
		query.executeUpdate();
	}
}
