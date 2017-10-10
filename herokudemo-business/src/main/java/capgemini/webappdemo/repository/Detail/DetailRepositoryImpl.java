package capgemini.webappdemo.repository.Detail;


import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class DetailRepositoryImpl extends EntityRepositoryImpl<Detail> implements DetailRepository {
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");

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

	@Override
	public void start(int id) throws ParseException {
		Detail detail = get(id);
		detail.setStart_time(getDate());
		update(detail);
	}

	@Override
	public void end(int id) throws ParseException {
		Detail detail = get(id);
		detail.setEnd_time(getDate());
		update(detail);
	}

	@Override
	public void inputCost(int id, int price) {
		Detail detail = get(id);
		detail.setInput_cost(price);
		update(detail);
	}

	private Date getDate() throws ParseException {
		Date date = new Date();
		String strDate = dateFormat.format(date);
		Date result = dateFormat.parse(strDate);
		return result;
	}

}
