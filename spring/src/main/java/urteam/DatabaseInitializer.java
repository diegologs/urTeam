package urteam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import urteam.community.*;
import urteam.event.*;
import urteam.news.*;
import urteam.sport.*;
import urteam.user.*;

@Component
public class DatabaseInitializer {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private CommunityRepository communityRepo;
	
	@Autowired
	private NewsRepository newsRepo;
	
	@Autowired
	private SportRepository sportRepo;

	@PostConstruct
	public void init() throws ParseException {

		
		// Sample sports
		Sport sport1 = new Sport();
		sport1.setName("Running");
		sport1.setMultiplicator(0.75);
		
		Sport sport2 = new Sport();
		sport2.setName("Mountain Bike");
		sport2.setMultiplicator(0.95);
		
		Sport sport3 = new Sport();
		sport3.setName("Roller");
		sport3.setMultiplicator(0.95);
		
		sportRepo.save(sport1);
		sportRepo.save(sport2);
		sportRepo.save(sport3);
		
		// Sample users
		for (int i = 0; i < 5; i++) {
			String username = "name" + (i+1);
			String surname = "surname" + i;
			String nickname = "user" + i;
			String password = "pass";
			String email = "user" + i + "@email.com";
			String bio = "user" + i;
			String score = String.valueOf(234*i);
			String city = "city" + i;
			String country = "country" + i;
			String avatar = "default-avatar";
			
			User user = new User(username, surname, nickname, password, email, bio, score, city, country, avatar, "ROLE_USER");
			user.setScore("5750");
			userRepo.save(user); 
		}
		
		String avatar = "avatar-aleatorio";
	 	User admin = new User("admin", "admin", "admin", "admin", "admin@email.com", "admin","9999","admin","España",avatar, "ROLE_USER", "ROLE_ADMIN");
	 	userRepo.save(admin); 
		
		
		// Sample events
		for (int i = 0; i < 10; i++) {	
			String name = "Evento " + i;			
			double price = i;
			String info = "Aqui va la descripcion de los eventos";
			String place = "Universidad Rey Juan Carlos I";
			Event event = new Event(name, info, price, place);
			String[] sports = {"Mountain Bike", "Running", "Roller"};
			event.setSport(sports[(i%3)]);
			
			String main_photo = "default-mainphoto";
			event.setMain_photo(main_photo);

			Date start_date = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(i) + "/05/2017");
			event.setStart_date(start_date);
			Date end_date = new SimpleDateFormat("dd/MM/yyyy").parse("02/11/2017");
			event.setEnd_date(end_date);

			Calendar cal = toCalendar(start_date);			
			event.setDay_date(cal.get(Calendar.DAY_OF_MONTH));
			event.setMonth_date(cal.get(Calendar.MONTH));
			event.setYear_date(cal.get(Calendar.YEAR));
			
			event.setOwner_id(userRepo.findOne((long) ((i%5)+1)));

			eventRepo.save(event);
		}	    

		// Sample communities	
	    for (int i = 0; i < 5; i++) {
			String name = "Comunidad  " + i;
			String info = "Información de la comunidad.";
			String main_photo = "default-mainphoto";
			String[] sports = {"Mountain Bike", "Running", "Roller"};
			String sport = sports[(i%3)];
			Community community = new Community(name, info, sport, main_photo);
			
			community.setOwner_id(userRepo.findOne((long) ((i%5)+1)));
			communityRepo.save(community);
		}
	   
	    // Add followers
/*	    User user1 = userRepo.getOne((long) 1);
	    User user2 = userRepo.getOne((long) 2);
	    user1.getFollowing().add(user2);
	    userRepo.getOne(2L).addFollowing(userRepo.getOne(3L));
	    userRepo.getOne(2L).addFollowing(userRepo.getOne(1L));
	    userRepo.getOne(3L).addFollowing(userRepo.getOne(1L));
	    userRepo.getOne(3L).addFollowing(userRepo.getOne(2L));
	    userRepo.getOne(3L).addFollowing(userRepo.getOne(4L));
	    userRepo.getOne(4L).addFollowing(userRepo.getOne(2L));*/
	    
	       
/*	    // Add communities to users
	    userRepo.getOne(1L).addCommunity(communityRepo.getOne(1L));
	    userRepo.getOne(1L).addCommunity(communityRepo.getOne(2L));
	    userRepo.getOne(1L).addCommunity(communityRepo.getOne(4L));
	    userRepo.getOne(2L).addCommunity(communityRepo.getOne(1L));
	    userRepo.getOne(2L).addCommunity(communityRepo.getOne(2L));
	    userRepo.getOne(2L).addCommunity(communityRepo.getOne(3L));
	    userRepo.getOne(3L).addCommunity(communityRepo.getOne(1L));
	    userRepo.getOne(3L).addCommunity(communityRepo.getOne(2L));
	    userRepo.getOne(3L).addCommunity(communityRepo.getOne(3L));
	    userRepo.getOne(3L).addCommunity(communityRepo.getOne(4L));  */	    
	}

	private static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
