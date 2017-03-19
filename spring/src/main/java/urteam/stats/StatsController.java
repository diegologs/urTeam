package urteam.stats;

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
import urteam.user.UserSport;

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

	@RequestMapping("/addstats/{nickname}")
	public String addUserStats(@PathVariable String nickname, @RequestParam String sport, @RequestParam String date,
			@RequestParam double sesionTime , Model model, HttpServletRequest request) {
		User user = userRepository.findByNickname(nickname);
//		Stats newStats = new Stats();
//		newStats.setDate(date);
//		newStats.setTotalSesionTime(sesionTime);
//		newStats.setUserId(user.getId());
//		newStats.setSport(sport);
//		user.addStat(newStats);
//		user.setScore(String.valueOf(computeUserScore(user)));
//		userRepository.save(user);
		
		
		Stats_dos statsDos = new Stats_dos();
	    statsDos.setDate(date);
	    statsDos.setTotalSesionTime(sesionTime);
	    
	    if(user.containsUserSport(sport)){
	    	user.getUserSportsList().get(user.userSportPosition(sport)).addSportStats(statsDos);
	    }else{
	    	UserSport userSport = new UserSport();
	    	userSport.setSportName(sport);
	    	userSport.addSportStats(statsDos);
	    	user.addUserSportsList(userSport);
	    }
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
			return "redirect:/user/{nickname}";
		} else {
			return "redirect:/user/{nickname}";
		}
		
	}

	public double computeUserScore(User user) {
		
		double totalScore = 0;

		List<UserSport> userSport = user.getUserSportsList();

		for (UserSport u : userSport) {
			Sport sport = sportRepository.findByName(u.getSportName());
			for(Stats_dos s: u.getSportStats()){
				totalScore += sport.getMultiplicator() * s.getTotalSesionTime() * 100;
			}
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
