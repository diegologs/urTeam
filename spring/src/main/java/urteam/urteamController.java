package urteam;

import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urteam.event.*;
import urteam.community.*;
import urteam.user.*;

@Controller
public class urteamController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private CommunityRepository communityRepo;

	@PostConstruct
	public void init() {
		
		
		for (int i = 0; i < 10; i++) {
			String name = "Usuario" + i;
			String surname = "apellido" + i;
			String nickname = "user" + surname.substring(1, 3) + i;
			String password = "123456";
			String email = name + surname + i + "@urteam.com";
			userRepo.save(new User(name, surname, nickname, password, email));
		}

		for (int i = 0; i < 10; i++) {
			String name = String.valueOf(i);
			String sport = String.valueOf(i);
			double price = i;
			String info = String.valueOf(i);
			String place = String.valueOf(i);
			Date start_date = new Date(i);
			Date end_date = new Date(i);
			eventRepo.save(new Event(name, sport, price, info, place, start_date, end_date));
		}

		for (int i = 0; i < 10; i++) {
			String name = String.valueOf(i);
			String info = String.valueOf(i);
			communityRepo.save(new Community(name, info, "Running"));
		}
	}

	@RequestMapping("/")
	public String index(Model model) {

		return "index";
	}
	
	@RequestMapping("/adminPanel/edit")
	public String edit(Model model,String action) {
		model.addAttribute("edit_Section",true);
		return "controlPanel-base";
	}
	
	@RequestMapping("/adminPanel/changePass")
	public String changePass(Model model,String action) {
		model.addAttribute("changePass_Section",true);
		return "controlPanel-base";
	}
	
	@RequestMapping("/adminPanel/manageEvents")
	public String manageEvents(Model model,String action) {
		model.addAttribute("manageEvents_Section",true);
		List<Event> events = eventRepo.findAll();
		model.addAttribute("events",events);
		return "controlPanel-base";
	}
	
	@RequestMapping("/adminPanel/manageUsers")
	public String manageUsers(Model model,String action) {
		model.addAttribute("manageUsers_Section",true);
		List<User> users = userRepo.findAll();
		model.addAttribute("users",users);
		return "controlPanel-base";
	}
	
	@RequestMapping("/adminPanel/manageGroups")
	public String manageGroups(Model model,String action) {
		model.addAttribute("manageGroups_Section",true);
		List<Community> communities = communityRepo.findAll();
		model.addAttribute("communities",communities);
		return "controlPanel-base";
	}

}
