package urteam.event;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urTeamService;
import urteam.community.Community;
import urteam.user.User;

@Service
public class EventService {

	@Autowired
	private urTeamService urteamService;

	@Autowired
	private EventRepository eventRepo;

	public Event findOne(long id) {
		return eventRepo.findOne(id);
	}

	public Page<Event> findAll(Pageable pageable) {
		return eventRepo.findAll(pageable);
	}

	public Page<Event> findBySport(String sport, Pageable pageable) {
		return eventRepo.findBySport(sport, pageable);
	}

	public Page<Event> findAll(PageRequest pageRequest) {
		return eventRepo.findAll(pageRequest);
	}

	public void save(Event event) {
		eventRepo.save(event);
	}

	public void save(User user, Event event, MultipartFile file,String start_date, String end_date) {
		// Crear evento
		try {
			event.setOwner_id(user);
//			String fakeStart = event.getStart_date().toString();
//			String fakeEnd = event.getEnd_date().toString();
//			Date final_start_date = new SimpleDateFormat().parse(fakeStart);
//			event.setStart_date(final_start_date);
//			Date final_end_date = new SimpleDateFormat().parse(fakeEnd);
//			event.setEnd_date(final_end_date);
			Calendar cal = toCalendar(event.getStart_date());
			event.setDay_date(cal.get(Calendar.DAY_OF_MONTH));
			String month = monthToString(cal.get(Calendar.MONTH));
			event.setMonth_date(month);
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
			e.printStackTrace();
		}
		// Guardar evento y recargar pagina
		eventRepo.save(event);
	}
	private String monthToString(int month_date) {
		String[] months = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEPT", "OCT", "NOV", "DIC"};
		return months[month_date];
	}

	public void follow(User user, Event event) {
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

	public void delete(long id) {
		eventRepo.delete(id);
	}

	public List<Event> getEventsByCriteria(String criteria) {
		List<Event> foundEvents = eventRepo.findByNameContaining(criteria);
		if (foundEvents != null) {
			return foundEvents;
		} else {
			return null;
		}
	}
	
	public byte[] getEventAvatar(long id) throws FileNotFoundException, IOException{
		Event event = eventRepo.findOne(id);
		if(event != null){
			 byte[] file = urteamService.getFile(ConstantsUrTeam.EVENT_AVATAR, event.getEventId(),
					event.getMain_photo()); 
			if(file != null){
				return file;
			}
		} else {
			return null;
		}
		return null;
	}
	
	public void setImage(Event event, MultipartFile file) {

		try {
				SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
				Date date = new Date();

				String filename = "avatar-" + formater.format(date);

				if (urteamService.uploadImageFile(file, filename, ConstantsUrTeam.EVENT_AVATAR,event.getEventId())) {
					event.setMain_photo(filename);
					eventRepo.save(event);
				}
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}

}
