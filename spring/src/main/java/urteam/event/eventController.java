package urteam.event;

import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.urteamController;
import urteam.community.Community;




@Controller
public class eventController {
	
	@Autowired
	private EventRepository eventRepo;
	
	@Autowired
	private urteamController urteam;
	
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
		
		Page<Event> eventos = eventRepo.findAll(new PageRequest(0,9));
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
		
		model.addAttribute("imagen",event.getMain_photo());
		model.addAttribute("event.participants", event.getParticipants_IDs().size());
		return "event";
	}
	
	@RequestMapping("/addEvent")
	public String newEvent() {
		return "addEvent";

	}
	
	@RequestMapping("/eventAdded")
	public String eventAdded(Model model, Event event, @RequestParam String start_date,
			@RequestParam String end_date,  @RequestParam("file") MultipartFile file) throws ParseException {
		
		Date final_start_date = new SimpleDateFormat("dd/MM/yyyy").parse(start_date);
		event.setStart_date(final_start_date);
		
		Date final_end_date = new SimpleDateFormat("dd/MM/yyyy").parse(end_date);		
		event.setEnd_date(final_end_date);		
		
		Calendar cal = toCalendar(event.getStart_date());		
		event.setDay_date(cal.get(Calendar.DAY_OF_MONTH));
		event.setMonth_date(cal.get(Calendar.MONTH));
		event.setYear_date(cal.get(Calendar.YEAR));
		
		if(urteam.uploadImageFile(model, file, "action")){
			event.setMain_photo("test");
		}
		
		eventRepo.save(event);
<<<<<<< HEAD
		//Page<Event> eventos = eventRepo.findAll(new PageRequest(0,3));
		//model.addAttribute("events", eventos);
		
		return "redirect:/events";
=======
		Page<Event> eventos = eventRepo.findAll(new PageRequest(0,9));
		model.addAttribute("events", eventos);
		return "events";
>>>>>>> branch 'master' of https://github.com/Frostqui/urTeam.git
	}
	
	private static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}
	
	@RequestMapping("/event/{id}/EventEdited")
	public String groupEdited(Model model, @PathVariable long id,  @RequestParam String info) {
		Event event = eventRepo.findOne(id);
		event.setInfo(info);
		eventRepo.save(event);
		model.addAttribute("event", event);
		return "redirect:/event/{id}";
	}
	
	@RequestMapping("/addimage")
	public String addimage(){
		return "addimage";
	}
	
//	@PostMapping("/")
//    public String checkPersonInfo(@Valid Event personForm, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "form";
//        }
//        return "redirect:/results";
//    }
}
