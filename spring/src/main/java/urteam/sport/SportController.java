package urteam.sport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SportController {
	
	@Autowired
	SportRepository sportRepository;
	
	
	
	public List<Sport> getSportList() {
		List<Sport> sportsList = new ArrayList<>();
		sportsList = sportRepository.findAll();
		return sportsList;
	}
	
	
	

}
