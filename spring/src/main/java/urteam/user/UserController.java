package urteam.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import urteam.community.CommunityRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@RequestMapping("/userprofile/{id}")
	public String userProfile(Model model, @PathVariable Long id){
		User user = userRepository.findOne(id);
		model.addAttribute("user",user);
		return "user";
	}
}
