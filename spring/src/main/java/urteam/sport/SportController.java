package urteam.sport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SportController {

	@Autowired
	SportService sportService;

	public List<Sport> getSportList() {
		return sportService.getSports();
	}

}
