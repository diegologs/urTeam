package urteam.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.community.Community;
import urteam.sport.*;
import urteam.user.*;

@Controller
public class eventController {

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private urteamController urteam;

	@Autowired
	private SportController sportController;

	@Autowired
	private UserComponent userComponent;

	@RequestMapping("/events")
	public String eventos(Model model, Pageable page, HttpServletRequest request) {
		String sortedBy;
		if (page.getSort() != null) {
			sortedBy = page.getSort().iterator().next().getProperty();
		} else {
			sortedBy = "name";
			page = new PageRequest(page.getPageNumber(), page.getPageSize(),
					new Sort(new Order(Sort.DEFAULT_DIRECTION, "name")));
		}
		Page<Event> eventos = eventRepo.findAll(new PageRequest(0, 6));
		model.addAttribute("events", eventos);
		model.addAttribute("sortedBy", sortedBy);
		model.addAttribute("sportList", sportController.getSportList());
		model.addAttribute("events_active", true);
		if ((userComponent.isLoggedUser())) {
			long id = userComponent.getLoggedUser().getId();
			User user = userRepo.findOne(id);
			model.addAttribute("user", user);
			if (userComponent.getLoggedUser().getId() == user.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "events";
		} else {
			return "events";
		}
	}

	@RequestMapping(value = "/moreContent")
	public String moreAllShelf(Model model, @RequestParam int page) {
		Page<Event> eventos = eventRepo.findAll(new PageRequest(page, 6));
		model.addAttribute("event", eventos);
		return "listEvents";
	}

	@RequestMapping("/event/{id}")
	public String showEvent(Model model, @PathVariable long id, HttpServletRequest request) {
		Event event = eventRepo.findOne(id);
		model.addAttribute("event", event);
		model.addAttribute("imagen", event.getMain_photo());
		model.addAttribute("event.participants", event.getParticipants_IDs().size());
		model.addAttribute("eventGallery", event.getEventImages());
		model.addAttribute("events_active", true);
		model.addAttribute("numberOfMembers", event.getParticipants_IDs().size());
		if (userComponent.isLoggedUser()) {
			User user = userRepo.findOne(userComponent.getLoggedUser().getId());
			model.addAttribute("user", user);
			if (user.getEventList().contains(event)) {
				model.addAttribute("following", true);
			} else {
				model.addAttribute("following", false);
			}
			if (userComponent.getLoggedUser().getId() == user.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "event";
		} else {
			return "event";
		}
	}

	@RequestMapping("/addEvent")
	public String newEvent(Model model, HttpServletRequest request) {
		model.addAttribute("events_active", true);
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "addEvent";
		} else {
			return "addEvent";
		}
	}

	@RequestMapping("/eventAdded")
	public String eventAdded(Model model, Event event, @RequestParam String start_date, @RequestParam String end_date,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) throws ParseException {
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
		if (urteam.uploadImageFile(model, file, filename, ConstantsUrTeam.EVENT_AVATAR, event.getEventId())) {
			event.setMain_photo(filename);
		}
		eventRepo.save(event);
		Page<Event> eventos = eventRepo.findAll(new PageRequest(0, 9));
		model.addAttribute("events", eventos);
		model.addAttribute("events_active", true);
		if ((userComponent.isLoggedUser())) {
			long id = userComponent.getLoggedUser().getId();
			User user = userRepo.findOne(id);
			model.addAttribute("user", user);
			if (userComponent.getLoggedUser().getId() == user.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/events";
		} else {
			return "redirect:/events";
		}
	}

	private static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	@RequestMapping("/event/{id}/EventEdited")
	public String groupEdited(Model model, @PathVariable long id, @RequestParam String info,
			HttpServletRequest request) {
		Event event = eventRepo.findOne(id);
		event.setInfo(info);
		eventRepo.save(event);
		model.addAttribute("event", event);
		model.addAttribute("events_active", true);
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/event/{id}";
		} else {
			return "redirect:/event/{id}";
		}

	}

	@RequestMapping("/event/{id}/addImage")
	public String addImage(Model model, @PathVariable long id, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws ParseException {
		Event event = eventRepo.findOne(id);
		// Filename formater
		SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
		Date date = new Date();
		String filename = "imageingallery-" + formater.format(date);
		if (urteam.uploadImageFile(model, file, filename, ConstantsUrTeam.EVENT_IMGS, event.getEventId())) {
			event.addNewImageToGallery(filename);
		}
		eventRepo.save(event);
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/event/{id}";
		} else {
			return "redirect:/event/{id}";
		}
	}

	@RequestMapping("/sortEventByName/{name}")
	public String sortEventByName(Model model, @RequestParam String name) {
		List<Event> eventos = eventRepo.findAll();
		eventos = eventRepo.findByName(name);
		model.addAttribute("events", eventos);
		return "redirect:/events";
	}

	@RequestMapping("/sortEventBySport")
	public String sortEventBySport(Model model, @RequestParam String sport) {
		List<Event> eventos = eventRepo.findBySport(sport);
		model.addAttribute("events", eventos);
		return "redirect:/events";
	}

	@RequestMapping("/event/{id}/follow")
	public String follow(Model model, @PathVariable long id, HttpServletRequest request) {
		Event event = eventRepo.findOne(id);
		User user = userRepo.findOne(userComponent.getLoggedUser().getId());
		if (user.getEventList().contains(event)) {
			user.removeEvent(event);
			model.addAttribute("following", false);

		} else {
			user.addEvent(event);
			model.addAttribute("following", true);
		}
		userRepo.save(user);
		model.addAttribute("event", event);
		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:/event/{id}";
		} else {
			return "redirect:/event/{id}";
		}
	}

	// @PostMapping("/")
	// public String checkPersonInfo(@Valid Event personForm, BindingResult
	// bindingResult) {
	// if (bindingResult.hasErrors()) {
	// return "form";
	// }
	// return "redirect:/results";
	// }
}
