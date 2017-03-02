package urteam;

import java.sql.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public void init (){
		for (int i = 0; i < 10; i++) {
			String name = String.valueOf(i);
			String surname = String.valueOf(i);
			userRepo.save(new User(name, surname));
		}
		
		for (int i = 0; i < 10; i++) {
			String name = String.valueOf(i);
			String sport = String.valueOf(i);
			double price= i;
			String info = String.valueOf(i);
			String place = String.valueOf(i);
			Date start_date = new Date(i);
			Date end_date = new Date(i);
			eventRepo.save(new Event(name, sport, price, info, place, start_date, end_date));
		}

		for (int i = 0; i < 10; i++) {
			String name = String.valueOf(i);
			String info = String.valueOf(i);
			communityRepo.save(new Community(name, info));
		}
	}
	
	@RequestMapping("/")
	public String index(Model model) {

		

		return "index";
	}

}
