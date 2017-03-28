package urteam.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/events")
public class EventRestController {
	
	interface CompleteEvent extends Event.BasicEvent{}
	
	@Autowired
	private EventRepository eventRepository;

	@JsonView(CompleteEvent.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Event> events() {
		// Añadir elementos basicos
		List<Event> events = eventRepository.findAll();
		
			return events;
	}
	
	@JsonView(CompleteEvent.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@PathVariable long id){
		Event event = eventRepository.findOne(id);
		if(event != null){
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		}else{
			return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
		}
	}
}
