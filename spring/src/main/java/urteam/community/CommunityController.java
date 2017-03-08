package urteam.community;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String editGroup(Model model, @PathVariable long id) {
		
		Community community = communityRepo.findOne(id);

		model.addAttribute("community", community);

		return "group";
	}
	
	
	
	@RequestMapping("/group/{id}/GroupEdited")
	public String groupEdited(Model model, @PathVariable long id,  @RequestParam String info) {
		
		Community community = communityRepo.findOne(id);
		community.setInfo(info);
		
		communityRepo.save(community);
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