package urteam.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import urteam.community.Community;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/userprofile/{id}")
	public String userProfile(Model model, @PathVariable Long id){
		User user = userRepository.findOne(id);
		model.addAttribute("user",user);
		List<User> friends= user.getFollowing(); 
		List<Community> communities = user.getCommunityList();
		model.addAttribute("following",friends);
		model.addAttribute("communities",communities);
		model.addAttribute("members",communities.size());
		model.addAttribute("numberOfFollowers", user.getNumberOfFollower());
		return "user";
	}
}
