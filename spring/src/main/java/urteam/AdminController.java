package urteam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import urteam.community.Community;
import urteam.community.CommunityRepository;
import urteam.event.Event;
import urteam.event.EventRepository;
import urteam.user.User;
import urteam.user.UserRepository;

@Controller
public class AdminController {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommunityRepository communityRepository;
	
	@RequestMapping("/adminPanel")
	public String adminRoot(Model model) {
		model.addAttribute("edit_Section", true);
		return "controlPanel-base";
	}

	@RequestMapping("/adminPanel/edit")
	public String edit(Model model, String action) {
		model.addAttribute("edit_Section", true);
		return "controlPanel-base";
	}

	@RequestMapping("/adminPanel/changePass")
	public String changePass(Model model, String action) {
		model.addAttribute("changePass_Section", true);
		return "controlPanel-base";
	}

	@RequestMapping("/adminPanel/manageEvents")
	public String manageEvents(Model model, String action) {
		model.addAttribute("manageEvents_Section", true);
		List<Event> events = eventRepository.findAll();
		model.addAttribute("events", events);
		return "controlPanel-base";
	}
	
	@RequestMapping("/adminPanel/manageEvents/delete/{id}")
	public String manageEventsDelete(@PathVariable long id) {
		Event event = eventRepository.findOne(id);
		eventRepository.delete(event);
		return "redirect:/adminPanel/manageEvents";
	}

	@RequestMapping("/adminPanel/manageUsers")
	public String manageUsers(Model model, String action) {
		model.addAttribute("manageUsers_Section", true);
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "controlPanel-base";
	}
	
	@RequestMapping("/adminPanel/manageUsers/delete/{id}")
	public String manageUsersDelete(@PathVariable long id) {
		User user = userRepository.findOne(id);
		userRepository.delete(user);
		return "redirect:/adminPanel/manageUsers";
	}

	@RequestMapping("/adminPanel/manageGroups")
	public String manageGroups(Model model, String action) {
		model.addAttribute("manageGroups_Section", true);
		List<Community> communities = communityRepository.findAll();
		model.addAttribute("communities", communities);
		return "controlPanel-base";
	}
	
	@RequestMapping("/adminPanel/manageGroups/delete/{id}")
	public String manageGroupsDelete(@PathVariable long id) {
		Community community = communityRepository.findOne(id);
		communityRepository.delete(community);
		return "redirect:/adminPanel/manageGroups";
	}

}
