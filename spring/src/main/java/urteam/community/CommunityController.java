package urteam.community;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.news.*;
import urteam.user.*;

@Controller
public class CommunityController {

	@Autowired
	private urteamController urteam;

	@Autowired
	private CommunityService communityService;

	@Autowired
	private NewsRepository newsRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserComponent userComponent;

	@RequestMapping("/groups")
	public String getGroups(Model model, HttpServletRequest request) {
		// Añadir elementos basicos
		Page<Community> groups = communityService.findAll((new PageRequest(0, 3)));
		model.addAttribute("communitys", groups);
		model.addAttribute("groups_active", true);

		// Comprobar si hay un usuario logueado y añadirlo
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
			model.addAttribute("logged", true);
			model.addAttribute("user", userLogged);
			// Comprobar si es admin
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			
			//Comprobar si el usuario sigue a alguna comunidad
			for (Community community : groups) {
				model.addAttribute("communityFollowed", userLogged.getCommunityList().contains(community));
			}
			return "groups";
		} else {
			return "groups";
		}
	}

	@RequestMapping("/sortGroupByName")
	public String sortGroupByName(Model model, @RequestParam String name) {

		List<Community> groups = communityService.findByName(name);
		

		model.addAttribute("communitys", groups);
		model.addAttribute("groups_active", true);

		return "groups";
	}

	@RequestMapping("/group/{id}")
	public String editGroup(Model model, HttpServletRequest request, @PathVariable long id) {
		// Buscar la comunidad y su creador
		Community community = communityService.findOne(id);
		User ownerCommunity = userRepo.findOne(community.getOwner_id().getId());
		String ownerName = ownerCommunity.getNickname();

		// Añadir elementos basicos
		model.addAttribute("community", community);
		model.addAttribute("ownerName", ownerName);
		model.addAttribute("numberOfMembers", community.getCommunityUsers().size());
		model.addAttribute("members", community.getCommunityUsers());
		model.addAttribute("communityGallery", community.getCommunityImages());

		// Comprobar si hay un usuario logueado
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
			model.addAttribute("logged", true);
			model.addAttribute("user", userLogged);
			model.addAttribute("communityFollowed", userLogged.getCommunityList().contains(community));
			// Comprobar si es admin
			model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
			// Comprobar si es el creador
			model.addAttribute("owner", userLogged.getId() == ownerCommunity.getId());
			return "group";
		} else {
			return "group";
		}
	}

	@RequestMapping("/sortGroupBySport")
	public String sortGroupBySport(Model model, @RequestParam String sport) {
		List<Community> community = communityService.findBySport(sport);
		
		model.addAttribute("communitys", community);
		model.addAttribute("groups_active", true);
		return "groups";
	}

	@RequestMapping(value = "/moreGroups")
	public String moreAllShelf(Model model, @RequestParam int page) {
		Page<Community> groups = communityService.findAll(new PageRequest(page, 3));
		model.addAttribute("community", groups);
		return "listGroups";
	}

	@RequestMapping("/group/{id}/GroupEdited")
	public String groupEdited(Model model, @PathVariable long id, @RequestParam String info,
			HttpServletRequest request) {
		// Buscar la comunidad y su creador
		Community community = communityService.findOne(id);
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

			// Comprobar que el usuario y el dueño del evento son el mismoy si
			// lo son modificar
			communityService.editInfo(community, info);
			model.addAttribute("community", community);

			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}
	}

	@RequestMapping("/group/{id}/addNews")
	public String groupEdited(Model model, @PathVariable long id, @RequestParam String title, @RequestParam String text,
			HttpServletRequest request) {
		// Buscar la comunidad y su creador
		Community community = communityService.findOne(id);
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
			News news = new News(title, text);
			communityService.addNews(community,news);
			model.addAttribute("community", community);
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}
	}

	@RequestMapping("/group/{id}/addImage")
	public String addImage(Model model, @PathVariable long id, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws ParseException {
		// Buscar la comunidad y su creador
		Community community = communityService.findOne(id);
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
			communityService.addImage(community, file);
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

		communityService.add(community, file);
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
		Community community = communityService.findOne(id);

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

			communityService.save(community);
			userRepo.save(userLogged);
			model.addAttribute("community", community);
			return "redirect:/group/{id}";
		} else {
			return "redirect:/group/{id}";
		}
	}
}