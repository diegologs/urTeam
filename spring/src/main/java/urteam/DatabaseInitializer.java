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

		// Sample users
		for (int i = 0; i < 10; i++) {
			String name = "Usuario" + i;
			String surname = "apellido" + i;
			String nickname = "user" + surname.substring(1, 3) + i;
			String password = "123456";
			String email = name + surname + i + "@urteam.com";
			String bio = "Lorem ipsum dolor sit amet, " + "consectetur adipiscing elit, "
					+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
					+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris "
					+ "nisi ut aliquip ex ea commodo consequat. "
					+ "Duis aute irure dolor in reprehenderit in voluptate velit "
					+ "esse cillum dolore eu fugiat nulla pariatur. "
					+ "Excepteur sint occaecat cupidatat non proident, "
					+ "sunt in culpa qui officia deserunt mollit anim id est laborum. "
					+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
					+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
			String score = "9999";
			String city = "Madrid";
			String country = "España";

			userRepo.save(new User(name, surname, nickname, password, email, bio, score, city, country));
		}
		
		//Add followers
		
		User user1= userRepo.findOne(1L);
		User user2= userRepo.findOne(2L);
		user1.addFollower(user2);
		user1.addFollowing(user2);
		user2.addFollower(user1);
		user2.addFollowing(user1);
		userRepo.save(user1);
		userRepo.save(user2);
		
		// Sample events
		for (int i = 0; i < 18; i++) {	
			String name = "Evento de Prueba";
			String[] sports = {"Running", "Mountain Bike", "Roller"}; 
			String sport = sports[i%3];
			double price = i;
			String info = "Aqui va la descripcion de lso eventos";
			String place = "Universidad Rey Juan Carlos I";

			Date start_date = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(i) + "/05/2017");
			Date end_date = new SimpleDateFormat("dd/MM/yyyy").parse("02/11/2017");

			Calendar cal = toCalendar(start_date);

			Event event = new Event(name, sport, price, info, place, start_date, end_date);
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
	}

	private static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
