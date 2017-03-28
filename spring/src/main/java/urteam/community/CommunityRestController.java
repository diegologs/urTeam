package urteam.community;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.news.News;
import urteam.news.NewsRepository;
import urteam.user.User;
import urteam.user.UserComponent;
import urteam.user.UserRepository;


@RestController


@RequestMapping("/api/groups")
public class CommunityRestController {

	@Autowired
	private urteamController urteam;

	@Autowired
	private CommunityService service;

	@Autowired
	private NewsRepository newsRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserComponent userComponent;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<Community> groups() {
		
		return service.findAll();
	}
	
	
	
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Community> getGroup(@PathVariable long id) {
		
		Community community = service.findOne(id);
		
		if(community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
		
		
		
	}


	

	@RequestMapping(value = "/group/{id}/edit", method = RequestMethod.PUT)
	public ResponseEntity<Community> addInfo(@PathVariable long id, @RequestParam String info) {
		// Buscar la comunidad y su creador
		Community community = service.findOne(id);
		User ownerCommunity = userRepo.findOne(community.getOwner_id().getId());

		// Comprobar si hay un usuario logueado
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
			

			// Comprobar que el usuario y el dueño del evento son el mismoy si
			// lo son modificar
			if (userLogged.getId() == ownerCommunity.getId()) {
				community.setInfo(info);
			}
			service.save(community);
		
		}
		
		if(community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
		
	}

	
	@RequestMapping("/group/{id}/addNews")
	public ResponseEntity<Community> groupEdited(@PathVariable long id, @RequestParam String title, @RequestParam String text) {
		// Buscar la comunidad y su creador
		Community community = service.findOne(id);
		User ownerCommunity = userRepo.findOne(community.getOwner_id().getId());

		// Comprobar si hay un usuario logueado
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);

			// Comprobar que el usuario y el dueño de la comunidad son el mismo
			// y si lo son modificar
			if (userLogged.getId() == ownerCommunity.getId()) {
				News news = new News(title, text);
				community.getNews().add(news);
				newsRepo.save(news);
			}
			service.save(community);
		
		}
		
			
		if(community != null){
			return new ResponseEntity<>(community, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(community, HttpStatus.NOT_FOUND);
		}
			
	}

/*
	@RequestMapping("/group/{id}/addImage")
	public String addImage(Model model, @PathVariable long id, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws ParseException {
		// Buscar la comunidad y su creador
		Community community = communityRepo.findOne(id);
		User ownerCommunity = userRepo.findOne(community.getOwner_id().getId());

		// Comprobar si hay un usuario logueado
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
			model.addAttribute("logged", true);
			model.addAttribute("user", userLogged);
			model.addAttribute("communityFollowed", userLogged.getCommunityList().contains(community));
			// Comprobar si es admin
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));

			// Comprobar que el usuario y el dueño de la comunidad son el mismo
			// y si lo son modificar
			if (userLogged.getId() == ownerCommunity.getId()) {
				SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
				Date date = new Date();

				String filename = "imageingallery-" + formater.format(date);

				if (urteam.uploadImageFile(model, file, filename, ConstantsUrTeam.COMMUNITY_IMGS,
						community.getCommunityId())) {
					community.addImage(filename);
				}
			}
			communityRepo.save(community);
			model.addAttribute("community", community);
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}
	}

	@RequestMapping("addGroup")
	public String newEvent() {
		return "addGroup";
	}

	@RequestMapping("/GroupAdded")
	public String groupAdded(Model model, Community community, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws ParseException {

		// Filename formater
		SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
		Date date = new Date();

		// EventId generator
		SimpleDateFormat communityIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
		String communitytId = communityIdFormater.format(date);
		community.setCommunityId(communitytId);
		community.setOwner_id(userComponent.getLoggedUser());

		String filename = "avatar-" + formater.format(date);

		if (urteam.uploadImageFile(model, file, filename, ConstantsUrTeam.COMMUNITY_AVATAR,
				community.getCommunityId())) {
			community.setMain_photo(filename);
		}
		communityRepo.save(community);

		model.addAttribute("groups_active", true);

		if ((userComponent.isLoggedUser())) {
			long idUser = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(idUser);
			model.addAttribute("user", userLogged);
			if (userComponent.getLoggedUser().getId() == userLogged.getId()) {
				model.addAttribute("logged", true);
			}
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			return "redirect:groups";
		} else {
			return "redirect:groups";
		}

	}

	@RequestMapping("/group/{id}/follow")
	public String follow(Model model, @PathVariable long id, HttpServletRequest request) {
		// Buscar la comunidad y su creador
		Community community = communityRepo.findOne(id);

		// Comprobar si hay un usuario logueado
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
			model.addAttribute("logged", true);
			model.addAttribute("user", userLogged);			
			// Comprobar si es admin
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));

			// Comprobar si la comunidad esta entre las seguidas del usuario
			if (userLogged.getCommunityList().contains(community)) {
				userLogged.removeCommunity(community);
			} else {
				userLogged.addCommunity(community);
			}
			model.addAttribute("communityFollowed", userLogged.getCommunityList().contains(community));

			communityRepo.save(community);
			userRepo.save(userLogged);
			model.addAttribute("community", community);
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}
	}
	
	*/
	}
