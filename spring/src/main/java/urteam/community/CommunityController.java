package urteam.community;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import urteam.event.Event;
import urteam.event.EventRepository;

@Controller
public class CommunityController{
	
	@Autowired
	private CommunityRepository communityRepo;	
	
	@RequestMapping("/groups")
	public String eventos(Model model) {

		model.addAttribute("communitys", communityRepo.findAll());

		return "groups";
	}
	
	@RequestMapping("/group/{id}")
	public String showEvent(Model model, @PathVariable long id) {
		
		Community community = communityRepo.findOne(id);

		model.addAttribute("community", community);

		return "group";
	}
	
	@RequestMapping("addGroup")
	public String newEvent() {

		return "addGroup";

	}
	
	@RequestMapping("GroupAdded")
	public String groupAdded(Model model, Community community) {
		
		communityRepo.save(community);
		model.addAttribute("communitys", communityRepo.findAll());
		return "groups";

	}
}