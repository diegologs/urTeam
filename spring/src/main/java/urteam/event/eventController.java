package urteam.event;

import java.text.SimpleDateFormat;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class eventController {
	
	@Autowired
	private EventRepository eventRepo;
	
	@RequestMapping("/events")
	public String eventos(Model model) {

		model.addAttribute("eventos", eventRepo.findAll());

		return "events";
	}
	
	
	@RequestMapping("/event/{id}")
	public String showEvent(Model model, @PathVariable long id) {
		
		Event event = eventRepo.findOne(id);

		model.addAttribute("event", event);

		return "event";
	}
	
	@RequestMapping("/addEvent")
	public String newEvent() {

		return "addEvent";

	}
	
	@RequestMapping("/eventAdded")
	public String eventAdded(Model model, Event evento, @RequestParam String start_date, @RequestParam String end_date) {
		
		System.out.println(start_date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf.parse(start_date);
		Date sqlStartDate = new Date(date.getTime());
		
		date = sdf.parse(end_date);
		Date sqlEndDate = new Date(date.getTime());
		
		evento.setStart_date(sqlStartDate);
		evento.setEnd_date(sqlEndDate);
		eventRepo.save(evento);
		model.addAttribute("eventos", eventRepo.findAll());
		return "events";

	}
}
