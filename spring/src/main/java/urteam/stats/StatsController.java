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
import urteam.sport.SportService;
import urteam.user.User;
import urteam.user.UserComponent;
import urteam.user.UserRepository;

@Controller
public class StatsController {
	
	
	
	@Autowired
	SportService sportService;

	@Autowired
	StatsRepository statsRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommunityRepository communityRepository;
	
	@Autowired
	private UserComponent userComponent;

//	@RequestMapping("/addstats/{nickname}")
//	public String addUserStats(@PathVariable String nickname, @RequestParam String sport, @RequestParam String date,
//			@RequestParam double sesionTime , Model model, HttpServletRequest request) {
//		User user = userRepository.findByNickname(nickname);			
//		Stat statsDos = new Stat();
//	    statsDos.setDate(date);
//	    statsDos.setTotalSesionTime(sesionTime);
//	    
//	    if(user.containsUserSport(sport)){
//	    	user.getUserSportsList().get(user.userSportPosition(sport)).addSportStats(statsDos);
//	    }else{
//	    	UserSportStats userSport = new UserSportStats();
//	    	userSport.setSportName(sport);
//	    	userSport.addSportStats(statsDos);
//	    	user.addUserSportsList(userSport);
//	    }
//		user.setScore(String.valueOf(computeUserScore(user)));
//	    userRepository.save(user);
//		
//		
//		if ((userComponent.isLoggedUser())) {
//			long idUser = userComponent.getLoggedUser().getId();
//			User userLogged = userRepository.findOne(idUser);
//			model.addAttribute("user", userLogged);
//			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
//				model.addAttribute("logged", true);
//			}
//			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
//			return "redirect:/user/{nickname}";
//		} else {
//			return "redirect:/user/{nickname}";
//		}
//		
//	}
//
//	public double computeUserScore(User user) {
//		
//		double totalScore = 0;
//
//		List<UserSportStats> userSport = user.getUserSportsList();
//
//		for (UserSportStats u : userSport) {
//			Sport sport = sportService.getSport(u.getSportName());
//			for(Stat s: u.getSportStats()){
//				totalScore += sport.getMultiplicator() * s.getTotalSesionTime() * 100;
//			}
//		}
//		return totalScore;
//	}
//	
//	public int computeUserLevel(User user){
//		
//		double userScore = Double.parseDouble(user.getScore());
//		
//		return (int) (userScore/1000); 
//			
//	}
	
	
//	public int computeUserBarLevel(User user){
//		
//		double userScore =  Double.parseDouble(user.getScore());
//		
//		int userbarlevel =((int)userScore%1000)/10;
//		
//		return userbarlevel;
//	}

}
