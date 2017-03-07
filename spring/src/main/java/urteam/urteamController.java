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
	
	@RequestMapping("/adminPanel/{action}")
	public String goTo(Model model, @PathVariable String action){
		switch(action){
		case "edit":
			model.addAttribute("edit_Section",true);
			break;
			
		case "changePass":
			model.addAttribute("changePass_Section",true);
			break;
		
		case  "manageEvents":
			model.addAttribute("manageEvents_Section",true);
			break;
			
		case  "manageUsers":
			model.addAttribute("manageUsers_Section",true);
			break;
			
		case  "manageGroups":
			model.addAttribute("manageGroups_Section",true);
			break;
			
		default : model.addAttribute("edit_Section",true);
		
		}
		return "controlPanel-base";
	}

}
