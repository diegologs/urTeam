package urteam;

import java.sql.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import urteam.event.Event;
import urteam.event.EventRepository;

@Controller
public class urteamController {
	
	@Autowired
	private EventRepository repository;
	
	@PostConstruct
	public void init() {
		for(int i=1;i<=10;i++){
		repository.save(new Event("Nombre Evento # "+i,"Running",20.0,"Información detallada Evento","Madrid",new Date(2017,03,01),new Date(2017,03,01))); 
		}
	}

	@RequestMapping("/events")
	public String eventos(Model model) {

		model.addAttribute("eventos", repository.findAll());

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
