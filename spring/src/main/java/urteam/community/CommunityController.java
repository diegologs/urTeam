package urteam.community;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import urteam.user.User;
import urteam.user.UserComponent;
import urteam.user.UserRepository;

@Controller
public class CommunityController{
	
	@Autowired
	private urteamController urteam;
	
	@Autowired
	private CommunityRepository communityRepo;	
	
	@Autowired
	private NewsRepository newsRepo;	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserComponent userComponent;
	
	@RequestMapping("/groups")
	public String getGroups(Model model, HttpServletRequest request) {
		
		Page<Community> groups = communityRepo.findAll(new PageRequest(0,3));

		model.addAttribute("communitys", groups);
		model.addAttribute("groups_active", true);

		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "groups";
		} else {
			return "groups";
		}
	}
	
	@RequestMapping("/sortGroupByName")
	public String sortGroupByName(Model model, @RequestParam String name) {
		
		
		
		List<Community> groups = communityRepo.findByName(name);

			
		model.addAttribute("communitys", groups);
		model.addAttribute("groups_active", true);

		return "groups";
	}
	
	
	@RequestMapping("/group/{id}")
	public String editGroup(Model model, HttpServletRequest request,@PathVariable long id) {
		
		Community community = communityRepo.findOne(id);
		
		List<User> users = userRepo.findAll();    
	    
	    User user = users.get(0);
	    
	    if(user.getCommunityList().contains(community)){
	      
	    	model.addAttribute("following", true);
	      
	    }else{
	    
	    	model.addAttribute("following", false);
	    
	    }
	    model.addAttribute("numberOfMembers",community.getCommunityUsers().size());
	    model.addAttribute("members",community.getCommunityUsers());
		model.addAttribute("community", community);
		model.addAttribute("communityGallery", community.getCommunityImages());
		
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "group";
		} else {
			return "group";
		}
		
		
		
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
	public String groupEdited(Model model, @PathVariable long id,  @RequestParam String info, HttpServletRequest request) {
		
		Community community = communityRepo.findOne(id);
		community.setInfo(info);
		
		communityRepo.save(community);
		model.addAttribute("community", community);
		
		
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}

	}

	
	@RequestMapping("/group/{id}/addNews")
	public String groupEdited(Model model, @PathVariable long id, @RequestParam String title, @RequestParam String text, HttpServletRequest request) {
		
		Community community = communityRepo.findOne(id);
		communityRepo.save(community);		
		News news = new News(title, text);
		community.getNews().add(news);
		newsRepo.save(news);
		model.addAttribute("community", community);

		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}

	}
	
	
	@RequestMapping("/group/{id}/addImage")
	public String addImage(Model model, @PathVariable long id, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws ParseException {
		
		
		Community community  = communityRepo.findOne(id);
		
		//Filename formater
		SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
		Date date = new Date();
		
		
		String filename = "imageingallery-"+formater.format(date);
		
		if(urteam.uploadImageFile(model, file,filename,ConstantsUrTeam.COMMUNITY_IMGS, community.getCommunityId())){
			community.addImage(filename);
		}
		
		communityRepo.save(community);
		
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}
		
		

	}	
	
	
	@RequestMapping("addGroup")
	public String newEvent() {

		return "addGroup";

	}
	
	@RequestMapping("/GroupAdded")
	public String groupAdded(Model model, Community community, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws ParseException {

		//Filename formater
		SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
		Date date = new Date();
		
		//EventId generator
		SimpleDateFormat communityIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
		String communitytId = communityIdFormater.format(date);
		community.setCommunityId(communitytId);
		String filename = "avatar-"+formater.format(date);
	
		
		if(urteam.uploadImageFile(model, file,filename,ConstantsUrTeam.COMMUNITY_AVATAR, community.getCommunityId())){
			community.setMain_photo(filename);
		}
		communityRepo.save(community);
		
		model.addAttribute("groups_active", true);
		
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:groups";
		} else {
			return "redirect:groups";
		}
		
	}
	
	
	@RequestMapping("/group/{id}/follow")
	  public String follow(Model model, @PathVariable long id, HttpServletRequest request) {
	    
	    Community community = communityRepo.findOne(id);
	    
	    List<User> users = userRepo.findAll();    
	    
	    User user = users.get(0);
	    
	    if(user.getCommunityList().contains(community)){
	      
	      user.removeCommunity(community);
	      
	    }else{
	    
	      user.addCommunity(community);
	      model.addAttribute("following", true);
	    
	    }
	    
	    userRepo.save(user);
	    
	    model.addAttribute("community", community);
	    
	    
	    if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}
	    

	  }
	
	
}