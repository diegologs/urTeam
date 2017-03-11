package urteam.community;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.event.Event;
import urteam.event.EventRepository;
import urteam.news.News;
import urteam.news.NewsRepository;

@Controller
public class CommunityController{
	
	@Autowired
	private urteamController urteam;
	
	@Autowired
	private CommunityRepository communityRepo;	
	
	@Autowired
	private NewsRepository newsRepo;	
	
	@RequestMapping("/groups")
	public String eventos(Model model) {
		
		Page<Community> groups = communityRepo.findAll(new PageRequest(0,3));

		model.addAttribute("communitys", groups);
		model.addAttribute("groups_active", true);

		return "groups";
	}
	
	@RequestMapping("/sortGroupByName")
	public String sortGroupByName(Model model, @RequestParam String name) {
		
		
		
		List<Community> groups = communityRepo.findByName(name);

			
		model.addAttribute("communitys", groups);
		model.addAttribute("groups_active", true);

		return "groups";
	}
	
	
	@RequestMapping("/group/{id}")
	public String editGroup(Model model, @PathVariable long id) {
		
		Community community = communityRepo.findOne(id);

		model.addAttribute("community", community);
		
		

		return "group";
	}
	
	
	@RequestMapping("/sortGroupBySport")
	public String sortGroupBySport(Model model, @RequestParam String sport) {
		
		List<Community> community = communityRepo.findBySport(sport);

		model.addAttribute("communitys", community);
		model.addAttribute("groups_active", true);
		
		

		return "groups";
	}
	
	@RequestMapping(value="/moreGroups")
	public String moreAllShelf(Model model, @RequestParam int page){
		
		Page<Community> groups = communityRepo.findAll(new PageRequest(page,3));
		
		model.addAttribute("community", groups);
		
		return "listGroups";
	}
	
	
	@RequestMapping("/group/{id}/GroupEdited")
	public String groupEdited(Model model, @PathVariable long id,  @RequestParam String info) {
		
		Community community = communityRepo.findOne(id);
		community.setInfo(info);
		
		communityRepo.save(community);
		model.addAttribute("community", community);
		return "redirect:/group/{id}";

	}

	
	@RequestMapping("/group/{id}/addNews")
	public String groupEdited(Model model, @PathVariable long id, @RequestParam String title, @RequestParam String text) {
		
		Community community = communityRepo.findOne(id);
		communityRepo.save(community);
		
		
		
		
		News news = new News(title, text);
		community.getNews().add(news);
		
	
		
		newsRepo.save(news);
		model.addAttribute("community", community);
		
		
		return "redirect:/group/{id}";

	}
	
	
	@RequestMapping("/community/{id}/addImage")
	public String addImage(Model model, @PathVariable long id, @RequestParam("file") MultipartFile file) throws ParseException {
		
		
		Community community  = communityRepo.findOne(id);
		
		//Filename formater
		SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
		Date date = new Date();
		
		
		String filename = "imageingallery-"+formater.format(date);
		
		if(urteam.uploadImageFile(model, file,filename,ConstantsUrTeam.COMMUNITY_IMGS, community.getCommunityId())){
			community.addImage(filename);
		}
		
		communityRepo.save(community);
		return "redirect:/community/{id}";

	}

	
	
	
	@RequestMapping("addGroup")
	public String newEvent() {

		return "addGroup";

	}
	

	
	
	@RequestMapping("GroupAdded")
	public String groupAdded(Model model, Community community) {
		
		communityRepo.save(community);
		model.addAttribute("groups_active", true);
		return "redirect:groups";

	}
}