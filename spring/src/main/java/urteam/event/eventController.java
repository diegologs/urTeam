package urteam.event;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	
	@RequestMapping("/addEvent")
	public String newEvent() {

		return "addEvent";

	}
	
	@RequestMapping("/eventAdded")
	public String eventAdded(Model model, Event event, @RequestParam String start_date, @RequestParam String end_date) throws ParseException {
		
		Date final_start_date = new SimpleDateFormat("dd/MM/yyyy").parse(start_date);
		event.setStart_date(final_start_date);
		
		Date final_end_date = new SimpleDateFormat("dd/MM/yyyy").parse(end_date);		
		event.setEnd_date(final_end_date);		
		
		Calendar cal = toCalendar(event.getStart_date());		
		event.setDay_date(cal.get(Calendar.DAY_OF_MONTH));
		event.setMonth_date(cal.get(Calendar.MONTH));
		event.setYear_date(cal.get(Calendar.YEAR));
		
		eventRepo.save(event);
		Page<Event> eventos = eventRepo.findAll(new PageRequest(0,3));
		model.addAttribute("events", eventos);
		return "events";
	}
	
	private static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}
}
