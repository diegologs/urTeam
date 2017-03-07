package urteam.event;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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
	public String eventos(Model model, Pageable page) {
		
		String sortedBy;
		
		if(page.getSort() != null){
			sortedBy = page.getSort().iterator().next().getProperty();
		}else{
			sortedBy = "name";
			page = new PageRequest(page.getPageNumber(), page.getPageSize(), 
					new Sort(new Order(Sort.DEFAULT_DIRECTION, "name")));
		}
		
		Page<Event> eventos = eventRepo.findAll(new PageRequest(0,3));
		model.addAttribute("events", eventos);
		model.addAttribute("sortedBy",sortedBy);
		return "events";
	}
	
	@RequestMapping(value="/moreContent")
	public String moreAllShelf(Model model, @RequestParam int page){
		
		Page<Event> eventos = eventRepo.findAll(new PageRequest(page,3));
		
		model.addAttribute("event", eventos);
		
		return "listEvents";
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
