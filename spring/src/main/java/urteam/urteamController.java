package urteam;

import java.sql.Date;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import urteam.event.Event;
import urteam.event.EventRepository;

import urteam.community.*;
import urteam.event.*;
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
			eventRepo.save(new Event());
		}

		for (int i = 0; i < 10; i++) {
			communityRepo.save(new Community());
		}
	}

	

}
