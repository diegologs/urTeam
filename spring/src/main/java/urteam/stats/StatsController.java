package urteam.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StatsController {
	
	@Autowired
	StatsRepository statsRepository;

}
