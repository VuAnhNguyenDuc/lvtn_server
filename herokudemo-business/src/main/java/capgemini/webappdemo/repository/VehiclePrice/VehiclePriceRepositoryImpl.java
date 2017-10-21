package capgemini.webappdemo.repository.VehiclePrice;


import capgemini.webappdemo.domain.VehiclePrice;
import capgemini.webappdemo.repository.EntityRepositoryImpl;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class VehiclePriceRepositoryImpl extends EntityRepositoryImpl<VehiclePrice> implements VehiclePriceRepository {
	
	public VehiclePriceRepositoryImpl() {
		super(VehiclePrice.class);
	}


	@Override
	public double getValue(String key) {
		Session session = getSession();

		String strQuery = "from VehiclePrice vp where vp.key = :key";
		Query query = session.createQuery(strQuery);
		query.setParameter("key",key);
		List<VehiclePrice> vps = query.list();
		if(vps.size() == 1){
			return vps.get(0).getValue();
		}
		return 0;
	}
}
