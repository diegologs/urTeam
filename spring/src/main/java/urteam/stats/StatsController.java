package urteam.stats;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urteam.community.CommunityRepository;
import urteam.user.UserRepository;

@Controller
public class StatsController {
	
	@Autowired
	StatsRepository statsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommunityRepository communityRepository;
	
	
	
	@RequestMapping("/add-user-stats/{userId}/{sport}/{date}/{sesionTime}")
	public String addUserStats(Model model, @RequestParam String userId,
			@RequestParam String sport, @RequestParam Date date,@RequestParam float sesionTime) {
				return null;
	}

}
