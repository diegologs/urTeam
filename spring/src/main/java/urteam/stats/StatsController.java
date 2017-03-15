package urteam.stats;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import urteam.user.UserComponent;
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
	
	@Autowired
	private UserComponent userComponent;

	@RequestMapping("/add-user-stats/{id}")
	public String addUserStats(@PathVariable long id, @RequestParam String sport, @RequestParam String date,
			@RequestParam double sesionTime , Model model, HttpServletRequest request) {
		User user = userRepository.findOne(id);
		Stats newStats = new Stats();
		newStats.setDate(date);
		newStats.setTotalSesionTime(sesionTime);
		Sport s = new Sport();
		s = sportRepository.findByName(sport);
		newStats.setSport(s);
		user.addStat(newStats);
		user.setScore(String.valueOf(computeUserScore(user)));
		userRepository.save(user);
		
		
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepository.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/userprofile/{id}";
		} else {
			return "redirect:/userprofile/{id}";
		}
		
	}

	public double computeUserScore(User user) {
		
		double totalScore = Double.parseDouble(user.getScore());

		List<Stats> stats = user.getSportStats();

		for (Stats s : stats) {
			totalScore += s.getSport().getMultiplicator() * s.getTotalSesionTime() * 100;
		}
		return totalScore;
		
	}
	
	public int computeUserLevel(User user){
		
		double userScore = Double.parseDouble(user.getScore());
		
		return (int) (userScore/1000); 
			
	}
	
	
	public int computeUserBarLevel(User user){
		
		double userScore =  Double.parseDouble(user.getScore());
		
		int userbarlevel =((int)userScore%1000)/10;
		
		return userbarlevel;
	}

}
