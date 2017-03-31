package urteam.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urTeamService;
import urteam.urteamController;
import urteam.user.User;

@Service
public class EventService {
	
	@Autowired
	private urTeamService urteamService;
	
	@Autowired
	private EventRepository eventRepo;
	
	public Event findOne(long id){
		return eventRepo.findOne(id);
	}
	
	public List<Event> findAll(){
		return eventRepo.findAll();
	}
	
	public Page<Event> findAll(PageRequest pageRequest){
		return eventRepo.findAll(pageRequest);
	}
	
	public void save(Event event){
		eventRepo.save(event);
	}
	
	public void save(User user, Event event, MultipartFile file, String start_date, String end_date){
		// Crear evento
		try {
			event.setOwner_id(user);
			Date final_start_date = new SimpleDateFormat("dd/MM/yyyy").parse(start_date);
			event.setStart_date(final_start_date);
			Date final_end_date = new SimpleDateFormat("dd/MM/yyyy").parse(end_date);
			event.setEnd_date(final_end_date);
			Calendar cal = toCalendar(event.getStart_date());
			event.setDay_date(cal.get(Calendar.DAY_OF_MONTH));
			event.setMonth_date(cal.get(Calendar.MONTH));
			event.setYear_date(cal.get(Calendar.YEAR));
			// Filename formater
			SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
			Date date = new Date();
			// EventId generator
			SimpleDateFormat eventIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
			String eventId = eventIdFormater.format(date);
			event.setEventId(eventId);
			String filename = "avatar-" + formater.format(date);
			
			if (urteamService.uploadImageFile(file, filename, ConstantsUrTeam.EVENT_AVATAR, event.getEventId())) {
				event.setMain_photo(filename);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Guardar evento y recargar pagina
		eventRepo.save(event);
	}
	
	public void follow(User user, Event event){
		// Comprobar si el evento esta entre las seguidas del usuario
		if (user.getEventList().contains(event)) {
			user.removeEvent(event);
		} else {
			user.addEvent(event);
		}
	}
	
	private static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public void delete(long id){
		eventRepo.delete(id);
	}

}
