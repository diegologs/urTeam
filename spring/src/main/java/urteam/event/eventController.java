package urteam.event;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class eventController {
	
	
	@RequestMapping("/events")
	public String eventos(Model model) {


		return "events";
	}
	
	@RequestMapping("/event/{id}")
	public String showEvent(Model model, @PathVariable long id) {
		
		Event event = repository.findOne(id);

		model.addAttribute("event", event);

		return "event";
	}
	
	@RequestMapping("/events/add")
	public String addEvent(Model model, Event evento) {

		repository.save(evento);

		return "events";

	}
}
