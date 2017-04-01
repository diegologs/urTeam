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
import urteam.user.UserService;

@Controller
public class StatsController {

	@Autowired
	SportService sportService;

	@Autowired
	UserService userService;

	@Autowired
	StatsService statsService;

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	private UserComponent userComponent;

	@RequestMapping("/addstats/{nickname}")
	public String addUserStats(@PathVariable String nickname, @RequestParam String sport, @RequestParam String date,
			@RequestParam double sesionTime, Model model, HttpServletRequest request) {

		Stat newStat = new Stat();
		newStat.setDate(date);
		newStat.setTotalSesionTime(sesionTime);
		statsService.newUserStat(nickname, sport, newStat);

		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userService.findOne(idUser);
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
}
