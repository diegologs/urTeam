package urteam.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import urteam.sport.SportController;
import urteam.sport.SportRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SportController sportController;

	@Autowired
	private urteamController urteamController;
	
	@Autowired
	private UserComponent userComponent;
	

	@RequestMapping("/newUser")
	public String eventAdded(Model model, User user, @RequestParam String password) throws ParseException {
		Date date = new Date();
		SimpleDateFormat userIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
		String generatedId = userIdFormater.format(date);
		user.setGeneratedId(generatedId);
		user.setPasswordHash(password);
		userRepository.save(user);
		return "redirect:/events";
	}
	
//	@RequestMapping("/{nickname}")
//	public String userProf(Model model, @PathVariable String nickname) {
//		User user = userRepository.findByNickname(nickname);
//		model.addAttribute("user", user);
//		List<User> friends = user.getFollowing();
//		List<Community> communities = user.getCommunityList();
//		model.addAttribute("following", friends);
//		model.addAttribute("communities", communities);
//		model.addAttribute("members", communities.size());
//		model.addAttribute("numberOfFollowers", user.getNumberOfFollower());
//		
//		model.addAttribute("user_active", true);
//		model.addAttribute("logged", true);
//		model.addAttribute("sportList",sportController.getSportList());
//		model.addAttribute("stats",user.getSportStats());
//		return "user";
//	}

	@RequestMapping("/userprofile/{id}")
	public String userProfile(Model model, @PathVariable Long id, HttpServletRequest request) {
		
		User me = userRepository.findOne(userComponent.getLoggedUser().getId());
		User user = userRepository.findOne(id);		
		model.addAttribute("userpage", user);
		
		List<User> friends = user.getFollowing();
		List<Community> communities = user.getCommunityList();
		model.addAttribute("following", friends);
		model.addAttribute("communities", communities);
		model.addAttribute("members", communities.size());
		model.addAttribute("numberOfFollowers", user.getNumberOfFollower());
		
		model.addAttribute("user_active", true);
		
		model.addAttribute("sportList",sportController.getSportList());
		model.addAttribute("stats",user.getSportStats());
		
		model.addAttribute("buttonfollowing", me.getId()!=user.getId());
		if(me.getFollowing().contains(user)){
		      
	    	model.addAttribute("isfollowed", true);
	      
	    }else{
	    
	    	model.addAttribute("isfollowed", false);
	    
	    }
		
		if ((userComponent.isLoggedUser())) {
			User userLogged = userRepository.findOne(me.getId());
			model.addAttribute("user", me);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "user";
		} else {
			return "user";
		}		
	}

	@RequestMapping("/userprofile/{id}/edit")
	public String userProfileEdit(Model model, @PathVariable long id, @RequestParam String username,
			@RequestParam String surname, @RequestParam String email, @RequestParam String bio,
			@RequestParam String city, @RequestParam String country, @RequestParam("file") MultipartFile file)
			throws ParseException {
		User editedUser = userRepository.findOne(id);

		editedUser.setUserName(username);
		editedUser.setSurname(surname);
		editedUser.setEmail(email);
		editedUser.setBio(bio);
		editedUser.setCity(city);
		editedUser.setCountry(country);

		if (file != null) {
			String filename = "avatar-" + editedUser.getGeneratedId();

			if (urteamController.uploadImageFile(model, file, filename, ConstantsUrTeam.USER_AVATAR,
					editedUser.getGeneratedId())) {
				editedUser.setAvatar(filename);
			}
		}
		userRepository.save(editedUser);

		return "redirect:/userprofile/{id}";
	}

	@RequestMapping("/userprofile/{id}/follow")
    public String follow(Model model, @PathVariable long id, HttpServletRequest request) {
      
      User user = userRepository.findOne(id);   
      
      User me = userRepository.findOne(userComponent.getLoggedUser().getId());
      
      if(me.getFollowing().contains(user)){
        
        me.getFollowing().remove(user);
        
      }else{
        me.getFollowing().add(user);
        model.addAttribute("buttonfollowing", true);
      
      }
      
      userRepository.save(me);
      
      model.addAttribute("user", me);
      
      
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
}
