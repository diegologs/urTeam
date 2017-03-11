package urteam.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/userprofile/{id}")
	public String userProfile(Model model, @PathVariable Long id){
		User user = userRepository.findOne(id);
		model.addAttribute("user",user);
		model.addAttribute("following",user.getFollowing());
		model.addAttribute("communities",user.getCommunityList());
		model.addAttribute("numberOfFollowers", user.getNumberOfFollower());
		return "user";
	}
}
