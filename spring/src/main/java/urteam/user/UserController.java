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
	
	@RequestMapping("/user/{id}")
	public String user(Model model, @PathVariable long id){
		model.addAttribute("user", userRepository.findOne(id));
		return "user";
	}
}
