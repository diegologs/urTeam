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

	public Sport getSport(String sportName) {
		return sportRepository.findByName(sportName);
	}

	public Sport createSport(Sport newSport) {
		sportRepository.save(newSport);
		return newSport;
	}
	
	public Sport updateSport(String name,Sport sport){
		Sport sportToUpdate = sportRepository.findByName(name);
		if(sportToUpdate != null){
			sportRepository.save(sport);
			return sport;
		}else{
			return null;
		}
	}
	
	public void deleteSport(String sportName) throws Exception{
		Sport sport = sportRepository.findByName(sportName);
		if(sport != null){
			sportRepository.delete(sport.getId());
		} else{
			throw new Exception();
		}
	}
}
