package urteam.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* Este servicio se usará para incluir la funcionalidad que sea 
 * usada desde el BookRestController y el BookWebController
 */
@Service
public class CommunityService {

	@Autowired
	private CommunityRepository repository;

	public Community findOne(long id) {
		return repository.findOne(id);
	}

	public List<Community> findAll() {
		return repository.findAll();
	}

	public void save(Community community) {
		repository.save(community);
	}

	public void delete(long id) {
		repository.delete(id);
	}
}