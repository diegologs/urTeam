package urteam.stats;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urteam.community.CommunityRepository;
import urteam.sport.Sport;
import urteam.sport.SportRepository;
import urteam.user.User;
import urteam.user.UserRepository;

@Controller
public class StatsController {
	
	@Autowired
	SportRepository sportRepository;

	@Autowired
	StatsRepository statsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommunityRepository communityRepository;

	@RequestMapping("/add-user-stats/{id}")
	public String addUserStats(@PathVariable long id, @RequestParam String sport, @RequestParam String date,
			@RequestParam double sesionTime) {
		User user = userRepository.findOne(id);
		Stats newStats = new Stats();
		newStats.setDate(new Date());
		newStats.setTotalSesionTime(sesionTime);
		newStats.setSport(sportRepository.findByName(sport));
		user.addStat(newStats);
		userRepository.save(user);
		computeUserScore(id);
		return "/userprofile/{id}";
	}

	public void computeUserScore(long id) {
		User user = userRepository.getOne(id);
		double totalScore = 0;

		List<Stats> stats = user.getSportStats();

		for (Stats s : stats) {
			totalScore += s.getSport().getMultiplicator() * s.getTotalSesionTime() * 100;
		}
		user.setScore(String.valueOf(totalScore));
		userRepository.save(user);
	}

}
