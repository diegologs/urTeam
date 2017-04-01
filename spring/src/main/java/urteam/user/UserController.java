package urteam.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.community.Community;
import urteam.event.Event;
import urteam.sport.SportController;
import urteam.sport.SportService;
import urteam.stats.StatsController;
import urteam.stats.StatsService;
import urteam.stats.UserSportStats;

@Controller
public class UserController {


	@Autowired
	private SportService sportService;

	@Autowired
	private urteamController urteamController;


	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StatsService statsService;

	@RequestMapping("/newUser")
	public String newUser(Model model, User user, @RequestParam String password) throws ParseException {
		user.setPasswordHash(password);
		userService.createNewUser(user);
		return "redirect:/";
	}

	@RequestMapping("/user/{nickname}")
	public String userProfile(Model model, @PathVariable String nickname, HttpServletRequest request) {

		User me = userService.findOne(userComponent.getLoggedUser().getId());
		User user = userService.getUser(nickname);
		model.addAttribute("userpage", user);
		Set<Map.Entry<String,UserSportStats>> stats = user.getSportStats().entrySet();

		List<User> friends = user.getFollowing();
		List<Community> communities = user.getCommunityList();
		List<Event> events = user.getEventList();
		model.addAttribute("userFollowing", friends);
		model.addAttribute("communities", communities);
		model.addAttribute("events", events);
		model.addAttribute("membersCommunity", communities.size());

		model.addAttribute("numberOfFollowers", user.getNumberOfFollower());
		model.addAttribute("sportList", sportService.getSports());
		model.addAttribute("userSportList",stats);
		model.addAttribute("level", statsService.computeUserLevel(user));
		model.addAttribute("progress", statsService.computeUserBarLevel(user));
		model.addAttribute("buttonfollowing", me.getId() != user.getId());
		if (me.getFollowing().contains(user)) {
			model.addAttribute("isfollowed", true);
		} else {
			model.addAttribute("isfollowed", false);
		}

		for (Event event : events) {
			model.addAttribute("eventFollowed", me.getEventList().contains(event));
		}
		for (Community community : communities) {
			model.addAttribute("communityFollowed", me.getCommunityList().contains(community));
		}

		if ((userComponent.isLoggedUser())) {
			User userLogged = userService.findOne(me.getId());
			model.addAttribute("user", me);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("user_active", true);
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "user";
		} else {
			return "user";
		}
	}

	@RequestMapping("/user/{nickname}/edit")
	public String userProfileEdit(Model model, @PathVariable String nickname, @RequestParam String username,
			@RequestParam String surname, @RequestParam String email, @RequestParam String bio,
			@RequestParam String city, @RequestParam String country, @RequestParam("file") MultipartFile file)
			throws ParseException {

		User editedUser = userService.getUser(nickname);
		User me = userService.findOne(userComponent.getLoggedUser().getId());

		if (editedUser.getId() == me.getId()) {

			editedUser.setUserName(username);
			editedUser.setSurname(surname);
			editedUser.setEmail(email);
			editedUser.setBio(bio);
			editedUser.setCity(city);
			editedUser.setCountry(country);
			userService.updateUserInfo(nickname, editedUser);
			
			if (file != null) {
//				String filename = "avatar-" + editedUser.getGeneratedId();
//				if (urteamController.uploadImageFile(model, file, filename, ConstantsUrTeam.USER_AVATAR,
//						editedUser.getGeneratedId())) {
//					editedUser.setAvatar(filename);
//				}
				userService.updateUserAvatar(nickname,file);
			}
			 
		}
		return "redirect:/user/{nickname}";
	}

	@RequestMapping("/user/{nickname}/follow")
	public String follow(Model model, @PathVariable String nickname, HttpServletRequest request) {
		User user = userService.getUser(nickname);
		User me = userService.findOne(userComponent.getLoggedUser().getId());
		userService.followUnfollow(me, nickname);
		model.addAttribute("buttonfollowing", me.getFollowing().contains(user));
		model.addAttribute("user", me);

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
