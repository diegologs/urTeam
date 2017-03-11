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
import urteam.news.News;
import urteam.news.NewsRepository;
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

	@PostConstruct
	public void init() throws ParseException {


		
//		User user1= userRepo.findOne((long) 1);
//		User user2= userRepo.findOne((long) 2);
//		user1.addFollower(user2);
//		user1.addFollowing(user2);
//		user2.addFollower(user1);
//		user2.addFollowing(user1);
//		userRepo.save(user1);
//		userRepo.save(user2);
		
		// Sample events
		for (int i = 0; i < 18; i++) {	
			String name = "Evento de Prueba";
			String[] sports = {"Running", "Mountain Bike", "Roller"}; 
			String sport = sports[i%3];
			double price = i;
			String info = "Aqui va la descripcion de los eventos";
			String place = "Universidad Rey Juan Carlos I";
			String main_photo = "http://lorempixel.com/400/200/sports/" + i%10;

			Date start_date = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(i) + "/05/2017");
			Date end_date = new SimpleDateFormat("dd/MM/yyyy").parse("02/11/2017");

			Calendar cal = toCalendar(start_date);

			Event event = new Event(name, sport, price, info, place, start_date, end_date, main_photo);
			event.setDay_date(cal.get(Calendar.DAY_OF_MONTH));
			event.setMonth_date(cal.get(Calendar.MONTH));
			event.setYear_date(cal.get(Calendar.YEAR));

			eventRepo.save(event);
		}

		// Sample communities
		for (int i = 0; i < 10; i++) {
			String name = "Comunidad  "+String.valueOf(i);
			String info = "Información de la comunidad.";
			
			News news = new News("Titulo de ejemplo de la noticia", "Texto de ejemplo de la noticia.");
			newsRepo.save(news);
			
			
			Community com = new Community(name, info, "Running");
			

			communityRepo.save(com);
		}
		
		
		// Sample users
		
	 	User user1 = new User("username1", "surname1", "nickname1", "password1", "email1", "bio1","9999","Madrid","España");
	    User user2 = new User("username2", "surname2", "nickname2", "password2", "email2", "bio2","9999","Madrid","España");
	    User user3 = new User("username3", "surname3", "nickname3", "password3", "email3", "bio3","9999","Madrid","España");
	    User user4 = new User("username4", "surname4", "nickname4", "password4", "email4", "bio4","9999","Madrid","España");

	    userRepo.save(user1);    
	    userRepo.save(user2);
	    userRepo.save(user3);
	    userRepo.save(user4);
	   
	//Add followers
	    
	    user1.addFollowing(user2);
	    user1.addFollowing(user4);
	    user2.addFollowing(user3);
//	    user2.addFollowing(user1);
	    user3.addFollowing(user2);
//	    user3.addFollowing(user1);
	       
	 //Add communities to users
	    user1.addCommunity(communityRepo.findOne((long) 1));
	    user1.addCommunity(communityRepo.findOne((long) 2));
	    
	    userRepo.save(user1);    
	    userRepo.save(user2);
	    userRepo.save(user3);
	    userRepo.save(user4);
	    
	}

	private static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
