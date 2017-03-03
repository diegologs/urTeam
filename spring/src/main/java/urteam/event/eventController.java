package urteam.event;

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
	
	@RequestMapping("addEvent")
	public String newEvent() {

		return "addEvent";

	}
	
	@RequestMapping("EventAdded")
	public String eventAdded(Model model, Event evento) {
		
		evento.setStart_date(new Date(3434));
		evento.setEnd_date(new Date(3434));
		eventRepo.save(evento);
		model.addAttribute("eventos", eventRepo.findAll());
		return "events";

	}
}
