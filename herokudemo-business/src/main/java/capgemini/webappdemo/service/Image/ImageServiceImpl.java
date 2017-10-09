package capgemini.webappdemo.service.Image;


import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Image;
import capgemini.webappdemo.repository.Employee.EmployeeRepository;
import capgemini.webappdemo.repository.Image.ImageRepository;
import capgemini.webappdemo.service.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ImageServiceImpl extends EntityServiceImpl<Image> implements ImageService {
	
	private ImageRepository Repository;
	@Autowired
	public void setRepository(ImageRepository Repository) {
		this.Repository = Repository;
		repository = Repository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		Repository.deleteAll();
	}
}
