package urteam.sport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SportService {
	
	@Autowired
	private SportRepository sportRepository;

	public List<Sport> getSports() {
		return sportRepository.findAll();
	}
}
