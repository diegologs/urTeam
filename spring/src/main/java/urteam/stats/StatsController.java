package urteam.stats;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urteam.community.CommunityRepository;
import urteam.sport.Sport;
import urteam.user.User;
import urteam.user.UserRepository;

@Controller
public class StatsController {
	
	@Autowired
	StatsRepository statsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommunityRepository communityRepository;
	
	
	
	@RequestMapping("/add-user-stats")
	public String addUserStats(Model model, @RequestParam long id,
			@RequestParam Sport sport, @RequestParam String date,@RequestParam double sesionTime) {
				User user = userRepository.getOne(id);
				Stats newStats = new Stats();
				newStats.setDate(new Date());
				newStats.setTotalSesionTime(sesionTime);
				newStats.setSport(sport);
				user.addStat(newStats);
				userRepository.save(user);
				return "";
	}
	
//	public double computeUser

}
